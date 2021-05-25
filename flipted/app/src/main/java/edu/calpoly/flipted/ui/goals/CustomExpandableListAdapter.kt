package edu.calpoly.flipted.ui.goals
import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.UidToStableId
import edu.calpoly.flipted.businesslogic.goals.Goal
import edu.calpoly.flipted.ui.goals.edit.EditGoalFragment
import java.text.SimpleDateFormat

class CustomExpandableListAdapter (
    fragment: Fragment,
    private val listView: ExpandableListView
) : BaseExpandableListAdapter() {

    private val dateFormat = SimpleDateFormat("MMMM dd, yyyy")

    private var goalData: List<Goal> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    private val viewModel = ViewModelProvider(fragment.requireActivity())[GoalsViewModel::class.java]
    private val context = fragment.requireActivity()

    private val fragmentManager = fragment.requireParentFragment().parentFragmentManager

    fun setGoals(goals: List<Goal>) {
        this.goalData = goals
        notifyDataSetChanged();
    }

    override fun getChild(listPosition: Int, expandedListPosition: Int)
        = goalData[listPosition].subGoals[expandedListPosition]


    override fun getChildView(
        listPosition: Int,
        expandedListPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val layoutInflater = LayoutInflater.from(context)
        val goal = goalData[listPosition]

        val fillInView = convertView ?: layoutInflater.inflate(R.layout.goals_item_sub, parent, false)

        val titleText: TextView = fillInView.findViewById(R.id.Comp_Title)
        val subtitle: TextView = fillInView.findViewById(R.id.Comp_Subtitle)
        val checkBox: CheckBox = fillInView.findViewById(R.id.goals_item_sub_complete_checkbox)

        val currSubGoal = goal.subGoals[expandedListPosition]

        titleText.text = currSubGoal.title

        if(currSubGoal.completed) {
            checkBox.isChecked = true
            subtitle.text = currSubGoal.completedDate?.let {
                "Completed on ${dateFormat.format(it)}"
            } ?: "Completed"
        } else {
            checkBox.isChecked = false
            subtitle.text = currSubGoal.dueDate.let { "Complete by ${dateFormat.format(it)}" }
        }

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setSubgoalCompleted(goal, currSubGoal, isChecked)
        }

        return fillInView
    }
    override fun getChildrenCount(listPosition: Int) = this.goalData[listPosition].subGoals.size

    override fun getGroup(listPosition: Int) = this.goalData[listPosition]
    override fun getGroupCount() = this.goalData.size

    override fun getGroupId(listPosition: Int) : Long = ExpandableListView.NO_ID.toLong()

    override fun getChildId(p0: Int, p1: Int): Long = ExpandableListView.NO_ID.toLong()

    override fun getGroupView(
        listPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val currGoal = goalData[listPosition]

        val fillInView = convertView ?: LayoutInflater.from(context).inflate(R.layout.goals_item_top, parent, false)

        val titleText : TextView = fillInView.findViewById(R.id.Goal_Title_Text)
        val subtitle : TextView = fillInView.findViewById(R.id.Goal_Subtitle)

        val progressContainer: ViewGroup = fillInView.findViewById(R.id.goals_item_top_progress_bar_container)
        val countText : TextView = fillInView.findViewById(R.id.Goal_Count)
        val topProgress : ProgressBar = fillInView.findViewById(R.id.mf_progress_bar)

        val checkBox : CheckBox = fillInView.findViewById(R.id.goals_item_top_check_box)

        val groupIndicator: ImageView = fillInView.findViewById(R.id.goals_item_top_group_indicator)

        val editButton: Button = fillInView.findViewById(R.id.goals_item_top_edit_button)


        if(currGoal.subGoals.isEmpty()) {
            progressContainer.visibility = View.GONE
            checkBox.visibility = View.VISIBLE

            checkBox.isChecked = currGoal.completed

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                viewModel.setGoalCompleted(currGoal, isChecked)
            }
        } else {
            progressContainer.visibility = View.VISIBLE
            checkBox.visibility = View.GONE

            val completedCount = currGoal.subGoals.count { it.completed }

            topProgress.max = currGoal.subGoals.size
            topProgress.progress = completedCount

            countText.text = "$completedCount / ${currGoal.subGoals.size}"

            val groupIndicatorDrawableId = if(isExpanded)
                R.drawable.goals_group_indicator_anim_fwd
            else
                R.drawable.goals_group_indicator_anim_rev

            val groupIndicatorDrawable = ContextCompat.getDrawable(context, groupIndicatorDrawableId) as AnimatedVectorDrawable
            groupIndicator.setImageDrawable(groupIndicatorDrawable)
            groupIndicatorDrawable.start()

            groupIndicator.setOnClickListener {
                if(isExpanded)
                    listView.collapseGroup(listPosition)
                else
                    listView.expandGroup(listPosition, true)
            }
        }
        titleText.text = currGoal.title

        editButton.setOnClickListener {
            currGoal.uid?.let {
                fragmentManager.commit {
                    replace(R.id.edit_goal_container, EditGoalFragment.newInstanceEditGoal(it,"Edit Goal"))
                    setReorderingAllowed(true)
                    addToBackStack("EditGoalFragment")

                }
            }
        }

        if(currGoal.completed) {
            subtitle.text = currGoal.completedDate?.let {
                "Completed on ${dateFormat.format(it)}"
            } ?: "Completed"
        } else {
            subtitle.text = currGoal.dueDate.let { "Complete by ${dateFormat.format(it)}" }
        }
        return fillInView

    }
    override fun hasStableIds() = false
    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int) = true



}
