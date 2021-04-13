package edu.calpoly.flipted.ui.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.tasks.data.Page
import java.lang.IllegalArgumentException

private const val PAGENO_ARG_PARAM = "pageNo"

/**
 * A simple [Fragment] subclass.
 * Use the [TaskPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskPageFragment : Fragment() {
    private var pageNo: Int? = null

    private lateinit var viewModel : TaskViewModel
    private lateinit var data : Page

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pageNo = it.getInt(PAGENO_ARG_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.task_page_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]
        val task = viewModel.currTask.value
        val page = pageNo

        if(task == null)
            throw IllegalStateException("No currTask")

        if(page == null)
            throw IllegalStateException("No pageNo provided")

        data = task.pages[page]

        view.findViewById<TextView>(R.id.task_page_test).text = "Page $page"
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * Note that the TaskViewModel must have the correct task set as currTask.
         *
         * @param pageNo The task page to show
         * @return A new instance of fragment TaskPageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(pageNo: Int) =
            TaskPageFragment().apply {
                arguments = Bundle().apply {
                    putInt(PAGENO_ARG_PARAM, pageNo)
                }
            }
    }
}