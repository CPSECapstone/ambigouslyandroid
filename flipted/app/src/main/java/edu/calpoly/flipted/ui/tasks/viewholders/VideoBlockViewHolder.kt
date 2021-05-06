package edu.calpoly.flipted.ui.tasks.viewholders

import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.tasks.GetId
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.TaskBlock
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.VideoBlock

class VideoBlockViewHolder(view : View, private val lifecycle : Lifecycle) : TaskBlockViewHolder(view) {
    private val videoPlayer : YouTubePlayerView = view.findViewById(R.id.youtubePlayerViewTask)
    private val title : TextView = view.findViewById(R.id.task_block_video_title)
    private val colorOfBlock: LinearLayout = view.findViewById(R.id.task_block_video_root)

    override fun bind(block: TaskBlock, position: Int) {
        val videoBlock = block as VideoBlock

        if (position % 2 != 0)
            colorOfBlock.setBackgroundColor(Color.parseColor("#F2F2F2"))
        else
            colorOfBlock.setBackgroundColor(Color.parseColor("#FFFFFF"))

        if(block.title != null) {
            title.text = block.title
        } else {
            title.visibility = View.GONE
        }

        lifecycle.addObserver(videoPlayer)

        videoPlayer.addYouTubePlayerListener(object: AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                val id = GetId.getYouTubeId(videoBlock.videoUrl)
                if(id == null || id == "Not YouTube Video" ) {
                    throw IllegalArgumentException("Not a youtube url")
                }
                youTubePlayer.cueVideo(id, 0f)
            }
        })
    }
}