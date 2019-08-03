package com.johnqualls

import android.app.Application
import com.johnqualls.list.GroceryListDataSource
import com.johnqualls.list.GroceryListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class GroceryListApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            listOf(module {
                factory { GroceryListDataSource() }
                viewModel { GroceryListViewModel(get()) }
            })
        }
    }
}