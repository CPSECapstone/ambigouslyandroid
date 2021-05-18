package edu.calpoly.flipted.ui.myProgress.missions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import edu.calpoly.flipted.R
import edu.calpoly.flipted.ui.myProgress.MissionTaskFragment

class MissionProgressFragment : Fragment() {

    private lateinit var viewModel: MissionsViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView : RecyclerView = view.findViewById(R.id.fragment_mission_recycler_view)
        val adapter = MissionsRecyclerViewAdapter(requireActivity())
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(requireActivity())[MissionsViewModel::class.java]

        viewModel.missionsProgress.observe(viewLifecycleOwner, Observer {
            adapter.missions = it
            adapter.notifyDataSetChanged()
        })

        viewModel.fetchMissionsProgress()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.mission_progress_fragment, container, false)

    companion object {
        @JvmStatic
        fun newInstance() = MissionProgressFragment()
        }
}