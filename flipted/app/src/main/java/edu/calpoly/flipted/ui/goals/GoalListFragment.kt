package edu.calpoly.flipted.ui.goals


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.calpoly.flipted.R
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [AddStringFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GoalListFragment : Fragment() {

    private lateinit var viewModel : GoalsViewModel
    private lateinit var expandableListView: ExpandableListView
    private lateinit var adapter: CustomExpandableListAdapter
    private lateinit var noGoalsMessage : ViewGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.goals_fragment_exp_list, container, false)

    private fun checkIfNoItems() {
        val goals = viewModel.goals.value
        if(goals == null || goals.isEmpty()) {
            expandableListView.visibility = View.GONE
            noGoalsMessage.visibility = View.VISIBLE
        } else {
            expandableListView.visibility = View.VISIBLE
            noGoalsMessage.visibility = View.GONE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[GoalsViewModel::class.java]

        expandableListView = view.findViewById(R.id.expandableList)
        noGoalsMessage = view.findViewById(R.id.goals_list_no_goals_msg)

        //Ref for switching group indicator: https://stackoverflow.com/questions/5800426/expandable-list-view-move-group-icon-indicator-to-right
        val newDisplay = expandableListView.viewTreeObserver
        newDisplay.addOnGlobalLayoutListener {
            expandableListView.setIndicatorBoundsRelative(
                expandableListView.right - 40,
                expandableListView.width
            )
        }

        adapter = CustomExpandableListAdapter(requireActivity())
        expandableListView.setAdapter(adapter)

        viewModel.goals.observe(viewLifecycleOwner, Observer { newGoals ->
            adapter.setGoals(newGoals)
            checkIfNoItems()
        })

        checkIfNoItems()

        viewModel.fetchGoals()
    }

    companion object {
        @JvmStatic
        fun newInstance() = GoalListFragment()
    }
}