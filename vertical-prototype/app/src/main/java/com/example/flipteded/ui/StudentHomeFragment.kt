package com.example.flipteded.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.flipteded.R
import kotlinx.android.synthetic.main.fragment_student_home.*

/**
 * A simple [Fragment] subclass.
 * Use the [StudentHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StudentHomeFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button = view.findViewById<Button>(R.id.goals_button)
            button.setOnClickListener{
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.main_view, GoalsFragment.newInstance())
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