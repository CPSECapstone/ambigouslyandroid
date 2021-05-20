package edu.calpoly.flipted.ui.myProgress.missionDetails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.UidToStableId
import edu.calpoly.flipted.businesslogic.missions.TaskStats

class CustomListAdapterTask(
        private val layoutInflater: LayoutInflater,
        private val context: Context

) : BaseAdapter() {

    var data: List<TaskStats> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val uidMap = UidToStableId<String>()

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): TaskStats {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return uidMap.getStableId(data[position].taskId)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val fillInView = convertView
                ?: layoutInflater.inflate(R.layout.mission_task_item, parent, false)
        val taskName: TextView = fillInView.findViewById(R.id.mission_task_name)
        val taskProgressBar: ProgressBar = fillInView.findViewById(R.id.mission_task_progress)
        val taskProgressText: TextView = fillInView.findViewById(R.id.mission_task_percent)
        val data = getItem(position)

        taskName.text = data.name
        if(data.submission != null){
            val progressVal = (data.submission.pointsAwarded.toFloat() /data.submission.pointsPossible.toFloat())*100
            if (progressVal <= 50){
                taskProgressBar.progressDrawable = context.let { ContextCompat.getDrawable(it, R.drawable.progress_bar_failed) }
            }
            if (progressVal < 80 && progressVal >= 50){
                taskProgressBar.progressDrawable = context.let { ContextCompat.getDrawable(it, R.drawable.progress_bar_almost_pass) }
            }
            taskProgressBar.progress = progressVal.toInt()
            val progressString = progressVal.toInt()

            taskProgressText.text = progressString.toString() + "%"
        }
        return fillInView

    }


}

