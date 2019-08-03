package com.johnqualls

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.johnqualls.list.GroceryListFragment

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, GroceryListFragment(), GroceryListFragment::class.java.name)
            .commit()
    }

}