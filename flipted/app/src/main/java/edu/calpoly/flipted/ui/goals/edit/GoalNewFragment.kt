package edu.calpoly.flipted.ui.goals.edit

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.goals.UnsavedNewGoal
import edu.calpoly.flipted.businesslogic.goals.UnsavedNewSubGoal
import edu.calpoly.flipted.ui.goals.GoalRecyclerViewAdapter
import java.text.SimpleDateFormat
import java.util.*


class GoalNewFragment : Fragment() {

    private lateinit var goalTitleText : EditText
    private lateinit var submitGoalButton: Button
    private lateinit var testText: TextView
    private lateinit var goalDueDateText: TextView
    private lateinit var goalCategorySelector: Spinner

    private lateinit var newGoal: UnsavedNewGoal

    private lateinit var subGoalsView : RecyclerView

    private var button_date: Button? = null
    private lateinit var textview_date: TextView

    var cal: Calendar = Calendar.getInstance()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.goals_fragment_create, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textview_date = view.findViewById(R.id.goals_fragment_create_date_text)
        button_date = view.findViewById(R.id.goals_fragment_create_date_selector_button)
        textview_date.text = "--/--/----"

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }
        button_date!!.setOnClickListener {
            DatePickerDialog(
                requireActivity(),
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        val personNames = arrayOf("Emotional", "Social", "Completion", "Logical")
        val spinner = view.findViewById<Spinner>(R.id.goals_fragment_create_category_spinner)
        if (spinner != null) {
            val arrayAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, personNames)
            spinner.adapter = arrayAdapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    Toast.makeText(activity, getString(R.string.selected_item) + " " + personNames[position], Toast.LENGTH_SHORT).show()
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }

        subGoalsView = view.findViewById(R.id.goals_fragment_create_subgoals_recyclerview)
        val adapter = GoalRecyclerViewAdapter(this)
        subGoalsView.adapter = adapter
        subGoalsView.layoutManager = LinearLayoutManager(requireActivity())
        subGoalsView.isNestedScrollingEnabled = false

        submitGoalButton = view.findViewById(R.id.goals_fragment_create_submit_button)
        goalTitleText  = view.findViewById(R.id.goals_fragment_create_name_input)
        testText = view.findViewById(R.id.testText)
        goalDueDateText  = view.findViewById(R.id.goals_fragment_create_date_text)
        goalCategorySelector  = view.findViewById(R.id.goals_fragment_create_category_spinner)

        submitGoalButton.setOnClickListener {
            val dateFormatter = SimpleDateFormat("MM dd, yyyy")
            val title: String = goalTitleText.text.toString()
            val goalDateString: String = goalDueDateText.text.toString()
            val goalDate: Date = dateFormatter.parse(goalDateString)
            val category: String = goalCategorySelector.selectedItem.toString()
            val subGoal = UnsavedNewSubGoal("tempTitle",goalDate)
            val subGoalList = adapter.subGoalBlocks.map {
                UnsavedNewSubGoal(it.title,it.dueDate!!)
            }

            //temporary crashes when more than one goal input
            val testText = "Name:\t$title\tDate:\t$goalDateString\tCategory:\t$category"
            lateinit var testTextSub: String
            subGoalList.forEach{
                testTextSub = "\tSubGoal Name:\t" + it.title + "\tSubGoal Date:\t" + it.dueDate
            }
            this.testText.text = testText + testTextSub
            //temporary

            newGoal = UnsavedNewGoal(title,goalDate,subGoalList,category,false,false,0)
        }

    }

    private fun updateDateInView() {
        val myFormat = "MM dd, yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textview_date!!.text = sdf.format(cal.getTime())
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            GoalNewFragment()
    }




}