package edu.calpoly.flipted.ui


import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
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
import edu.calpoly.flipted.ui.myProgress.ProgressFragment
import edu.calpoly.flipted.ui.myProgress.targets.LearningTargetsFragment
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
                val targetFragment = when (tab!!.text) {
                    "Home" -> StudentHomeFragment.newInstance()
                    "Classes" -> ClassesFragment.newInstance()
                    "My Team" -> MyTeamFragment.newInstance()
                    "Marketplace" -> MarketplaceFragment.newInstance()
                    "Leaderboard" -> LeaderboardFragment.newInstance()
                    "My Progress" -> ProgressFragment.newInstance()
                    else -> throw IllegalStateException()
                }
                supportFragmentManager.commit {
                    replace(R.id.main_view, targetFragment)
                    setReorderingAllowed(true)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
                val targetFragment = when (tab!!.text) {
                    "Home" -> StudentHomeFragment.newInstance()
                    "Classes" -> ClassesFragment.newInstance()
                    "My Team" -> MyTeamFragment.newInstance()
                    "Marketplace" -> MarketplaceFragment.newInstance()
                    "Leaderboard" -> LeaderboardFragment.newInstance()
                    "My Progress" -> ProgressFragment.newInstance()
                    else -> throw IllegalStateException()
                }
                supportFragmentManager.commit {
                    replace(R.id.main_view, targetFragment)
                    setReorderingAllowed(true)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })

        val loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        loginViewModel.isLoggedIn.observe(this, Observer {
            tabLayout.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Amplify.Auth.handleWebUISignInResponse(data)
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }


}