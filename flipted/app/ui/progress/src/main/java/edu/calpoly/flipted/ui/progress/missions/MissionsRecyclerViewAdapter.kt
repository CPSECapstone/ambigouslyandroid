package edu.calpoly.flipted.ui.progress.missions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import edu.calpoly.flipted.businesslogic.missions.MissionProgress
import edu.calpoly.flipted.ui.progress.R
import edu.calpoly.flipted.viewmodels.NavViewModel


class MissionsViewHolder(private val fragment: Fragment, view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
    private val progressBar: ProgressBar = view.findViewById(R.id.mission_progress)
    private val missionTitle: TextView = view.findViewById(R.id.mission_item_text)

    private val navViewModel = ViewModelProvider(fragment.requireActivity())[NavViewModel::class.java]

    private lateinit var missionId: String

    fun bind(missionProgress: MissionProgress) {
        itemView.setOnClickListener(this)
        missionId = missionProgress.mission.uid

        missionTitle.text = missionProgress.mission.name
        val completedTaskCount = missionProgress.progress.count { it.submission != null }
        progressBar.max = missionProgress.progress.size
        progressBar.progress = completedTaskCount

    }

    override fun onClick(v: View?) {
        fragment.parentFragment?.parentFragmentManager?.let{
            navViewModel.navigator.openMissionProgressDetailsFragment(it, missionId)
        }
    }
}


class MissionsRecyclerViewAdapter(private val fragment: Fragment): RecyclerView.Adapter<MissionsViewHolder>(){
    var missions:List<MissionProgress> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissionsViewHolder {
        val itemView = LayoutInflater.from(fragment.requireActivity()).inflate(R.layout.mission_progress_item, parent, false)
        return MissionsViewHolder(fragment, itemView)
    }

    override fun getItemCount(): Int = missions.size

    override fun onBindViewHolder(holder: MissionsViewHolder, position: Int) {
        holder.bind(missions[position])
    }


}

