package edu.calpoly.flipted.ui.tasks

import androidx.fragment.app.Fragment
import edu.calpoly.flipted.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

private const val ARG_PARAM1 = "question"

class FreeResponseFragment: Fragment() {

    private var question: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        arguments?.let {
            question = it.getString(ARG_PARAM1)
        }

        val view = inflater.inflate(R.layout.task_free_response, container, false)
        val questionText = view.findViewById(R.id.fr_input) as TextView

        questionText.text = question

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) = FreeResponseFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
            }
        }
    }
}