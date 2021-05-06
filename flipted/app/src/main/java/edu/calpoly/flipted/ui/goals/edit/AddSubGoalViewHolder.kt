package edu.calpoly.flipted.ui.goals.edit

import android.view.View
import android.widget.Button
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.goals.SubGoal
import edu.calpoly.flipted.ui.goals.GoalRecyclerViewAdapter
import java.util.*


class  AddSubGoalViewHolder(view: View, private val adapter: GoalRecyclerViewAdapter) : GoalBlockViewHolder(view) {
    private val addSubGoalButton: Button = view.findViewById(R.id.add_sub_goal_button)

    init {

        addSubGoalButton.setOnClickListener{
            adapter.subGoalBlocks = adapter.subGoalBlocks + MutableSubGoal(SubGoal("", Date(), false, null))
            adapter.notifyItemInserted(adapter.subGoalBlocks.size - 1)
        }


    }
}