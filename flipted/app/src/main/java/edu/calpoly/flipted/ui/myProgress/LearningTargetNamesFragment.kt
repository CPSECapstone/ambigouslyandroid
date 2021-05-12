package edu.calpoly.flipted.ui.myProgress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.UidToStableId
import edu.calpoly.flipted.businesslogic.learningTargets.LearningTarget
import edu.calpoly.flipted.businesslogic.tasks.data.RubricRequirement

class LearningTargetNamesFragment  : Fragment() {

    private lateinit var viewModel: TargetsViewModel
    private lateinit var list: ListView
    private lateinit var allTargets: TextView

    private lateinit var adapter: LearningTargetNamesFragment.TargetNamesListAdapter

    private val uidMap = UidToStableId<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.learning_target_names_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        list = view.findViewById(R.id.learning_target_names_list)
        allTargets = view.findViewById(R.id.all_targets_text)

        viewModel = ViewModelProvider(requireActivity())[TargetsViewModel::class.java]

        val targetMap = viewModel.targetMap.value ?: throw IllegalArgumentException("Null target map")

        val targetNames = targetMap.keys.toList()

        adapter = TargetNamesListAdapter()

        list.adapter = adapter

        adapter.data = targetNames

        allTargets.setOnClickListener {
            viewModel.toggleAllTargets()
            allTargets.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                LearningTargetNamesFragment()
    }

    inner class TargetNamesListAdapter : BaseAdapter() {

        var data: List<LearningTarget> = listOf()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun getCount(): Int = data.size

        override fun getItem(p0: Int): LearningTarget = data[p0]

        override fun getItemId(p0: Int): Long = uidMap.getStableId(data[p0].uid)

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val fillInView = convertView ?: layoutInflater.inflate(R.layout.learning_target_names_item, parent, false)
            val targetText : TextView = fillInView.findViewById(R.id.learning_target_name_text)

            val data = getItem(position)

            targetText.text = data.targetName

            //color relates to if in current list or not
            targetText.setOnClickListener{ view ->
                val result = viewModel.updateSelectedTargets(data.uid)
                if (viewModel.allSelected.value!!) {
                    allTargets.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray2))
                }
                if (result) {
                    targetText.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                }
                else {
                targetText.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray2))
                }
            }
            return fillInView
        }

    }
}