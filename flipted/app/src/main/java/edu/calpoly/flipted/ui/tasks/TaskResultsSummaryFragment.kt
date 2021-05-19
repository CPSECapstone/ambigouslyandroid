package edu.calpoly.flipted.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.UidToStableId


class TaskResultsSummaryFragment : Fragment() {

    private lateinit var viewModel: TaskViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.task_results_fragment_summary, container, false)

    /*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]

        val listViewTask: ListView = view.findViewById(R.id.learning_objectives_list)
        adapter = CustomListAdapter(layoutInflater, requireContext())
        listViewTask.adapter = adapter


    }
    */


    companion object {
        @JvmStatic
        fun newInstance() =
                TaskResultsSummaryFragment()
    }



    /*
  inner class CustomListAdapter(


    ) : BaseAdapter() {

        var data: List<TaskStats> = listOf()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        private val uidMap = UidToStableId<String>()

        override fun getCount(): Int {
            return data.size
        }

        override fun getItem(position: Int): TaskStats {
            return data[position]
        }

        override fun getItemId(position: Int): Long {
            return uidMap.getStableId(data[position].taskId)
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val fillInView = convertView


        }


    }
    */





}