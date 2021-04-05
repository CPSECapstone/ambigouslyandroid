package edu.calpoly.flipted.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import edu.calpoly.flipted.R
import edu.calpoly.flipted.ui.goals.GoalsFragment
import edu.calpoly.flipted.ui.quizzes.QuizFragment

/**
 * A simple [Fragment] subclass.
 * Use the [StudentHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StudentHomeFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val goalsButton = view.findViewById<Button>(R.id.goals_button)
            goalsButton.setOnClickListener{
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.main_view, GoalsFragment.newInstance())
                    addToBackStack(null)
                    commit()
                }
            }

        // mock task id
        val taskId = 1
        val quizButton = view.findViewById<Button>(R.id.quiz_button)
        quizButton.setOnClickListener{
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.main_view, QuizFragment.newInstance(1))
                addToBackStack(null)
                commit()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_home, container, false)
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            StudentHomeFragment()
    }
}