package edu.calpoly.flipted.ui.quizzes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.ListView
import android.widget.RadioButton
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.calpoly.flipted.R

/**
 * A simple [Fragment] subclass.
 * Use the [McFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class McFragment : Fragment() {

    private lateinit var viewModel : QuizViewModel
    private lateinit var listView: ListView
    private lateinit var listAdapter: QuestionListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mc, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[QuizViewModel::class.java]
        listView = view.findViewById(R.id.question_list)
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
        fun newInstance() =
            McFragment()
    }
}