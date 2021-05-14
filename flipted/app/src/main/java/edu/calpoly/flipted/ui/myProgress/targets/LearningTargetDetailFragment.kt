package edu.calpoly.flipted.ui.myProgress.targets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ExpandableListView
import android.widget.ListView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.UidToStableId
import edu.calpoly.flipted.businesslogic.targets.LearningTarget

private const val TARGET_ID_ARG_PARAM = "targetId"

class LearningTargetDetailFragment : Fragment() {
    private lateinit var viewModel: TargetsViewModel
    private var targetId: String? = null

    private var refreshCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            targetId = it.getString(TARGET_ID_ARG_PARAM)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.learning_target_detail_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[TargetsViewModel::class.java]

        val title: TextView = view.findViewById(R.id.learning_target_detail_title)
        val objectivesList: ExpandableListView = view.findViewById(R.id.learning_target_detail_list)
        val otherTargetsList: RecyclerView = view.findViewById(R.id.learning_target_detail_other_targets_list)

        if(targetId == null)
            throw IllegalStateException("No targetId provided")

        val objectivesAdapter = LearningTargetExpandableListAdapter(requireActivity())
        objectivesList.setAdapter(objectivesAdapter)

        viewModel.allProgress.observe(viewLifecycleOwner, Observer { progressMap ->
            val target = progressMap[targetId]

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
                    Log.e("LearningTargetDetailFragment", "Could not find learning target with id $targetId")
                }
                return@Observer
            }

            title.text = target.target.targetName
            objectivesAdapter.objectives = target.objectives
            // TODO: propagate the data to the adapters for the otherTargetsList
        })

        if(viewModel.allProgress.value == null)
            viewModel.fetchAllTargetProgress()
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