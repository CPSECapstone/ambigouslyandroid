package edu.calpoly.flipted.businesslogic.tasks

import java.util.regex.Matcher
import java.util.regex.Pattern

class GetId {

    object Idinput {

        fun getYouTubeId(youTubeUrl: String): String? {
            val pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed/)[^#&?]*"
            val compiledPattern = Pattern.compile(pattern)
            val matcher = compiledPattern.matcher(youTubeUrl)
            return if (matcher.find()) {
                matcher.group()
            } else {
                "Not YouTube Video"
            }
        }

    }





}