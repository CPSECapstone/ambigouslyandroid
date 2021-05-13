package edu.calpoly.flipted.ui.missions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import edu.calpoly.flipted.R
import edu.calpoly.flipted.ui.tasks.TaskFragment

/**
 * A simple [Fragment] subclass.
 * Use the [MissionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MissionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_mission, container, false)

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

    companion object {
        @JvmStatic
        fun newInstance() = MissionFragment()
        }
}