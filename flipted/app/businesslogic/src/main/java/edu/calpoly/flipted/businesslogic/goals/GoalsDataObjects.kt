package edu.calpoly.flipted.businesslogic.goals

import java.util.Date

data class SubGoal(
        val title: String,
        val dueDate: Date,
        val completed: Boolean,
        val completedDate: Date?,
        val stableId : Long = nextId
) {
    companion object {
        private var nextId : Long = 10
            get() {
                field += 1
                return field
            }
    }
}

data class Goal(
        val uid: String?,
        val title: String,
        val dueDate: Date,
        val completed: Boolean,
        val completedDate: Date?,
        val subGoals: List<SubGoal>,
        val category: String,
        val favorited: Boolean,
        val isOwnedByStudent: Boolean,
        val pointValue: Int?
)


