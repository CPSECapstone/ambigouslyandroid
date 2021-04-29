package edu.calpoly.flipted.ui.goals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.goals.Goal

/**
 * A simple [Fragment] subclass.
 * Use the [GoalsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GoalsFragment : Fragment() {
    private lateinit var goalsListFragmentContainer : ViewGroup
    //private lateinit var viewModel : GoalsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.goals_fragment_main, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        viewModel = ViewModelProvider(requireActivity())[GoalsViewModel::class.java]
        goalsListFragmentContainer = view.findViewById(R.id.goals_list_fragment_container)

        val showGoalsList : () -> Unit = {
            goalsListFragmentContainer.removeAllViews()
            childFragmentManager.beginTransaction()
                .replace(R.id.goals_list_fragment_container, GoalListFragment.newInstance())
                .commitNow()
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
        */


        val newGoalButton = view.findViewById<Button>(R.id.newGoalButton)
        newGoalButton.setOnClickListener{
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.main_view, GoalNewFragment.newInstance())
                addToBackStack(null)
                commit()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = GoalsFragment()
    }
}