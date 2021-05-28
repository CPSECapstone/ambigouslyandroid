package edu.calpoly.flipted.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment


class TaskResultsHelpFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.results_help, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val send: Button = view.findViewById(R.id.results_help_btn)
        val attachFile: ImageView = view.findViewById(R.id.resultsAttachFile)

        send.setOnClickListener {
            Toast.makeText(requireActivity(), "Not Implemented", Toast.LENGTH_LONG).show()
        }

        attachFile.setOnClickListener {
            Toast.makeText(requireActivity(), "Not Implemented", Toast.LENGTH_LONG).show()
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment TaskResultsHelpFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            TaskResultsHelpFragment()
    }
}