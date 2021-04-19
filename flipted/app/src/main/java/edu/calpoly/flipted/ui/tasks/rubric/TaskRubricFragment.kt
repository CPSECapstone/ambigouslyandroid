package edu.calpoly.flipted.ui.tasks.rubric

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ListView
import androidx.lifecycle.ViewModelProvider
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.tasks.data.RubricRequirement
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

        val rubricRequiments = task.pages.flatMap { page ->
            page.blocks.map { block -> block.requirement }
        }.filterNotNull()

        adapter = RubricListAdapter()

        list.adapter = adapter

        adapter.data = rubricRequiments

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
            checkBox.isChecked = data.isComplete

            return fillInView
        }

    }
}