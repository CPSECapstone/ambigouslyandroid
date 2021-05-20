package edu.calpoly.flipted.ui.myProgress.targets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import edu.calpoly.flipted.R

class LearningTargetProgressFragment : Fragment() {

    private lateinit var viewModel: TargetsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView : RecyclerView = view.findViewById(R.id.fragment_learning_target_recycler_view)
        val adapter = LearningTargetsRecyclerViewAdapter(this)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(requireActivity())[TargetsViewModel::class.java]

        viewModel.allProgress.observe(viewLifecycleOwner, Observer {
            adapter.targets = it.values.toList()
            adapter.notifyDataSetChanged()
        })

        viewModel.fetchAllTargetProgress()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.learning_target_progress_fragment, container, false)

    companion object {
        @JvmStatic
        fun newInstance() = LearningTargetProgressFragment()
    }
}