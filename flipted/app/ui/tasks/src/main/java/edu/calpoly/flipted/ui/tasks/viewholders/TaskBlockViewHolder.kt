package edu.calpoly.flipted.ui.tasks.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.TaskBlock

abstract class TaskBlockViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(block : TaskBlock, position: Int)
}