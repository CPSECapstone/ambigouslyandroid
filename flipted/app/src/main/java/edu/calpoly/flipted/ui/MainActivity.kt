package edu.calpoly.flipted.ui


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.google.android.material.tabs.TabLayout
import edu.calpoly.flipted.R
import edu.calpoly.flipted.ui.classes.ClassesFragment
import edu.calpoly.flipted.ui.home.StudentHomeFragment
import edu.calpoly.flipted.ui.leaderboard.LeaderboardFragment
import edu.calpoly.flipted.ui.login.LoginFragment
import edu.calpoly.flipted.ui.login.LoginViewModel
import edu.calpoly.flipted.ui.marketplace.MarketplaceFragment
import edu.calpoly.flipted.ui.myProgress.MyProgressFragment
import edu.calpoly.flipted.ui.myTeam.MyTeamFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.main_view, LoginFragment.newInstance())
                setReorderingAllowed(true)
            }
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
                supportFragmentManager.commit {
                    replace(R.id.main_view, targetFragment)
                    setReorderingAllowed(true)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })

        val loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        loginViewModel.isLoggedIn.observe(this, Observer {
            tabLayout.visibility = if(it) View.VISIBLE else View.GONE
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Amplify.Auth.handleWebUISignInResponse(data)
        }
    }


}