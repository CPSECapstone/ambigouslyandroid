package edu.calpoly.flipted.ui.myProgress

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.UidToStableId
import edu.calpoly.flipted.businesslogic.targets.CalculateObjectiveMastery
import edu.calpoly.flipted.businesslogic.targets.ObjectiveProgress
import edu.calpoly.flipted.businesslogic.targets.TargetProgress
import edu.calpoly.flipted.businesslogic.targets.TaskObjectiveProgress
import edu.calpoly.flipted.ui.MasteryResources

class LearningObjectivesExpandableListAdapter(
        private val context: Context,
        private val listView: ExpandableListView
) : BaseExpandableListAdapter() {
    var objectives: List<ObjectiveProgress> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val objectiveIds = UidToStableId<String>()
    private val taskIds = UidToStableId<String>()

    override fun getGroupCount(): Int = objectives.size

    override fun getChildrenCount(groupPosition: Int): Int = objectives[groupPosition].tasks.size

    override fun getGroup(groupPosition: Int): ObjectiveProgress = objectives[groupPosition]

    override fun getChild(groupPosition: Int, childPosition: Int): TaskObjectiveProgress
        = objectives[groupPosition].tasks[childPosition]

    override fun getGroupId(groupPosition: Int): Long
        = objectiveIds.getStableId(objectives[groupPosition].objectiveId)

    override fun getChildId(groupPosition: Int, childPosition: Int): Long
        = taskIds.getStableId(objectives[groupPosition].tasks[childPosition].taskId)

    override fun hasStableIds(): Boolean = true

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
        val fillInView = convertView ?: LayoutInflater.from(context).inflate(R.layout.learning_objective_item, parent, false)

        val groupIndicator: ImageView = fillInView.findViewById(R.id.learning_objective_item_group_indicator)
        val masteryIndicator: ImageView = fillInView.findViewById(R.id.learning_objective_item_mastery_indicator)
        val objectiveTitle: TextView = fillInView.findViewById(R.id.learning_objective_item_title)

        groupIndicator.setOnClickListener {
            if(isExpanded)
                listView.collapseGroup(groupPosition)
            else
                listView.expandGroup(groupPosition, true)
        }

        val objectiveProgress = getGroup(groupPosition)

        val objectiveMastery = CalculateObjectiveMastery.execute(objectiveProgress)
        val colorResource = MasteryResources.colorResource(objectiveMastery)
        val color = ResourcesCompat.getColor(context.resources, colorResource, null)
        masteryIndicator.setColorFilter(color)

        objectiveTitle.text = objectiveProgress.objectiveName

        return fillInView
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        val fillInView = convertView ?: LayoutInflater.from(context).inflate(R.layout.learning_objective_task_item, parent, false)

        val taskTitle: TextView = fillInView.findViewById(R.id.learning_objective_task_item_task_name)
        val indicatorBody: ImageView = fillInView.findViewById(R.id.learning_objective_task_item_mastery_indicator_color)
        val indicatorText: TextView = fillInView.findViewById(R.id.learning_objective_task_item_mastery_indicator_text)

        val taskProgress = getChild(groupPosition, childPosition)

        taskTitle.text = taskProgress.taskName

        val colorResource = MasteryResources.colorResource(taskProgress.mastery)
        val color = ResourcesCompat.getColor(context.resources, colorResource, null)
        indicatorBody.setColorFilter(color)

        val stringResource = MasteryResources.stringResource(taskProgress.mastery)
        indicatorText.setText(stringResource)

        return fillInView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = true

}

class LearningTargetViewHolder(view: View, context: Context) : RecyclerView.ViewHolder(view) {
    private val titleText: TextView = view.findViewById(R.id.learning_target_item_title)
    private val objectives: ExpandableListView = view.findViewById(R.id.learning_target_item_objective_list)

    private val adapter = LearningObjectivesExpandableListAdapter(context, objectives)

    init {
        objectives.setAdapter(adapter)
    }

    fun bind(progress: TargetProgress) {
        titleText.text = progress.target.targetName
        adapter.objectives = progress.objectives
    }
}