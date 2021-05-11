package edu.calpoly.flipted.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.amplifyframework.auth.AuthUserAttributeKey
import edu.calpoly.flipted.R
import edu.calpoly.flipted.ui.goals.GoalsFragment
import edu.calpoly.flipted.ui.login.LoginFragment
import edu.calpoly.flipted.ui.login.LoginViewModel
import edu.calpoly.flipted.ui.missions.MissionFragment
import edu.calpoly.flipted.ui.tasks.TaskFragment

/**
 * A simple [Fragment] subclass.
 * Use the [StudentHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StudentHomeFragment : Fragment() {

    private lateinit var loginViewModel : LoginViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val goalsButton = view.findViewById<Button>(R.id.goals_button)
            goalsButton.setOnClickListener{
                parentFragmentManager.commit {
                    replace(R.id.main_view, GoalsFragment.newInstance())
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
            }

        val missionButton = view.findViewById<Button>(R.id.mission_button)
        missionButton.setOnClickListener{
            parentFragmentManager.commit {
                replace(R.id.main_view, MissionFragment.newInstance())
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }

        loginViewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]

        val welcomeMsg = view.findViewById<TextView>(R.id.student_home_welcome_msg)
        val logoutButton = view.findViewById<Button>(R.id.student_home_logout_button)
        loginViewModel.userAttributes.observe(viewLifecycleOwner, Observer { attributes ->
            val email = attributes.find{it.key.equals(AuthUserAttributeKey.email())}?.value ?: "Student"
            welcomeMsg.text = "Welcome $email!"
        })
        loginViewModel.fetchUserAttributes()

        loginViewModel.isLoggedIn.observe(viewLifecycleOwner, Observer {
            if(!it)
                parentFragmentManager.commit {
                    replace(R.id.main_view, LoginFragment.newInstance())
                    setReorderingAllowed(true)
                }
        })

        logoutButton.setOnClickListener {
            loginViewModel.logOut()
        }

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_home, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            StudentHomeFragment()
    }
}