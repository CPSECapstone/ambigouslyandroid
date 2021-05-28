package edu.calpoly.flipted.ui.tasks.viewholders

import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.TaskBlock
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.TextBlock
import edu.calpoly.flipted.ui.tasks.R

class TextBlockViewHolder(view : View) : TaskBlockViewHolder(view) {
    private val title : TextView = view.findViewById(R.id.task_block_text_title)
    private val text : TextView = view.findViewById(R.id.task_block_text)
    private val colorOfBlock: LinearLayout = view.findViewById(R.id.task_block_text_root)

    override fun bind(block: TaskBlock, position: Int) {
        val textBlock = block as TextBlock

        if (position % 2 != 0)
            colorOfBlock.setBackgroundColor(Color.parseColor("#F2F2F2"))
        else
            colorOfBlock.setBackgroundColor(Color.parseColor("#FFFFFF"))

        if(block.title != null) {
            title.text = block.title
        } else {
            title.visibility = View.GONE
        }

        text.text = textBlock.contents
        text.textSize = textBlock.fontSize.toFloat()
    }
}