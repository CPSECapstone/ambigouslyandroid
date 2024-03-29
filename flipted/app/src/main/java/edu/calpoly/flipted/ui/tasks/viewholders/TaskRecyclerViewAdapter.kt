package edu.calpoly.flipted.ui.tasks.viewholders
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.*
import edu.calpoly.flipted.ui.tasks.TaskViewModel

class TaskRecyclerViewAdapter(
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
            R.layout.task_block_image -> ImageBlockViewHolder(inflatedView,inflater)
            R.layout.task_block_quiz -> QuizBlockViewHolder(inflatedView, inflater, viewModel)
            R.layout.task_block_text -> TextBlockViewHolder(inflatedView)
            R.layout.task_block_video -> VideoBlockViewHolder(inflatedView, fragment.viewLifecycleOwner.lifecycle)
            else -> throw IllegalArgumentException("Unknown viewType")
        }
    }

    override fun onBindViewHolder(holder: TaskBlockViewHolder, position: Int) {
        holder.bind(taskBlocks[position], position)
        //change bind to accept postion and change color based on postion
    }

    override fun getItemCount(): Int = taskBlocks.size

    override fun getItemViewType(position: Int): Int = when(taskBlocks[position]) {
        is ImageBlock -> R.layout.task_block_image
        is QuizBlock -> R.layout.task_block_quiz
        is TextBlock -> R.layout.task_block_text
        is VideoBlock -> R.layout.task_block_video
        else -> throw IllegalArgumentException("Unknown TaskBlock type")
    }
}