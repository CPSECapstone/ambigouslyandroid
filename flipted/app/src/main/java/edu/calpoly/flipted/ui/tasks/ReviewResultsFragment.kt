package edu.calpoly.flipted.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import edu.calpoly.flipted.R
import edu.calpoly.flipted.ui.missions.MissionFragment

/**
 * A simple [Fragment] subclass.
 * Use the [ReviewResultsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReviewResultsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.results_review_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pageTitle: TextView = view.findViewById(R.id.review_results_page_name)
        val taskTitle: TextView = view.findViewById(R.id.review_results_task_name)
        val tabs: TabLayout = view.findViewById(R.id.results_pager_tabs)
        val pager: ViewPager2 = view.findViewById(R.id.results_pager)
        val taskBtn: Button = view.findViewById(R.id.results_review_btn)
        val continueBtn: Button = view.findViewById(R.id.results_continue_learning_btn)

        val viewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]

        val currTask = viewModel.currTask.value
            ?: throw IllegalStateException("No task found")
        pageTitle.text = "QUIZ REVIEW"
        taskTitle.text = currTask.name

        pager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = 3

            override fun createFragment(position: Int): Fragment = when (position) {
                0 -> TaskResultsFragment.newInstance()
                1 -> TaskResultsSummaryFragment.newInstance()
                2 -> TaskResultsHelpFragment.newInstance()
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
        }.attach()

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                // Handle tab select
                pageTitle.text = when (tab!!.text) {
                    "Quiz Review" -> "QUIZ REVIEW"
                    "Task Results" -> "TASK RESULTS"
                    "Get Help" -> "GET HELP"
                    else -> throw IllegalStateException()
                }
                val params = pager.layoutParams
                params.height = when (tab!!.text) {
                    "Quiz Review" -> FrameLayout.LayoutParams.MATCH_PARENT
                    "Task Results" -> 1400
                    "Get Help" -> 750
                    else -> throw IllegalStateException()
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
        })

        taskBtn.setOnClickListener {
            parentFragmentManager.popBackStack(
                "Task Results",
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            parentFragmentManager.commit {
                replace(R.id.main_view, TaskFragment.newInstance(currTask.uid))
                addToBackStack("Start task")
                setReorderingAllowed(true)
            }
        }

        continueBtn.setOnClickListener {
            parentFragmentManager.popBackStack(
                "Task Results",
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            parentFragmentManager.commit {
                replace(R.id.main_view, MissionFragment.newInstance())
                addToBackStack(null)
                setReorderingAllowed(true)
            }
        }

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