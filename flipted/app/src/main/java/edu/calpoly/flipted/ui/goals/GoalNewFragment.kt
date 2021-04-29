package edu.calpoly.flipted.ui.goals

import android.annotation.SuppressLint
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
import edu.calpoly.flipted.businesslogic.goals.NewGoalInput
import edu.calpoly.flipted.businesslogic.goals.SubGoal
import edu.calpoly.flipted.businesslogic.goals.SubGoalInput
import kotlinx.android.synthetic.main.goals_fragment_create.view.*
import java.text.SimpleDateFormat
import java.util.*


class GoalNewFragment : Fragment() {

    private lateinit var GoalName : EditText
    private lateinit var SubmitGoalButtons: Button
    private lateinit var TestText: TextView
    private lateinit var GoalDate: TextView
    private lateinit var GoalCategory: Spinner
    lateinit var newGoal: NewGoalInput

    private lateinit var recyclerView : RecyclerView

    fun getDateFromDatePicker(datePicker: DatePicker): Date {
        val day = datePicker.dayOfMonth
        val month = datePicker.month
        val year = datePicker.year
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return calendar.getTime()
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recyclerView = view.findViewById(R.id.goal_page_recyclerview)
        val adapter = GoalRecyclerViewAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())



        SubmitGoalButtons = view.findViewById(R.id.SubmitNewGoalButton)
        GoalName  = view.findViewById(R.id.goalNameInput)
        TestText  = view.findViewById(R.id.testText)
        GoalDate  = view.findViewById(R.id.goal_date_text)
        GoalCategory  = view.findViewById(R.id.spinner)

        SubmitGoalButtons.setOnClickListener {
                val dateFormatter = SimpleDateFormat("MM dd, yyyy")
                val title: String = GoalName.text.toString()
                val goalDateString: String = GoalDate.text.toString()
                val goalDate: Date = dateFormatter.parse(goalDateString)
                val category: String = GoalCategory.selectedItem.toString()
                val subGoal = SubGoalInput("tempTitle",goalDate)
                val subGoalList = adapter.subGoalBlocks.map {

                    SubGoalInput(it.title,it.dueDate!!)
                }

                //temporary crashes when more than one goal input
                val testText = "Name:\t$title\tDate:\t$goalDateString\tCategory:\t$category"
                lateinit var testTextSub: String
                subGoalList.forEach{
                    testTextSub = "\tSubGoal Name:\t" + it.title + "\tSubGoal Date:\t" + it.dueDate
                }
                TestText.text = testText + testTextSub
                //temporary

                newGoal = NewGoalInput(title,goalDate,subGoalList,category,false,false,0)
        }

    }

    private var button_date: Button? = null
    private var textview_date: TextView? = null
    var cal: Calendar = Calendar.getInstance()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.goals_fragment_create, container, false)
        textview_date = view.goal_date_text
        button_date = view.button_date_1
        textview_date!!.text = "--/--/----"
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
        val spinner = view.findViewById<Spinner>(R.id.spinner)
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
    return view
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