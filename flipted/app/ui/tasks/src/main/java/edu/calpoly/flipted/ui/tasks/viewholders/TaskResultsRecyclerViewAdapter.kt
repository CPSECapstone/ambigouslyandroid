package edu.calpoly.flipted.ui.tasks.viewholders
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.QuizBlock
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.TaskBlock
import edu.calpoly.flipted.ui.tasks.R
import edu.calpoly.flipted.viewmodels.TaskViewModel

class TaskResultsRecyclerViewAdapter(
    private val fragment: Fragment
) : RecyclerView.Adapter<TaskBlockViewHolder>() {

    private val inflater = fragment.layoutInflater
    private val viewModel = ViewModelProvider(fragment.requireActivity())[TaskViewModel::class.java]

    var taskBlocks : List<TaskBlock> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskBlockViewHolder {
        val inflatedView = inflater.inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.task_block_quiz -> ResultBlockViewHolder(inflatedView, inflater, viewModel, fragment.requireContext())
            else -> throw IllegalArgumentException("Unknown viewType")
        }
    }

    override fun onBindViewHolder(holder: TaskBlockViewHolder, position: Int) {
        holder.bind(taskBlocks[position], position)
    }

    override fun getItemCount(): Int = taskBlocks.size

    override fun getItemViewType(position: Int): Int = when(taskBlocks[position]) {
        is QuizBlock -> R.layout.task_block_quiz
        else -> throw IllegalArgumentException("Unknown TaskBlock type")
    }

}