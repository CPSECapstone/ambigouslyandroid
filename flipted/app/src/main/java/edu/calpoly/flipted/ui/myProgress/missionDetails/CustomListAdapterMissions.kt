package edu.calpoly.flipted.ui.myProgress.missionDetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.UidToStableId
import edu.calpoly.flipted.businesslogic.missions.MissionProgress


class CustomListAdapterMissions(
        private val layoutInflater: LayoutInflater,
        private val activity: FragmentActivity?


) : BaseAdapter() {

    var data: List<MissionProgress> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val uidMap = UidToStableId<String>()

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): MissionProgress {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return uidMap.getStableId(data[position].mission.uid)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val fillInView = convertView
                ?: layoutInflater.inflate(R.layout.mission_item, parent, false)
        val progressBar: ProgressBar = fillInView.findViewById(R.id.mission_progress)
        val missionTitle: TextView = fillInView.findViewById(R.id.mission_item_text)
        val data = getItem(position)

        missionTitle.text =  data.mission.name
        val completedTaskCount = data.progress.count { it.submission != null }
        progressBar.max = data.progress.size
        progressBar.progress = completedTaskCount

        missionTitle.setOnClickListener {

            activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.main_view, MissionTaskFragment.newInstance(data.mission.uid))
                    .addToBackStack(null)
                    .commit()
        }

        return fillInView

    }


}