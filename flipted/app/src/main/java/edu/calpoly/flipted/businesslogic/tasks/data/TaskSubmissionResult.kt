package edu.calpoly.flipted.businesslogic.tasks.data

import java.util.*

data class TaskSubmissionResult(
        val taskId: String,
        val graded: Boolean,
        val pointsAwarded: Int
        //val answers: List<GradedAnswer>
)