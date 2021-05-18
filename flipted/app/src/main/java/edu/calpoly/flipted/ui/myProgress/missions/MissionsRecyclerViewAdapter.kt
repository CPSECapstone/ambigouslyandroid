package edu.calpoly.flipted.ui.myProgress.missions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.missions.MissionProgress
import edu.calpoly.flipted.ui.myProgress.missionDetails.MissionTaskFragment


class MissionsViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val progressBar:ProgressBar = view.findViewById(R.id.mission_progress)
    private val missionTitle:TextView = view.findViewById(R.id.mission_item_text)


    fun bind(missionProgress: MissionProgress) {
        missionTitle.text = missionProgress.mission.name
        val completedTaskCount = missionProgress.progress.count { it.submission != null }
        progressBar.max = missionProgress.progress.size
        progressBar.progress = completedTaskCount
    }
}

lateinit var f_manager: FragmentManager

class MissionsRecyclerViewAdapter(private val context: Context): RecyclerView.Adapter<MissionsViewHolder>(){
    var missions:List<MissionProgress> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissionsViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.mission_item, parent, false)
        return MissionsViewHolder(itemView)
    }

    override fun getItemCount(): Int = missions.size

    override fun onBindViewHolder(holder: MissionsViewHolder, position: Int) {
        holder.bind(missions[position])

        val missionDetailButton = holder.itemView.findViewById<TextView>(R.id.mission_item_text)

        missionDetailButton.setOnClickListener(View.OnClickListener() {
            fun onClick(view: View) {
                f_manager.beginTransaction()
                        .replace(R.id.mission_progress, MissionTaskFragment.newInstance("10"))
                        .commit();
            }
        })
    }


}

