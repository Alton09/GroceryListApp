package com.johnqualls

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.johnqualls.list.GroceryListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tag = GroceryListFragment::class.java.name
        supportFragmentManager.let {
            if (it.findFragmentByTag(tag) == null) {
                it.beginTransaction()
                    .add(R.id.fragment_container, GroceryListFragment(), tag)
                    .commit()
            }

        }
    }

}