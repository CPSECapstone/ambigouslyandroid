package edu.calpoly.flipted.ui.myProgress

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.targets.CalculateObjectiveMastery
import edu.calpoly.flipted.businesslogic.targets.TargetProgress
import edu.calpoly.flipted.ui.MasteryResources

class LearningTargetViewHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
    private val titleText: TextView = view.findViewById(R.id.learning_target_item_title)
    private val objectiveContainer: LinearLayout = view.findViewById(R.id.learning_target_item_objective_list)

    private var expandedMap: MutableMap<String, Boolean> = mutableMapOf()

    fun bind(progress: TargetProgress) {
        titleText.text = progress.target.targetName

        objectiveContainer.removeAllViews()

        progress.objectives.forEach {objectiveProgress ->
            val fillInObjective = LayoutInflater.from(context).inflate(R.layout.learning_objective_item, objectiveContainer, false)

            val groupIndicator: ImageView = fillInObjective.findViewById(R.id.learning_objective_item_group_indicator)
            val masteryIndicator: ImageView = fillInObjective.findViewById(R.id.learning_objective_item_mastery_indicator)
            val objectiveTitle: TextView = fillInObjective.findViewById(R.id.learning_objective_item_title)
            val taskContainer: LinearLayout = fillInObjective.findViewById(R.id.learning_objective_item_task_container)

            fillInObjective.setOnClickListener{
                // Note: the equality comparison with a boolean value is necessary because of the implicit null check
                if(expandedMap[objectiveProgress.objectiveId] == true) {
                    expandedMap[objectiveProgress.objectiveId] = false
                    taskContainer.removeAllViews()
                } else {
                    expandedMap[objectiveProgress.objectiveId] = true
                    objectiveProgress.tasks.forEach { taskProgress ->
                        val fillInTask = LayoutInflater.from(context).inflate(R.layout.learning_objective_task_item, objectiveContainer, false)

                        val taskTitle: TextView = fillInTask.findViewById(R.id.learning_objective_task_item_task_name)
                        val indicatorBody: ImageView = fillInTask.findViewById(R.id.learning_objective_task_item_mastery_indicator_color)
                        val indicatorText: TextView = fillInTask.findViewById(R.id.learning_objective_task_item_mastery_indicator_text)

                        taskTitle.text = taskProgress.taskName

                        val colorResource = MasteryResources.colorResource(taskProgress.mastery)
                        val color = ResourcesCompat.getColor(context.resources, colorResource, null)
                        indicatorBody.setColorFilter(color)

                        val stringResource = MasteryResources.stringResource(taskProgress.mastery)
                        indicatorText.setText(stringResource)

                        taskContainer.addView(fillInTask)
                    }
                }
            }

            val objectiveMastery = CalculateObjectiveMastery.execute(objectiveProgress)
            val colorResource = MasteryResources.colorResource(objectiveMastery)
            val color = ResourcesCompat.getColor(context.resources, colorResource, null)
            masteryIndicator.setColorFilter(color)

            objectiveTitle.text = objectiveProgress.objectiveName

            objectiveContainer.addView(fillInObjective)
        }
    }
}