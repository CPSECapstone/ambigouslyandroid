package edu.calpoly.flipted.ui.goals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import edu.calpoly.flipted.R
import edu.calpoly.flipted.ui.quizzes.McFragment

/**
 * A simple [Fragment] subclass.
 * Use the [GoalsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GoalsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.goals_fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.beginTransaction()
            .replace(R.id.goals_list_fragment_container, GoalListFragment.newInstance())
            .commitNow()

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
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            GoalsFragment()
    }
}