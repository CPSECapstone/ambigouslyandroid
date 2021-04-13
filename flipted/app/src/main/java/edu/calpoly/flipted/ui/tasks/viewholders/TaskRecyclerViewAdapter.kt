package edu.calpoly.flipted.ui.tasks.viewholders
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.*

class TaskRecyclerViewAdapter(
    private val inflater: LayoutInflater
) : RecyclerView.Adapter<TaskBlockViewHolder>() {

    var taskBlocks : List<TaskBlock> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskBlockViewHolder {
        val inflatedView = inflater.inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.task_block_image -> ImageBlockViewHolder(inflatedView)
            R.layout.task_block_quiz -> QuizBlockViewHolder(inflatedView)
            R.layout.task_block_text -> TextBlockViewHolder(inflatedView)
            R.layout.task_block_video -> VideoBlockViewHolder(inflatedView)
            else -> throw IllegalArgumentException("Unknown viewType")
        }
    }

    override fun onBindViewHolder(holder: TaskBlockViewHolder, position: Int) {
        holder.bind(taskBlocks[position])
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