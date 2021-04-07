package edu.calpoly.flipted.ui.quizzes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.calpoly.flipted.R
import edu.calpoly.flipted.ui.home.StudentHomeFragment

private const val QUIZ_ID_PARAM_NAME = "quizId"

/**
 * A simple [Fragment] subclass.
 * Use the [QuizResultsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuizResultsFragment : Fragment() {
    private var quizId: Int = -1

    private lateinit var viewModel: QuizViewModel

    private lateinit var resultsText: TextView
    private lateinit var resultsProgressBar: ProgressBar
    private lateinit var continueButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            quizId = it.getInt(QUIZ_ID_PARAM_NAME)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View
        = inflater.inflate(R.layout.quiz_results_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(quizId == -1) {
            throw IllegalStateException("No quizId provided")
        }

        resultsText = view.findViewById(R.id.quiz_results_score_text)
        resultsProgressBar = view.findViewById(R.id.quiz_results_score_progressbar)
        continueButton = view.findViewById(R.id.quiz_results_continue_button)

        continueButton.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main_view, StudentHomeFragment.newInstance()).commit()
        }

        viewModel = ViewModelProvider(requireActivity())[QuizViewModel::class.java]

        viewModel.quizResult.observe(viewLifecycleOwner, Observer {
            resultsText.text = "${it.numCorrect}/${it.questions.size}"
            resultsProgressBar.max = it.questions.count()
            resultsProgressBar.progress = it.numCorrect
            Log.i("QuizResultsFragment", "Displaying results with id ${it.uid}")
        })
        viewModel.fetchResult(quizId)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param quizId The quiz for which results should be shown
         * @return A new instance of fragment QuizResultsFragment.
         */
        @JvmStatic
        fun newInstance(quizId: Int) =
                QuizResultsFragment().apply {
                    arguments = Bundle().apply {
                        putInt(QUIZ_ID_PARAM_NAME, quizId)
                    }
                }
    }
}