package edu.calpoly.flipted.ui.tasks

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import edu.calpoly.flipted.R
import edu.calpoly.flipted.ui.tasks.rubric.TaskRubricFragment
import java.util.*

private const val TASKID_ARG_PARAM = "taskId"

/**
 * A simple [Fragment] subclass.
 * Use the [QuizFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskFragment : Fragment() {
    private var taskId: String? = null

    private lateinit var progressBar : ProgressBar
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager : ViewPager2
    private lateinit var rubricView : FragmentContainerView
    private lateinit var viewModel : TaskViewModel
    private lateinit var rubricButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            taskId = it.getString(TASKID_ARG_PARAM)
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
        rubricView = view.findViewById(R.id.task_pager_rubric_container)

        rubricButton = view.findViewById(R.id.task_button_rubric)

        val pagerAdapter = TaskPagerAdapter(this)
        var clickNum: Int = 0
        rubricButton.setOnClickListener {
            if (clickNum == 0) {
                rubricView.visibility = View.VISIBLE
                clickNum++
            } else if (clickNum == 1) {
                rubricView.visibility = View.GONE
                clickNum = 0
            }
        }

        viewModel.clearTask()


        viewModel.currTask.observe(viewLifecycleOwner, Observer {
            if(it == null) {
                progressBar.visibility = View.VISIBLE
                tabLayout.visibility = View.GONE
                viewPager.visibility = View.GONE
                rubricView.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                tabLayout.visibility = View.VISIBLE
                viewPager.visibility = View.VISIBLE
                rubricView.visibility = View.VISIBLE

                pagerAdapter.pages = it.pages

                childFragmentManager.commit {
                    replace(R.id.task_pager_rubric_container, TaskRubricFragment.newInstance())
                    setReorderingAllowed(true)
                }
            }
        })
        viewModel.fetchTask(id)

        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "Page ${position + 1}"
        }.attach()

    }

    companion object {
        @JvmStatic
        fun newInstance(taskId: String) = TaskFragment().apply {
            arguments = Bundle().apply {
                putString(TASKID_ARG_PARAM, taskId)
            }
        }
    }
}