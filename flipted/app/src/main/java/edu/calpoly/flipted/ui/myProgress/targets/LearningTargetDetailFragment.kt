package edu.calpoly.flipted.ui.myProgress.targets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.calpoly.flipted.R

private const val TARGET_ID_ARG_PARAM = "targetId"

class LearningTargetDetailFragment : Fragment() {
    private lateinit var viewModel: TargetsViewModel
    private lateinit var _targetId: String

    private lateinit var title: TextView
    private lateinit var objectivesList: ExpandableListView
    private lateinit var otherTargetsList: RecyclerView

    private lateinit var objectivesAdapter: LearningTargetExpandableListAdapter
    private lateinit var targetsAdapter: LearningTargetCardsAdapter

    private var refreshCount = 0

    var targetId: String
        get() = _targetId
        set(value) {
            _targetId = value

            if(viewModel.allProgress.value == null)
                viewModel.fetchAllTargetProgress()
            else
                bindData()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            _targetId = it.getString(TARGET_ID_ARG_PARAM) ?: throw IllegalArgumentException("Missing parameter")
        } ?: throw IllegalArgumentException("Missing parameter")
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.learning_target_detail_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[TargetsViewModel::class.java]

        title = view.findViewById(R.id.learning_target_detail_title)
        objectivesList = view.findViewById(R.id.learning_target_detail_list)
        otherTargetsList = view.findViewById(R.id.learning_target_detail_other_targets_list)

        objectivesAdapter = LearningTargetExpandableListAdapter(requireActivity())
        objectivesList.setAdapter(objectivesAdapter)

        targetsAdapter = LearningTargetCardsAdapter(this)
        otherTargetsList.adapter = targetsAdapter
        otherTargetsList.layoutManager = LinearLayoutManager(requireActivity())

        viewModel.allProgress.observe(viewLifecycleOwner, Observer { progressMap ->
            val target = progressMap[_targetId]

            if(target == null){
                // We couldn't find the task with the given taskId.
                // It's possible our cache is just out of date, so pull new data from the backend.
                // However, we don't want to get caught in an endless cycle of refreshing from the
                // backend if it turns out the targetId actually doesn't exist.
                // Solution: keep a count of how many times we've refreshed, and give up if we
                // don't find the task after refreshing a couple of times
                refreshCount += 1
                if(refreshCount < 3) {
                    viewModel.fetchAllTargetProgress()
                } else {
                    Log.e("LearningTargetDetailFragment", "Could not find learning target with id $_targetId")
                }
                return@Observer
            }

            bindData()
        })

        if(viewModel.allProgress.value == null)
            viewModel.fetchAllTargetProgress()
    }

    private fun bindData() {
        val progressMap = viewModel.allProgress.value ?: throw IllegalStateException("Attempt to bind missing data")
        val target = progressMap[_targetId] ?: throw IllegalStateException("Attempt to bind missing data")
        title.text = target.target.targetName
        objectivesAdapter.objectives = target.objectives

        targetsAdapter.targets = progressMap.values.filter{it.target.uid != _targetId}
        targetsAdapter.notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        fun newInstance(targetId: String) =
                LearningTargetDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(TARGET_ID_ARG_PARAM, targetId)
                }
            }
    }

}