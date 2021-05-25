package edu.calpoly.flipted.ui.missions

import android.graphics.drawable.LayerDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.missions.MissionProgress
import edu.calpoly.flipted.businesslogic.missions.TaskStats

sealed class MissionTaskViewHolder(view: View) : RecyclerView.ViewHolder(view)

class MissionTitleViewHolder(view: View) : MissionTaskViewHolder(view) {
    private val title: TextView = view.findViewById(R.id.mission_title_item_title)
    private val description: TextView = view.findViewById(R.id.mission_title_item_description)

    fun bind(mission: MissionProgress) {
        title.text = mission.mission.name
        description.text = mission.mission.description
    }
}

class TaskViewHolder(view: View, private val fragment: Fragment) : MissionTaskViewHolder(view) {
    private val indicator: ImageView = view.findViewById(R.id.mission_task_item_indicator)
    private val title: TextView = view.findViewById(R.id.mission_task_item_title)
    private val points: TextView = view.findViewById(R.id.mission_task_item_points)

    fun bind(task: TaskStats) {
        title.text = task.task.name

        val indicatorDrawable = ResourcesCompat.getDrawable(fragment.resources,
            if(task.submission == null) {
                R.drawable.mission_task_exclaim
            } else {
                R.drawable.mission_task_check
            }, null
        )
        indicator.setImageDrawable(indicatorDrawable)

        points.text = "${task.task.points} points"
    }
}

class MissionTaskRecyclerAdapter(private val fragment: Fragment) : RecyclerView.Adapter<MissionTaskViewHolder>() {
    var data : MissionProgress? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissionTaskViewHolder {
        val view = LayoutInflater.from(fragment.requireActivity()).inflate(viewType, parent, false)
        return when(viewType) {
            R.layout.mission_title_item -> MissionTitleViewHolder(view)
            R.layout.mission_task_item -> TaskViewHolder(view, fragment)
            else -> throw IllegalArgumentException("Unknown viewType")
        }
    }

    override fun onBindViewHolder(holder: MissionTaskViewHolder, position: Int) {
        val mission = data ?: return
        when(holder) {
            is MissionTitleViewHolder -> holder.bind(mission)
            is TaskViewHolder -> holder.bind(mission.progress[position - 1])
        }
    }

    override fun getItemCount(): Int = ((data?.mission?.content?.size) ?: 0) + 1

    override fun getItemViewType(position: Int): Int = when(position) {
        0 -> R.layout.mission_title_item
        else -> R.layout.mission_task_item
    }
}