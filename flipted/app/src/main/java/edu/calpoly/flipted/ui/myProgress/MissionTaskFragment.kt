package edu.calpoly.flipted.ui.myProgress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import edu.calpoly.flipted.R

class MissionTaskFragment() : Fragment() {

    private lateinit var viewModel: MissionTaskViewModel
    private lateinit var listView: ListView
    private lateinit var adapter: CustomListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.mission_task_fragment_exp_list, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MissionTaskViewModel::class.java]
        adapter = CustomListAdapter(this,listView)
        listView.adapter = adapter

        viewModel.fetchTasks()

    }

    companion object {
        @JvmStatic
        fun newInstance() = MissionTaskFragment()
    }

}