package edu.calpoly.flipted.ui.quizzes

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.quizzes.Quiz
import edu.calpoly.flipted.businesslogic.quizzes.ValidateQuizInput
import edu.calpoly.flipted.businesslogic.quizzes.ValidationResponseType

private const val ARG_PARAM1 = "taskId"

/**
 * A simple [Fragment] subclass.
 * Use the [QuizFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuizFragment : Fragment() {

    private lateinit var viewModel : QuizViewModel
    private lateinit var listView: ListView
    private lateinit var listFooter : View
    private lateinit var listAdapter: QuestionListAdapter

    private lateinit var footerErrorWarnMsg : TextView

    private var taskId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        arguments?.let {
            taskId = it.getInt(ARG_PARAM1)
        }

        val view = inflater.inflate(R.layout.quiz_fragment, container, false)
        listView = view.findViewById(R.id.question_list)

        listFooter = inflater.inflate(R.layout.quiz_footer, listView, false)
        footerErrorWarnMsg = listFooter.findViewById(R.id.quiz_errors_exist_msg)
        listView.addFooterView(listFooter)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[QuizViewModel::class.java]

        listFooter.findViewById<Button>(R.id.quiz_submit_button).setOnClickListener {
            Log.i("QuizFragment", "Submitting quiz...")

            val filledInQuiz = Quiz(viewModel.quizData.value!!.uid, listAdapter.questionsData)

            val validationResults = ValidateQuizInput.execute(filledInQuiz.questions, listAdapter.validationIssues)
            if(validationResults.isNotEmpty()) {
                footerErrorWarnMsg.visibility = View.VISIBLE
                listAdapter.validationIssues = validationResults
                return@setOnClickListener
            }

            viewModel.quizResult.observe(viewLifecycleOwner, Observer {
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.main_view, QuizResultsFragment.newInstance(it.uid))
                    commit()
                }
            })

            viewModel.submitQuizForGrading(filledInQuiz)

        }

        listAdapter = QuestionListAdapter(requireActivity())
        listView.adapter = listAdapter

        viewModel.quizData.observe(viewLifecycleOwner, Observer {
            listAdapter.questionsData = it.questions
        })
        viewModel.fetchQuestions(taskId!!)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) = QuizFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_PARAM1, param1)
            }
        }
    }
}