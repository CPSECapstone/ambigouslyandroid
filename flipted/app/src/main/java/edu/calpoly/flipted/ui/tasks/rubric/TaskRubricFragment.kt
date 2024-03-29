package edu.calpoly.flipted.ui.tasks.rubric

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.ListView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.UidToStableId
import edu.calpoly.flipted.businesslogic.tasks.data.RubricRequirement
import edu.calpoly.flipted.ui.tasks.ReviewResultsFragment
import edu.calpoly.flipted.ui.tasks.TaskResultsFragment
import edu.calpoly.flipted.ui.tasks.TaskViewModel


class TaskRubricFragment : Fragment() {

    private lateinit var viewModel: TaskViewModel
    private lateinit var list: ListView

    private lateinit var adapter: RubricListAdapter

    private val uidMap = UidToStableId<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.task_rubric_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        list = view.findViewById(R.id.rubric_items_list)


        viewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]

        val task = viewModel.currTask.value ?: throw IllegalArgumentException("Null task")

        val rubricRequirements = task.requirements

        adapter = RubricListAdapter()

        list.adapter = adapter

        adapter.data = rubricRequirements

        viewModel.currResponse.observe(viewLifecycleOwner, Observer {
            val task = viewModel.currTask.value ?: return@Observer
            val err = viewModel.errorMessage.value ?: return@Observer

            if (viewModel.currResponse.value?.taskId != task.uid)
                return@Observer
            if (err.isEmpty() || err.contains("submission").not()) {
                parentFragment?.parentFragmentManager?.popBackStack("Start task", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                parentFragment?.parentFragmentManager?.commit {
                    replace(R.id.main_view, ReviewResultsFragment.newInstance())
                    addToBackStack("Task Results")
                    setReorderingAllowed(true)
                }
            } else {
                val errorMsg = view.findViewById(R.id.submit_error_msg) as TextView
                errorMsg.text = err
                errorMsg.visibility = View.VISIBLE
            }

        })
        val submitButton = view.findViewById<Button>(R.id.task_submit_button)

        viewModel.eligibleForSubmission.observe(viewLifecycleOwner, Observer {
            submitButton.isEnabled = it
        })

        submitButton.setOnClickListener {
            viewModel.submitTask(task.uid)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = TaskRubricFragment()
    }


    inner class RubricListAdapter : BaseAdapter() {

        var data: List<RubricRequirement> = listOf()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun getCount(): Int = data.size

        override fun getItem(p0: Int): RubricRequirement = data[p0]

        override fun getItemId(p0: Int): Long = uidMap.getStableId(data[p0].uid)

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val fillInView = convertView
                    ?: layoutInflater.inflate(R.layout.task_rubric_checkbox, parent, false)
            val checkBox: CheckBox = fillInView.findViewById(R.id.checkBox)

            val data = getItem(position)

            checkBox.text = data.description

            if (convertView == null) {
                checkBox.isChecked = data.isComplete
            }

            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                viewModel.saveRubricRequirement(RubricRequirement(data.description, isChecked, data.uid))
            }


            return fillInView
        }

    }
}