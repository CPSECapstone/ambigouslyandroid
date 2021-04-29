package edu.calpoly.flipted.ui.goals
import android.app.DatePickerDialog
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import edu.calpoly.flipted.R
import kotlinx.android.synthetic.main.goals_fragment_create_subgoal_top.view.*
import java.text.SimpleDateFormat
import java.util.*


class TopSubGoalViewHolder(view: View, private val adapter: GoalRecyclerViewAdapter) : GoalBlockViewHolder(view)  {
    private val subGoalName : EditText= view.findViewById(R.id.subGoalNameInput)
    private val deleteSubGoalButton : Button= view.findViewById(R.id.deleteGoalButton)
    private val subGoalDateText : TextView= view.findViewById(R.id.subGoalDateText)
    private var subGoalButtonDate: Button? = null
    private var subGoalButtonDateText: TextView? = null
    var cal: Calendar = Calendar.getInstance()
    var subGoal: MutableSubGoal? = null

    fun bind(block: MutableSubGoal) {
        subGoalName.hint = block.title
        subGoalDateText.text = block.dueDate?.toString() ?:""
        subGoal = block


        deleteSubGoalButton.setOnClickListener{
            adapter.subGoalBlocks = adapter.subGoalBlocks.filter {
                it != block
            }
        }


    }

    init {

        subGoalButtonDateText = view.subGoalDateText
        subGoalButtonDate = view.subGoalDateButton
        subGoalButtonDateText!!.text = "--/--/----"

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            subGoal!!.dueDate = cal.time
            adapter.notifyDataSetChanged()
        }
        subGoalButtonDate!!.setOnClickListener {
            DatePickerDialog(
                    view.context,
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

    }

    private fun updateDateInView() {
        val myFormat = "MM dd, yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        subGoalButtonDateText!!.text = sdf.format(cal.time)
    }


}