package edu.calpoly.flipted.ui.myProgress.targets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
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

class LearningTargetsFragment : Fragment() {
    private lateinit var viewModel: TargetsViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.learning_targets_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[TargetsViewModel::class.java]

        val targetRecyclerView: RecyclerView = view.findViewById(R.id.learning_targets_container)
        val allTargets: TextView = view.findViewById(R.id.all_targets_text)
        val namesList: ListView = view.findViewById(R.id.learning_target_names_list)

        val targetsAdapter = LearningTargetsAdapter(this)
        val namesAdapter = TargetNamesListAdapter()

        targetRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        targetRecyclerView.adapter = targetsAdapter

        namesList.adapter = namesAdapter

        allTargets.setOnClickListener {
            viewModel.clearSelection()
        }

        viewModel.selectedLearningTargets.observe(viewLifecycleOwner, Observer {
            allTargets.setTextColor(ContextCompat.getColor(requireContext(),
                if (it.isEmpty()) R.color.black else R.color.gray2
            ))

        })

        viewModel.selectedLearningTargets.observe(viewLifecycleOwner, Observer { selectedTargets ->
            val allProgress = viewModel.allProgress.value ?: listOf()
            targetsAdapter.selectedTargets = if(selectedTargets.isEmpty())
                allProgress
            else
                allProgress.filter{it.target in selectedTargets}

            val targets = viewModel.allProgress.value?.map{
                it.target
            } ?: listOf()
            namesAdapter.data = targets
        })

        viewModel.fetchAllTargetProgress()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                LearningTargetsFragment()
    }


    inner class TargetNamesListAdapter : BaseAdapter() {
        private val uidMap = UidToStableId<String>()

        var data: List<LearningTarget> = listOf()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun getCount(): Int = data.size

        override fun getItem(p0: Int): LearningTarget = data[p0]

        override fun getItemId(p0: Int): Long = uidMap.getStableId(data[p0].uid)

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val fillInView = convertView
                ?: layoutInflater.inflate(R.layout.learning_target_names_item, parent, false)
            val targetText: TextView = fillInView.findViewById(R.id.learning_target_name_text)

            val data = getItem(position)

            targetText.text = data.targetName

            val selectedTargets = viewModel.selectedLearningTargets.value ?: setOf()
            targetText.setTextColor(
                ContextCompat.getColor(requireContext(),
                if (selectedTargets.contains(data)) {
                    R.color.black
                } else {
                    R.color.gray2
                }
            ))

            viewModel.selectedLearningTargets.observe(viewLifecycleOwner, Observer {
                targetText.setTextColor(
                    ContextCompat.getColor(requireContext(),
                    if (it.contains(data)) {
                        R.color.black
                    } else {
                        R.color.gray2
                    }
                ))
            })

            targetText.setOnClickListener { _ ->
                viewModel.toggleSelectTarget(data)
            }
            return fillInView
        }

    }

}