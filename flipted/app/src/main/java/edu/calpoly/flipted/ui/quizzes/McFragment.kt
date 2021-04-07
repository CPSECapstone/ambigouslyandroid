package edu.calpoly.flipted.ui.quizzes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.calpoly.flipted.R

private const val ARG_PARAM1 = "taskId"

/**
 * A simple [Fragment] subclass.
 * Use the [McFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class McFragment : Fragment() {

    private lateinit var viewModel : QuizViewModel

    private lateinit var question : TextView
    private lateinit var answer1 : RadioButton
    private lateinit var answer2 : RadioButton
    private lateinit var answer3 : RadioButton
    private lateinit var answer4 : RadioButton

    private var taskId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        arguments?.let {
            taskId = it.getString(ARG_PARAM1)
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mc, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        question = view.findViewById(R.id.mc_question)
        answer1 = view.findViewById(R.id.answer1)
        answer2 = view.findViewById(R.id.answer2)
        answer3 = view.findViewById(R.id.answer3)
        answer4 = view.findViewById(R.id.answer4)

        viewModel = ViewModelProvider(requireActivity())[QuizViewModel::class.java]
        viewModel.quizData.observe(viewLifecycleOwner, Observer {
            if(it.size != 1)
                Log.w("McFragment", "Expected 1 question, received ${it.size}")
            val questionData = it[0]
            question.text = questionData.description

            if(questionData.options.size != 4)
                Log.w("McFragment", "Expected 4 answers, received ${questionData.options.size}")

            answer1.text = questionData.options[0].description
            answer2.text = questionData.options[1].description
            answer3.text = questionData.options[2].description
            answer4.text = questionData.options[3].description
        })
        viewModel.fetchQuestions(taskId)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            McFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}