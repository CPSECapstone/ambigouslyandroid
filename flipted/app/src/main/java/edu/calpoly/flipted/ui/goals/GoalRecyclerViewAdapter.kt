package edu.calpoly.flipted.ui.goals


import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.goals.SubGoal
import edu.calpoly.flipted.businesslogic.goals.SubGoalInput
import java.util.*



data class MutableSubGoal(

    var title: String,
    val id: String?,
    var dueDate: Date?,
    val completed: Boolean,
    val completedDate: Date?

){
    private val myid = nextId
    companion object {
        private var nextId = 0
            get(){
                field += 1
                return field
            }
    }

    override fun equals(other: Any?): Boolean {
        return when(other){
            is MutableSubGoal -> other.title == title
                    && other.id == id
                    && other.dueDate == dueDate
                    && other.completed == completed
                    && other.completedDate == completedDate
                    && other.myid == myid
            else -> false

        }
    }
}

class GoalRecyclerViewAdapter(
        private val fragment: Fragment
) : RecyclerView.Adapter<GoalBlockViewHolder>()
{

    private val inflater = fragment.layoutInflater

    var subGoalBlocks : List<MutableSubGoal> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalBlockViewHolder {
        val inflatedView = inflater.inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.goals_fragment_create_subgoal_top -> TopSubGoalViewHolder(inflatedView,this)
            R.layout.goals_fragment_create_subgoal_bottom -> BottomSubGoalViewHolder(inflatedView, this)
            else -> throw IllegalArgumentException("Unknown viewType")
        }
    }

    override fun onBindViewHolder(holder: GoalBlockViewHolder, position: Int) {
        if (position != subGoalBlocks.size){
            (holder as TopSubGoalViewHolder).bind(subGoalBlocks[position])
        }
    }

    override fun getItemCount(): Int = subGoalBlocks.size + 1

    override fun getItemViewType(position: Int): Int =
        if(position == subGoalBlocks.size)
            R.layout.goals_fragment_create_subgoal_bottom
        else
            R.layout.goals_fragment_create_subgoal_top




}

