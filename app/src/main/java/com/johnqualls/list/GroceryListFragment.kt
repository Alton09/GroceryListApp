package com.johnqualls.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.johnqualls.databinding.FragmentGroceryListBinding
import kotlinx.android.synthetic.main.fragment_grocery_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class GroceryListFragment : Fragment() {
    private val viewModel by viewModel<GroceryListViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentGroceryListBinding.inflate(inflater, container, false).apply{
            viewModel = this@GroceryListFragment.viewModel
            lifecycleOwner = this@GroceryListFragment.viewLifecycleOwner
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).setSupportActionBar(grocery_list_toolbar)
        grocery_list_items.adapter = GrocerListAdapter()
    }

}
