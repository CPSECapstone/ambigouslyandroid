package edu.calpoly.flipted.ui.tasks.viewholders

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.ImageBlock
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.TaskBlock

class ImageBlockViewHolder(view : View, val inflater: LayoutInflater) : TaskBlockViewHolder(view) {
    private val title : TextView = view.findViewById(R.id.task_image_block_title)
    private val image : ImageView = view.findViewById(R.id.task_block_image)
    private val colorOfBlock: LinearLayout = view.findViewById(R.id.task_block_image_root)
    //private val rootLayout: LinearLayout = view.findViewById(R.id.task_block_image_root)

    override fun bind(block: TaskBlock, position: Int) {
        val imageBlock = block as ImageBlock

        //val imageLayoutBackground = inflater.inflate(R.layout.task_block_image, rootLayout, true)
        if (position % 2 != 0)
            colorOfBlock.setBackgroundColor(Color.parseColor("#F2F2F2"))
        else
            colorOfBlock.setBackgroundColor(Color.parseColor("#FFFFFF"))


                if(block.title != null) {
            title.text = block.title
        } else {
            title.visibility = View.GONE
        }

        Picasso.get().load(block.imageUrl).into(image)
    }


}