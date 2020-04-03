package com.johnqualls.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.jakewharton.rxbinding3.swiperefreshlayout.refreshes
import com.johnqualls.databinding.FragmentGroceryListBinding
import com.johnqualls.udf.observeViewState
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_grocery_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class GroceryListFragment : Fragment() {

    private val disposables = CompositeDisposable()
    private val viewModel by viewModel<GroceryListViewModel>()
    private lateinit var databinding: FragmentGroceryListBinding

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewState(viewModel, ::bindViewState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        FragmentGroceryListBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@GroceryListFragment.viewLifecycleOwner
            databinding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).setSupportActionBar(grocery_list_toolbar)
        grocery_list_items.adapter = GrocerListAdapter()
        registerInput()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    private fun bindViewState(viewState: GroceryListViewState) {
        databinding.run {
            groceryListProgress.isRefreshing = viewState.loading
            (groceryListItems.adapter as GrocerListAdapter).let { it.swap(viewState) }
        }
    }

    private fun registerInput() {
        Observable.merge(
            (grocery_list_items.adapter as GrocerListAdapter).viewEventObservable,
            grocery_list_progress.refreshes().map { GroceryListViewEvent.SwipeRefresh }
        )
            .subscribe({
                viewModel.processInput(it as GroceryListViewEvent)
            }, {
                Timber.e(it)
            })
            .addTo(disposables)
    }
}
