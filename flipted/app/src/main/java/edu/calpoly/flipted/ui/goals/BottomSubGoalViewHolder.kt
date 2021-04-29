package edu.calpoly.flipted.ui.goals

import android.view.View
import android.widget.Button
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.goals.SubGoal
import edu.calpoly.flipted.businesslogic.goals.SubGoalInput
import java.util.*


class  BottomSubGoalViewHolder(view: View, private val adapter: GoalRecyclerViewAdapter) : GoalBlockViewHolder(view) {
    val addSubGoalButton = view.findViewById<Button>(R.id.add_sub_goal_button)

    init {

        addSubGoalButton.setOnClickListener{
            adapter.subGoalBlocks = adapter.subGoalBlocks + MutableSubGoal("", "", null,false, null )
        }


    }
}