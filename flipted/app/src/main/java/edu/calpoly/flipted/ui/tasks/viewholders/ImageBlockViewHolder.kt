package edu.calpoly.flipted.ui.tasks.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.ImageBlock
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.TaskBlock

class ImageBlockViewHolder(view : View) : TaskBlockViewHolder(view) {
    private val title : TextView = view.findViewById(R.id.task_image_block_title)
    private val image : ImageView = view.findViewById(R.id.task_block_image)


    override fun bind(block: TaskBlock) {
        val imageBlock = block as ImageBlock

        if(block.title != null) {
            title.text = block.title
        } else {
            title.visibility = View.GONE
        }

        Picasso.get().load(block.imageUrl).into(image)
    }


}