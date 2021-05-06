package edu.calpoly.flipted.ui.goals.edit
import android.app.DatePickerDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import edu.calpoly.flipted.R
import edu.calpoly.flipted.ui.goals.GoalRecyclerViewAdapter
import java.text.SimpleDateFormat
import java.util.*


class SubGoalViewHolder(view: View, private val adapter: GoalRecyclerViewAdapter) : GoalBlockViewHolder(view)  {
    private val subGoalName : EditText = view.findViewById(R.id.goals_fragment_create_subgoal_list_item_title)
    private val deleteSubGoalButton : Button = view.findViewById(R.id.goals_fragment_create_subgoal_list_item_delete_button)
    private val subGoalDateText : TextView = view.findViewById(R.id.goals_fragment_create_subgoal_list_item_date_text)
    private var subGoalButtonDate: Button = view.findViewById(R.id.goals_fragment_create_subgoal_list_item_date_button)
    private var subGoalButtonDateText: TextView = view.findViewById(R.id.goals_fragment_create_subgoal_list_item_date_text)
    var cal: Calendar = Calendar.getInstance()
    lateinit var subGoal: MutableSubGoal

    private val dateFormat = SimpleDateFormat("MM/dd/yyyy")

    init {
        subGoalButtonDateText.text = "--/--/----"
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            subGoal.dueDate = cal.time
            adapter.notifyItemChanged(adapterPosition)
        }
        subGoalButtonDate.setOnClickListener {
            DatePickerDialog(
                    view.context,
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        deleteSubGoalButton.setOnClickListener{
            adapter.subGoalBlocks = adapter.subGoalBlocks.filter {
                it != subGoal
            }
            adapter.notifyItemRemoved(adapterPosition)
        }
        subGoalName.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                subGoal.title = p0.toString()
            }


        })
    }



    fun bind(subGoal: MutableSubGoal) {
        this.subGoal = subGoal
        subGoalName.setText(subGoal.title)
        subGoalDateText.text = subGoal.dueDate?.let{ dateFormat.format(it) } ?:""
    }

}