package edu.calpoly.flipted.ui.tasks

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.tasks.data.Page
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.QuizBlock
import edu.calpoly.flipted.ui.myProgress.missions.MissionProgressFragment
import edu.calpoly.flipted.ui.myProgress.targets.LearningTargetsFragment
import edu.calpoly.flipted.ui.tasks.viewholders.TaskRecyclerViewAdapter
import edu.calpoly.flipted.ui.tasks.viewholders.TaskResultsRecyclerViewAdapter
import java.lang.IllegalStateException

/**
 * A simple [Fragment] subclass.
 * Use the [ReviewResultsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReviewResultsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.results_review_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pageTitle : TextView = view.findViewById(R.id.review_results_page_name)
        val taskTitle: TextView = view.findViewById(R.id.review_results_task_name)
        val tabs: TabLayout = view.findViewById(R.id.results_pager_tabs)
        val pager: ViewPager2 = view.findViewById(R.id.results_pager)

        val viewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]

        val currTask = viewModel.currTask.value
                ?: throw IllegalStateException("No task found")
        pageTitle.text = "QUIZ REVIEW"
        taskTitle.text = currTask.name

        pager.adapter = object: FragmentStateAdapter(this) {
            override fun getItemCount(): Int = 3

            override fun createFragment(position: Int): Fragment = when(position) {
                0 -> TaskResultsFragment.newInstance()
                1 -> TaskResultsFragment.newInstance()
                2 -> TaskResultsFragment.newInstance()
                else -> throw IllegalArgumentException("Invalid ViewPager page")
            }

        }

        TabLayoutMediator(tabs, pager) { tab, position ->
            tab.text = when (position) {
                0 -> "Quiz Review"
                1 -> "Task Results"
                2 -> "Get Help"
                else -> throw IllegalArgumentException("Invalid TabLayoutMediator page")
            }
            pageTitle.text = when (position) {
                0 -> "QUIZ REVIEW"
                1 -> "TASK RESULTS"
                2 -> "GET HELP"
                else -> throw IllegalArgumentException("Invalid TabLayoutMediator page")
            }
        }.attach()

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ReviewResultsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
                ReviewResultsFragment()
    }
}