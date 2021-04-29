package edu.calpoly.flipted.ui.goals


import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import edu.calpoly.flipted.R
import edu.calpoly.flipted.ui.goals.edit.AddSubGoalViewHolder
import edu.calpoly.flipted.ui.goals.edit.GoalBlockViewHolder
import edu.calpoly.flipted.ui.goals.edit.MutableSubGoal
import edu.calpoly.flipted.ui.goals.edit.SubGoalViewHolder
import java.util.*

class GoalRecyclerViewAdapter(
        private val fragment: Fragment
) : RecyclerView.Adapter<GoalBlockViewHolder>()
{
    private val inflater = fragment.layoutInflater

    var subGoalBlocks : List<MutableSubGoal> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalBlockViewHolder {
        val inflatedView = inflater.inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.goals_fragment_create_subgoal_list_item -> SubGoalViewHolder(inflatedView,this)
            R.layout.goals_fragment_create_subgoal_list_item_add -> AddSubGoalViewHolder(inflatedView, this)
            else -> throw IllegalArgumentException("Unknown viewType")
        }
    }

    override fun onBindViewHolder(holder: GoalBlockViewHolder, position: Int) {
        if (position != subGoalBlocks.size){
            (holder as SubGoalViewHolder).bind(subGoalBlocks[position])
        }
    }

    override fun getItemCount(): Int = subGoalBlocks.size + 1

    override fun getItemViewType(position: Int): Int =
        if(position == subGoalBlocks.size)
            R.layout.goals_fragment_create_subgoal_list_item_add
        else
            R.layout.goals_fragment_create_subgoal_list_item
}

