package edu.calpoly.flipted.ui.goals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.goals.Goal
import edu.calpoly.flipted.ui.goals.edit.EditGoalFragment
import edu.calpoly.flipted.ui.tasks.rubric.TaskRubricFragment

/**
 * A simple [Fragment] subclass.
 * Use the [GoalsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GoalsFragment : Fragment() {
    private lateinit var goalsListFragmentContainer : ViewGroup
    private lateinit var viewModel : GoalsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.goals_fragment_main, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //need to make it with edit goal
        childFragmentManager.commit {
            replace(R.id.edit_goal_container, EditGoalFragment.newInstanceCreateGoal())
            setReorderingAllowed(true)
        }

        viewModel = ViewModelProvider(requireActivity())[GoalsViewModel::class.java]
        goalsListFragmentContainer = view.findViewById(R.id.goals_list_fragment_container)

        val showGoalsList : () -> Unit = {
            goalsListFragmentContainer.removeAllViews()
            childFragmentManager.commit {
                replace(R.id.goals_list_fragment_container, GoalListFragment.newInstance())
                setReorderingAllowed(true)
            }
        }

        if(viewModel.goals.value == null) {
            // We need to load the goals from the server before showing the list
            viewModel.goals.observe(viewLifecycleOwner, object : Observer<List<Goal>> {
                // Can't use lambda syntax here because we need the this pointer
                // Instead use an anonymous object
                override fun onChanged(t: List<Goal>?) {
                    showGoalsList()
                    // Need to remove the observer here. Otherwise, every time the data is refreshed
                    // an entirely new GoalsListFragment will be instantiated and loaded in
                    viewModel.goals.removeObserver(this)
                }
            })
            viewModel.fetchGoals()
        } else {
            showGoalsList()
        }

        val newGoalButton = view.findViewById<Button>(R.id.newGoalButton)
        newGoalButton.setOnClickListener{
            parentFragmentManager.commit {
                replace(R.id.edit_goal_container, EditGoalFragment.newInstanceCreateGoal())
                setReorderingAllowed(true)
                addToBackStack("EditGoalFragment")
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = GoalsFragment()
    }
}