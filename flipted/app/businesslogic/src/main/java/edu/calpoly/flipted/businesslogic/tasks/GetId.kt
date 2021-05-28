package edu.calpoly.flipted.businesslogic.tasks

import java.util.regex.Pattern


object GetId {
    fun getYouTubeId(youTubeUrl: String): String? {
        val matchResult = "(?:v=|\\/|^)([A-Za-z0-9_-]{11,})".toRegex().find(youTubeUrl)
        return if (matchResult != null) {
            matchResult.groupValues[1]
        } else {
            "Not YouTube Video"
        }
    }

}
