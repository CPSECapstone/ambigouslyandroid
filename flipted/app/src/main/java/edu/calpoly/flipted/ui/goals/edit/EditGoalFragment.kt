package edu.calpoly.flipted.ui.goals.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.goals.SubGoal
import edu.calpoly.flipted.ui.goals.GoalRecyclerViewAdapter
import java.text.SimpleDateFormat


private const val GOALID_ARG_PARAM = "goalId"

class EditGoalFragment : Fragment() {
    private var editGoalId: String? = null

    private lateinit var viewModel: EditGoalViewModel

    private lateinit var goalTitleText : EditText
    private lateinit var submitGoalButton: Button
    private lateinit var goalDueDateSetButton: Button
    private lateinit var goalDueDateText: TextView
    private lateinit var goalCategorySelector: Spinner

    private lateinit var subGoalsView : RecyclerView
    private val dateFormat = SimpleDateFormat("MMMM dd, yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            editGoalId = it.getString(GOALID_ARG_PARAM)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.goals_fragment_create, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[EditGoalViewModel::class.java]

        goalTitleText = view.findViewById(R.id.goals_fragment_create_name_input)
        submitGoalButton = view.findViewById(R.id.goals_fragment_create_submit_button)
        goalDueDateSetButton = view.findViewById(R.id.goals_fragment_create_date_selector_button)
        goalDueDateText = view.findViewById(R.id.goals_fragment_create_date_text)
        goalCategorySelector = view.findViewById(R.id.goals_fragment_create_category_spinner)
        subGoalsView = view.findViewById(R.id.goals_fragment_create_subgoals_recyclerview)

        goalTitleText.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus)
                viewModel.setGoalTitleText(goalTitleText.text.toString())
        }

        goalDueDateSetButton.setOnClickListener {
            GoalDueDatePickerFragment().show(childFragmentManager, GoalDueDatePickerFragment.TAG)
        }

        val categoryOptions = arrayOf("Emotional", "Social", "Completion", "Logical")
        val spinnerAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, categoryOptions)
        goalCategorySelector.adapter = spinnerAdapter
        goalCategorySelector.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.setGoalCategory(categoryOptions[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.setGoalCategory("")
            }
        }

        submitGoalButton.setOnClickListener {
            val errors = viewModel.validateGoal()
            if(errors.isEmpty()) {
                viewModel.saveGoal()
                parentFragmentManager.popBackStack("EditGoalFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            } else {
                Toast.makeText(requireActivity(), errors[0], Toast.LENGTH_LONG).show()
            }
        }

        subGoalsView = view.findViewById(R.id.goals_fragment_create_subgoals_recyclerview)
        val subgoalAdapter = GoalRecyclerViewAdapter(this)
        subGoalsView.adapter = subgoalAdapter
        subGoalsView.layoutManager = LinearLayoutManager(requireActivity())
        subGoalsView.isNestedScrollingEnabled = false

        viewModel.goal.observe(viewLifecycleOwner, Observer {
            goalTitleText.setText(it.title)
            goalDueDateText.text = dateFormat.format(it.dueDate)
            categoryOptions.indexOf(it.category).let { pos ->
                if(pos >= 0)
                    goalCategorySelector.setSelection(pos)
            }

            if(savedInstanceState == null) {
                subgoalAdapter.subGoalBlocks = it.subGoals.map { subgoal ->
                    when (subgoal) {
                        is SubGoal -> MutableSubGoal(subgoal)
                        else -> MutableSubGoal(subgoal.title, null, subgoal.dueDate, false, null)
                    }
                }
                subgoalAdapter.notifyDataSetChanged()
            }
        })

        if(savedInstanceState == null) {
            editGoalId.let {
                if(it != null) {
                    viewModel.fetchGoal(it)
                } else {
                    viewModel.fillInEmptyGoal()
                }
            }
        }

    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstanceCreateGoal() =
            EditGoalFragment()

        fun newInstanceEditGoal(goalId: String)
            = EditGoalFragment().apply {
                arguments = Bundle().apply {
                    putString(GOALID_ARG_PARAM, goalId)
                }
        }
    }




}