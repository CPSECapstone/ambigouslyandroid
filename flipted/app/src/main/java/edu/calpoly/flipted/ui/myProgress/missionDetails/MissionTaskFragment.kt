package edu.calpoly.flipted.ui.myProgress.missionDetails

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

private const val MISSION_ID_ARG_PARAM = "Integrated Science"

class MissionTaskFragment() : Fragment() {

    private lateinit var viewModel: MissionTaskViewModel
    private var missionId: String? = null
    private var refreshCount = 0
    private lateinit var adapter: CustomListAdapterTask
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
    ): View = inflater.inflate(R.layout.mission_progress_detail_fragment, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[MissionTaskViewModel::class.java]


        val listViewTask: ListView = view.findViewById(R.id.mission_task_list_view)
        val missionTitle: TextView = view.findViewById(R.id.mission_task_item_title)
        val listViewMission: ListView = view.findViewById(R.id.missions_container_list)

        adapter = CustomListAdapterTask(layoutInflater, requireContext())
        listViewTask.adapter = adapter
        adapterMissions = CustomListAdapterMissions(layoutInflater,activity)
        listViewMission.adapter = adapterMissions

        viewModel.allMissions.observe(viewLifecycleOwner, Observer { progressMap ->
            val mission = progressMap[missionId]

            if (mission == null) {
                refreshCount += 1
                if (refreshCount < 3) {
                    viewModel.fetchTaskStats()
                } else {
                    Log.e("MissionTaskFragment", "Could not find mission with id $missionId")
                }
                return@Observer
            }

            adapter.data = mission.progress
            missionTitle.text = mission.mission.name
            adapterMissions.data = progressMap.values.toList()
        })


        if(viewModel.allMissions.value == null)
            viewModel.fetchTaskStats()

    }

    companion object {
        @JvmStatic
        fun newInstance(MissionId: String) = MissionTaskFragment().apply {
            arguments = Bundle().apply {
                putString(MISSION_ID_ARG_PARAM, MissionId)
            }
        }
    }
}