package edu.calpoly.flipted.ui.myProgress.missions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import edu.calpoly.flipted.R

class MissionProgressFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView : RecyclerView = view.findViewById(R.id.fragment_mission_recycler_view)
        val adapter = MissionsRecyclerViewAdapter(requireActivity())
        recyclerView.adapter = adapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.mission_progress_fragment, container, false)

    companion object {
        @JvmStatic
        fun newInstance() = MissionProgressFragment()
        }
}