package edu.calpoly.flipted.ui.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

import edu.calpoly.flipted.R

private const val TASKID_ARG_PARAM = "taskId"

/**
 * A simple [Fragment] subclass.
 * Use the [QuizFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskFragment : Fragment() {
    private var taskId: Int? = null

    private lateinit var progressBar : ProgressBar
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager : ViewPager2

    private lateinit var viewModel : TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            taskId = it.getInt(TASKID_ARG_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.task_pager_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = taskId ?: throw IllegalStateException("No taskId provided")

        viewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]

        progressBar = view.findViewById(R.id.task_pager_progressbar)
        tabLayout = view.findViewById(R.id.task_pager_tabs)
        viewPager = view.findViewById(R.id.task_pager)

        val pagerAdapter = TaskPagerAdapter(this)

        viewModel.currTask.observe(viewLifecycleOwner, Observer {
            progressBar.visibility = View.GONE
            tabLayout.visibility = View.VISIBLE
            viewPager.visibility = View.VISIBLE

            pagerAdapter.pages = it.pages
        })
        viewModel.fetchTask(id)

        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "Page ${position + 1}"
        }.attach()

    }

    companion object {
        @JvmStatic
        fun newInstance(taskId: Int) = TaskFragment().apply {
            arguments = Bundle().apply {
                putInt(TASKID_ARG_PARAM, taskId)
            }
        }
    }
}