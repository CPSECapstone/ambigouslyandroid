package edu.calpoly.flipted.ui.myProgress

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.UidToStableId
import edu.calpoly.flipted.businesslogic.missions.MissionProgress
import edu.calpoly.flipted.businesslogic.missions.TaskStats


private const val MISSION_ID_ARG_PARAM = "Integrated Science"

class MissionTaskFragment() : Fragment() {

    private lateinit var viewModel: MissionTaskViewModel
    private var missionId: String? = null
    private var refreshCount = 0
    private lateinit var adapter: CustomListAdapter
    private lateinit var adapterMissions: CustomListAdapterMissions



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            missionId = it.getString(MISSION_ID_ARG_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.mission_task_fragment, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[MissionTaskViewModel::class.java]


        val listViewTask: ListView = view.findViewById(R.id.mission_task_list_view)
        val missionTitle: TextView = view.findViewById(R.id.mission_task_item_title)
        val listViewMission: ListView = view.findViewById(R.id.missions_container_list)


        adapter = CustomListAdapter()
        listViewTask.adapter = adapter
        adapterMissions = CustomListAdapterMissions()
        listViewMission.adapter = adapterMissions


        viewModel.allMissions.observe(viewLifecycleOwner, Observer { progressMap ->
            val mission = progressMap[missionId]

            if(mission == null){
                refreshCount += 1
                if(refreshCount < 3) {
                    viewModel.fetchTaskStats()
                } else {
                    Log.e("MissionTaskFragment", "Could not find task with id $missionId")
                }
                return@Observer
            }

            adapter.data = mission.progress
            missionTitle.text = mission.mission.name
            adapterMissions.data = progressMap.values.toList()
        })


       // val missions = viewModel.missionsProgress.value?: throw IllegalArgumentException("Null missions")

       // listViewMission.adapter = adapterMissions
        //adapterMissions.data = missions




        if(viewModel.allMissions.value == null)
            viewModel.fetchTaskStats()

    }

    companion object {
        @JvmStatic
        fun newInstance(MissionId: String) = MissionTaskFragment().apply {
            arguments = Bundle().apply {
                putString(MISSION_ID_ARG_PARAM,MissionId)
            }
        }
    }

  inner class CustomListAdapter(

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
                taskProgressBar.progress = (data.submission.pointsAwarded /data.submission.pointsPossible)*100
                taskProgressText.text =  ((data.submission.pointsAwarded /data.submission.pointsPossible)*100).toString() + "%"
            }
            return fillInView

        }


    }



inner class CustomListAdapterMissions(

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
            val progressBar:ProgressBar = fillInView.findViewById(R.id.mission_progress)
            val missionTitle:TextView = fillInView.findViewById(R.id.mission_item_text)
            val data = getItem(position)

            missionTitle.text =  data.mission.name
            val completedTaskCount = data.progress.count { it.submission != null }
            progressBar.max = data.progress.size
            progressBar.progress = completedTaskCount

            return fillInView

        }


    }


}