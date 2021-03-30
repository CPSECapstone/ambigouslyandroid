package com.example.flipted.ui.goals
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ProgressBar
import android.widget.TextView
import com.example.flipted.R
import com.example.flipted.businesslogic.goals.Goal
import java.time.*
import kotlin.math.*

class CustomExpandableListAdapter public constructor(
    private val context: Context
) : BaseExpandableListAdapter() {

    private var goalData: List<Goal> = listOf()

    fun setGoals(goals: List<Goal>) {
        this.goalData = goals
        notifyDataSetChanged();
    }

    override fun getChild(listPosition: Int, expandedListPosition: Int) : Any {
        val goal = goalData[listPosition]

        return if (expandedListPosition >= goal.completions.size)
        // No data for the "Add completion" button
            Object()
        else
            goal.completions[expandedListPosition]
    }
    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }
    override fun getChildView(
        listPosition: Int,
        expandedListPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val layoutInflater = LayoutInflater.from(context)
        val goal = goalData[listPosition]


        if(expandedListPosition >= goal.completions.size)
            return if(convertView != null && convertView.id == R.layout.goals_item_mark_progress )
                convertView
            else
                layoutInflater.inflate(R.layout.goals_item_mark_progress, parent, false)

        val fillinView = if (convertView != null && convertView.id == R.layout.goals_item_sub)
            convertView
        else
            layoutInflater.inflate(R.layout.goals_item_sub, parent, false)

        val currComp = goal.completions[expandedListPosition]
        val compDate = currComp.completedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        val month = compDate.month.toString().get(0)  + compDate.month.toString().substring(1).toLowerCase()

        val titleText = fillinView.findViewById(R.id.Comp_Title) as TextView
        val subtitle = fillinView.findViewById(R.id.Comp_Subtitle) as TextView

        titleText.text = currComp.description
        subtitle.text = "Completed on ${month} ${compDate.dayOfMonth}, ${compDate.year}"


        return fillinView
    }
    override fun getChildrenCount(listPosition: Int) =
        (this.goalData[listPosition].completions.size + 1)

    override fun getGroup(listPosition: Int) = this.goalData[listPosition]
    override fun getGroupCount() = this.goalData.size

    override fun getGroupId(listPosition: Int) = this.goalData[listPosition].uid.toLong()

    override fun getGroupView(
        listPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val currGoal = goalData[listPosition]

        val convertView = convertView ?: LayoutInflater.from(context).inflate(R.layout.goals_item_top, parent, false)

        val titleText : TextView = convertView.findViewById(R.id.Goal_Title_Text)
        val subtitle : TextView = convertView.findViewById(R.id.Goal_Subtitle)
        val countText : TextView = convertView.findViewById(R.id.Goal_Count)
        val topProgress : ProgressBar = convertView.findViewById(R.id.mf_progress_bar)
        val goalDate = currGoal.dueDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        val month = goalDate.month.toString().get(0)  + goalDate.month.toString().substring(1).toLowerCase()
        val percentDone = ((currGoal.completions.size.toDouble()/currGoal.targetCompletionCount)*100).roundToInt()

        titleText.text = currGoal.title
        subtitle.text = "Target: ${currGoal.title} by ${month} ${goalDate.dayOfMonth}, ${goalDate.year}"
        countText.text = "${currGoal.completions.size} ${currGoal.unitOfMeasurement}"
        topProgress.progress = percentDone
        return convertView

    }
    override fun hasStableIds(): Boolean {
        return false
    }
    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }




}