package edu.calpoly.flipted.ui.missions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.amplifyframework.auth.AuthUserAttributeKey
import edu.calpoly.flipted.R
import edu.calpoly.flipted.ui.goals.GoalsFragment
import edu.calpoly.flipted.ui.login.LoginFragment
import edu.calpoly.flipted.ui.login.LoginViewModel
import edu.calpoly.flipted.ui.tasks.TaskFragment

/**
 * A simple [Fragment] subclass.
 * Use the [MissionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MissionFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // mock task id for task 1
        val taskOneButton = view.findViewById<Button>(R.id.taskOneButton)
        taskOneButton.setOnClickListener{
            parentFragmentManager.commit {
                replace(R.id.main_view, TaskFragment.newInstance("4f681550ba9"))
                setReorderingAllowed(true)
                addToBackStack("Start task")
            }
        }

        // mock task id for task 2
        val taskTwoButton = view.findViewById<Button>(R.id.taskTwoButton)
        taskTwoButton.setOnClickListener{
            parentFragmentManager.commit {
                replace(R.id.main_view, TaskFragment.newInstance("90e0c730e56"))
                setReorderingAllowed(true)
                addToBackStack("Start task")
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mission, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MissionFragment()
        }
}