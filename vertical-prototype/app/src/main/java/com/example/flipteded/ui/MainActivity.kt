package com.example.flipteded.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.flipteded.R
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            // supportFragmentManager.beginTransaction().replace(R.id.main_view, StudentHomeFragment.newInstance()).commitNow()
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, GoalListFragment.newInstance())
                .commitNow()
        }

        val tabLayout = findViewById<TabLayout>(R.id.navbar)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                // Handle tab select
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })
    }


}