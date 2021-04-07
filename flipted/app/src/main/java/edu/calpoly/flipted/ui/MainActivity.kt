package edu.calpoly.flipted.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.RSInvalidStateException
import edu.calpoly.flipted.R
import edu.calpoly.flipted.ui.home.StudentHomeFragment
import com.google.android.material.tabs.TabLayout
import edu.calpoly.flipted.ui.classes.ClassesFragment
import edu.calpoly.flipted.ui.leaderboard.LeaderboardFragment
import edu.calpoly.flipted.ui.marketplace.MarketplaceFragment
import edu.calpoly.flipted.ui.myProgress.MyProgressFragment
import edu.calpoly.flipted.ui.myTeam.MyTeamFragment
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.main_view, StudentHomeFragment.newInstance()).commitNow()
        }

        val tabLayout = findViewById<TabLayout>(R.id.navbar)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                // Handle tab select
                val targetFragment = when(tab!!.text) {
                    "Home" -> StudentHomeFragment.newInstance()
                    "Classes" -> ClassesFragment.newInstance()
                    "My Team" -> MyTeamFragment.newInstance()
                    "Marketplace" -> MarketplaceFragment.newInstance()
                    "Leaderboard" -> LeaderboardFragment.newInstance()
                    "My Progress" -> MyProgressFragment.newInstance()
                    else -> throw IllegalStateException()
                }
                supportFragmentManager.beginTransaction().replace(R.id.main_view, targetFragment).commitNow()
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