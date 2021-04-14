package edu.calpoly.flipted.ui.tasks.viewholders

import android.view.View
import android.widget.TextView
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.TaskBlock
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.TextBlock

class TextBlockViewHolder(view : View) : TaskBlockViewHolder(view) {
    private val title : TextView = view.findViewById(R.id.text_block_text_title)
    private val text : TextView = view.findViewById(R.id.text_block_text)


    override fun bind(block: TaskBlock) {
        val textBlock = block as TextBlock

        if(block.title != null) {
            title.text = block.title
        } else {
            title.visibility = View.GONE
        }
    }
}