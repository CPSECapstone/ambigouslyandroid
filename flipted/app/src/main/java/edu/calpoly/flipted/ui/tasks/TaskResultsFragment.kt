package edu.calpoly.flipted.ui.tasks

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.tasks.data.Page
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.QuizBlock
import edu.calpoly.flipted.ui.tasks.viewholders.TaskRecyclerViewAdapter
import java.lang.IllegalStateException

/**
 * A simple [Fragment] subclass.
 * Use the [TaskResultsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskResultsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.task_results_fragment, container, false)
    }
/*
    override fun isAutoMeasureEnabled(): Boolean {
        return true
    }
*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pointsAwarded : TextView = view.findViewById(R.id.total_awarded_points)
        val hasBeenGraded : TextView = view.findViewById(R.id.has_been_graded)

        val viewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]

        val currResponse = viewModel.currResponse.value
                ?: throw IllegalStateException("No response found")
        val currTask = viewModel.currTask.value
                ?: throw IllegalStateException("No task found")
        pointsAwarded.text = "${currResponse.pointsAwarded} out of ${currResponse.pointsPossible} points"

        if (currResponse.graded) {
            hasBeenGraded.text = "This is your final score."
        }
        else {
            hasBeenGraded.text = "Some of the questions have not been graded yet."
        }

        val recyclerView : RecyclerView = view.findViewById(R.id.task_results_recyclerview)

        val adapter = TaskRecyclerViewAdapter(this)

        val blocks = mutableListOf<QuizBlock>()

        recyclerView.adapter = adapter

        val layoutManager = LinearLayoutManager(requireActivity())


        layoutManager.setAutoMeasureEnabled(true)
        recyclerView.layoutManager = layoutManager

        recyclerView.setNestedScrollingEnabled(false)

        currTask.pages.forEach { page ->
            blocks.addAll(page.blocks.filterIsInstance<QuizBlock>())
        }

        adapter.taskBlocks = blocks

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment TaskResultsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
                TaskResultsFragment()
    }
}