package edu.calpoly.flipted.ui.tasks.rubric

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.ListView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.tasks.data.RubricRequirement
import edu.calpoly.flipted.ui.tasks.TaskFragment
import edu.calpoly.flipted.ui.tasks.TaskResultsFragment
import edu.calpoly.flipted.ui.tasks.TaskViewModel


class TaskRubricFragment : Fragment() {

    private lateinit var viewModel : TaskViewModel
    private lateinit var list : ListView

    private lateinit var adapter : RubricListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
        = inflater.inflate(R.layout.task_rubric_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list = view.findViewById(R.id.rubric_items_list)

        viewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]

        // TODO: get task ID from current task for saving progress
        val task = viewModel.currTask.value ?: throw IllegalArgumentException("Null task")

        val rubricRequiments = task.requirements

        adapter = RubricListAdapter()

        list.adapter = adapter

        adapter.data = rubricRequiments

        viewModel.currResponse.observe(viewLifecycleOwner, Observer {
            parentFragment?.parentFragmentManager?.popBackStack("Start Task", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            parentFragment?.parentFragmentManager?.commit {
                replace(R.id.main_view, TaskResultsFragment.newInstance())
                addToBackStack("Task Result")
                setReorderingAllowed(true)
            }
        })
        val submitButton = view.findViewById<Button>(R.id.task_submit_button)
        submitButton.setOnClickListener{
            // TODO submit actual taskId
            viewModel.submitTask("")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = TaskRubricFragment()
    }


    inner class RubricListAdapter : BaseAdapter() {

        var data : List<RubricRequirement> = listOf()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun getCount(): Int = data.size

        override fun getItem(p0: Int): RubricRequirement = data[p0]

        override fun getItemId(p0: Int): Long = data[p0].uid.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val fillInView = convertView ?: layoutInflater.inflate(R.layout.task_rubric_checkbox, parent, false)
            val checkBox : CheckBox = fillInView.findViewById(R.id.checkBox)

            val data = getItem(position)

            checkBox.text = data.description

            return fillInView
        }

    }
}