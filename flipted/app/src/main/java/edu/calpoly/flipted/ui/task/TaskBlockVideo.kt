package edu.calpoly.flipted.ui.task

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import edu.calpoly.flipted.R


class TaskBlockVideo : AppCompatActivity() {
    private lateinit var youTubePlayerView: YouTubePlayerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_block_video)
        youTubePlayerView = findViewById(R.id.youtubePlayerViewTask)
        lifecycle.addObserver(youTubePlayerView)
    }


}