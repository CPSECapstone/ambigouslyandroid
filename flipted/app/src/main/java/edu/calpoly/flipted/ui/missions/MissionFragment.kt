package edu.calpoly.flipted.ui.missions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.calpoly.flipted.R
import edu.calpoly.flipted.ui.myProgress.missions.MissionsViewModel

private const val MISSION_ID_ARG_PARAM = "missionId"

/**
 * A simple [Fragment] subclass.
 * Use the [MissionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MissionFragment : Fragment() {

    private lateinit var missionId : String
    private lateinit var viewModel : MissionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            missionId = it.getString(MISSION_ID_ARG_PARAM) ?: throw IllegalArgumentException("Missing parameter")
        } ?: throw IllegalArgumentException("Missing parameter")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.mission_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val taskList: RecyclerView = view.findViewById(R.id.mission_tasks_recyclerview)
        val adapter = MissionTaskRecyclerAdapter(this)

        taskList.adapter = adapter
        taskList.layoutManager = LinearLayoutManager(requireActivity())
        taskList.addItemDecoration(MissionTasksItemDecoration(this))

        viewModel = ViewModelProvider(requireActivity())[MissionsViewModel::class.java]

        viewModel.missionsProgress.observe(viewLifecycleOwner, Observer {
            adapter.data = it[missionId]
            adapter.notifyDataSetChanged()
        })

        viewModel.fetchMissionsProgress()


    }

    companion object {
        @JvmStatic
        fun newInstance(missionId: String)
            = MissionFragment().apply {
                arguments = Bundle().apply {
                    putString(MISSION_ID_ARG_PARAM, missionId)
                }
            }
        }
}