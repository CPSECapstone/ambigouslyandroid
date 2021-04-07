package edu.calpoly.flipted.ui.quizzes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.quizzes.Quiz
import edu.calpoly.flipted.businesslogic.quizzes.ValidateQuizInput

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
        listView.addFooterView(listFooter)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[QuizViewModel::class.java]

        listFooter.findViewById<Button>(R.id.quiz_submit_button).setOnClickListener {
            Log.i("QuizFragment", "Submitting quiz...")

            val validationResults = ValidateQuizInput.execute(listAdapter.questionsData)
            if(validationResults.isNotEmpty()) {
                TODO()
            }
            viewModel.saveQuizCompletion(Quiz(viewModel.quizData.value!!.uid, listAdapter.questionsData))
            parentFragmentManager.beginTransaction().apply {
                // TODO: pass quizId
                replace(R.id.main_view, QuizResultsFragment.newInstance(0))
                commit()
            }
        }

        listAdapter = QuestionListAdapter(requireActivity())
        listView.adapter = listAdapter

        viewModel.quizData.observe(viewLifecycleOwner, Observer {
            listAdapter.questionsData = it.questions
            listAdapter.notifyDataSetChanged()
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