package com.example.flipted.ui.goals


import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ExpandableListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.flipted.R
import com.example.flipted.businesslogic.goals.GoalCompletion
import com.example.flipted.ui.MainViewModel

import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [AddStringFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GoalListFragment : Fragment() {

    private lateinit var viewModel : MainViewModel
    private lateinit var expandableListView: ExpandableListView
    private lateinit var adapter: CustomExpandableListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.goals_fragment_exp_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))[MainViewModel::class.java]

        expandableListView = view.findViewById(R.id.expandableList)
        //Ref for switching group indicator: https://stackoverflow.com/questions/5800426/expandable-list-view-move-group-icon-indicator-to-right
        val newDisplay = expandableListView.viewTreeObserver
        newDisplay.addOnGlobalLayoutListener {
            expandableListView.setIndicatorBoundsRelative(
                expandableListView.right - 40,
                expandableListView.width
            )
        }

        adapter = CustomExpandableListAdapter(requireActivity())
        expandableListView.setAdapter(adapter)

        expandableListView.setOnChildClickListener { _, v, groupPosition, childPosition, _ ->
            if (v.id == R.id.fragment_mark_progress) {
                val goal = viewModel.goals.value!![groupPosition]

                val mAlertDialog = AlertDialog.Builder(requireActivity())
                val inflater = this.layoutInflater
                val progressFormView = inflater.inflate(R.layout.goals_mark_progress_form, null)

                //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                mAlertDialog.setTitle("Mark Progress") //set alertdialog title
                //mAlertDialog.setMessage("Your message here") //set alertdialog message
                mAlertDialog.setPositiveButton("Save") { _, _ ->
                    val newTitle = progressFormView.findViewById<EditText>(R.id.Progress_Title).text
                    val newCompletion = GoalCompletion(newTitle.toString(), goal.uid, Date())
                    viewModel.save(newCompletion)
                }
                mAlertDialog.setNegativeButton("Cancel") { _, _ ->
                    Log.i("GoalListFragment", "Not saving completion...");
                }
                mAlertDialog.setView(progressFormView)
                mAlertDialog.show()
                true
            } else false


        }
        viewModel.goals.observe(viewLifecycleOwner, Observer { newGoals ->
            adapter.setGoals(newGoals)
        })

        viewModel.fetchGoals()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddStringFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = GoalListFragment()
    }
}