package edu.calpoly.flipted.businesslogic.tasks.data

import java.util.*

data class Task(
        val pages: List<Page>,
        val requirements: List<RubricRequirement>,
        val uid: String,
        val name: String,
        val instructions: String,
        val points: Int,
        val startAt: Date?,
        val endAt: Date?,
        val dueDate: Date?,
        val parentMissionId: String,
        val parentMissionIndex: Int,
        val objectiveId: String?
)