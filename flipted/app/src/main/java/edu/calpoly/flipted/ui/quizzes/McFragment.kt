package edu.calpoly.flipted.ui.quizzes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        viewModel.quizData.observe(viewLifecycleOwner, Observer { TODO() })
        viewModel.fetchQuestions()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            McFragment()
    }
}