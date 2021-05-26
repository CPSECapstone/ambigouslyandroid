package edu.calpoly.flipted.ui.missions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
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

class TaskViewHolder(view: View, private val fragment: Fragment, private val adapter: MissionTaskRecyclerAdapter)
    : MissionTaskViewHolder(view), View.OnClickListener {
    private val indicator: ImageView = view.findViewById(R.id.mission_task_item_indicator)
    private val title: TextView = view.findViewById(R.id.mission_task_item_title)
    private val points: TextView = view.findViewById(R.id.mission_task_item_points)

    private var pos : Int? = null
    private lateinit var taskId: String

    init {
        view.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        adapter.selectedIdx = pos
        //TODO: Load the sidebar with the selected item
        /*
            fragment.childFragmentManager.commit {
                replace(R.id.<sidebar_container_id>, <sidebar fragment>.newInstance(taskId))
                setReorderingAllowed(true)
            }
         */
    }

    fun bind(task: TaskStats, pos: Int) {
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

        this.pos = pos
        taskId = task.task.id
    }
}

class SelectedTaskViewHolder(view: View) : MissionTaskViewHolder(view) {
    private val title: TextView = view.findViewById(R.id.mission_selected_item_title)
    private val points: TextView = view.findViewById(R.id.mission_selected_item_points)
    private val progress: ProgressBar = view.findViewById(R.id.mission_selected_item_progress)

    fun bind(task: TaskStats) {
        title.text = task.task.name
        points.text = "${task.task.points} points"

        progress.max = task.task.points
        progress.progress = task.submission?.pointsAwarded ?: 0
    }

}

class MissionTaskRecyclerAdapter(private val fragment: Fragment) : RecyclerView.Adapter<MissionTaskViewHolder>() {
    var data : MissionProgress? = null
    var selectedIdx: Int? = null
        set(value) {
            val oldVal = field
            field = value
            if(oldVal != null)
                notifyItemChanged(oldVal)
            if(value != null)
                notifyItemChanged(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissionTaskViewHolder {
        val view = LayoutInflater.from(fragment.requireActivity()).inflate(viewType, parent, false)
        return when(viewType) {
            R.layout.mission_title_item -> MissionTitleViewHolder(view)
            R.layout.mission_task_item -> TaskViewHolder(view, fragment, this)
            R.layout.mission_selected_item -> SelectedTaskViewHolder(view)
            else -> throw IllegalArgumentException("Unknown viewType")
        }
    }

    override fun onBindViewHolder(holder: MissionTaskViewHolder, position: Int) {
        val mission = data ?: return
        when(holder) {
            is MissionTitleViewHolder -> holder.bind(mission)
            is TaskViewHolder -> holder.bind(mission.progress[position - 1], position)
            is SelectedTaskViewHolder -> holder.bind(mission.progress[position - 1])
        }
    }

    override fun getItemCount(): Int = ((data?.mission?.content?.size) ?: 0) + 1

    override fun getItemViewType(position: Int): Int = when(position) {
        0 -> R.layout.mission_title_item
        selectedIdx -> R.layout.mission_selected_item
        else -> R.layout.mission_task_item
    }
}