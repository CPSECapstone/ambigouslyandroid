package edu.calpoly.flipted.ui.myProgress.targets

import android.os.Bundle
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