package edu.calpoly.flipted.ui.quizzes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.calpoly.flipted.R

private const val QUIZ_ID_PARAM_NAME = "quizId"

/**
 * A simple [Fragment] subclass.
 * Use the [QuizResultsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuizResultsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var quizId: Int = -1

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


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param quizId The quiz for which results should be shown
         * @return A new instance of fragment QuizResultsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(quizId: Int) =
                QuizResultsFragment().apply {
                    arguments = Bundle().apply {
                        putInt(QUIZ_ID_PARAM_NAME, quizId)
                    }
                }
    }
}