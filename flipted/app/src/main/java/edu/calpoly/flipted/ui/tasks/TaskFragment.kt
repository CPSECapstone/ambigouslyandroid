package edu.calpoly.flipted.ui.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.quizzes.Quiz
import edu.calpoly.flipted.businesslogic.quizzes.ValidateQuizInput
import edu.calpoly.flipted.businesslogic.quizzes.ValidationResponseType
import edu.calpoly.flipted.ui.goals.GoalsViewModel
import edu.calpoly.flipted.ui.quizzes.QuizViewModel

private const val ARG_PARAM1 = "taskId"

/**
 * A simple [Fragment] subclass.
 * Use the [QuizFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskFragment : Fragment() {

    private lateinit var viewModel : TaskViewModel
    private lateinit var viewPager : ViewPager2
    private var taskId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        arguments?.let {
            taskId = it.getInt(ARG_PARAM1)
        }

        val view = inflater.inflate(R.layout.task_tabs, container, false)
        viewPager = view.findViewById(R.id.task_pager)

        val adapterViewPager = TaskPagerAdapter(getParentFragmentManager())

        viewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]
/*
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val currBlock = inflater.inflate(R.layout.goals_mark_progress_form, null)
            }
        });
*/
        viewModel.blocks.observe(viewLifecycleOwner, Observer { newBlocks ->
            adapterViewPager.setBlocks(newBlocks)
        })

        viewModel.fetchBlocks()

        return view
    }
/*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
*/
    companion object {
        @JvmStatic
        fun newInstance(param1: Int) = TaskFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_PARAM1, param1)
            }
        }
    }
}