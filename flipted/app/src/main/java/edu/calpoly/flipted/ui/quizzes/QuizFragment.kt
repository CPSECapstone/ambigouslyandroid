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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
            //TODO: validate quiz data. Make sure all questions have answers
            //TODO: Submit quiz data to backend
            parentFragmentManager.beginTransaction().apply {
                // TODO: pass quizId
                replace(R.id.main_view, QuizResultsFragment.newInstance(0))
                commit()
            }
        }

        listAdapter = QuestionListAdapter(requireActivity())
        listView.adapter = listAdapter

        viewModel.quizData.observe(viewLifecycleOwner, Observer {
            listAdapter.questionsData = it
            listAdapter.notifyDataSetChanged()
        })
        viewModel.fetchQuestions()
    }

    companion object {
        @JvmStatic
        fun newInstance() = QuizFragment()
    }
}