package edu.calpoly.flipted.ui.goals.edit

import edu.calpoly.flipted.businesslogic.goals.Goal
import edu.calpoly.flipted.businesslogic.goals.SubGoal
import java.util.*

class MutableSubGoal(
    var title: String,
    var dueDate: Date,
    val completed: Boolean,
    val completedDate: Date?,
    val id: Long
){
    constructor(subGoal: SubGoal) : this(
        subGoal.title,
        subGoal.dueDate,
        subGoal.completed,
        subGoal.completedDate,
        subGoal.stableId
    )

    val asSubGoal: SubGoal
        get() {
            return SubGoal(title, dueDate, completed, completedDate, id)
        }
}

class MutableGoal(
        var title: String,
        var uid: String?,
        var dueDate: Date?,
        var completedDate: Date?,
        var subGoals: MutableList<MutableSubGoal>,
        var completed: Boolean,
        var category: String,
        var favorited: Boolean,
        var ownedByStudent: Boolean,
        var pointValue: Int?
) {
    constructor(goal: Goal) : this(
            goal.title,
            goal.uid,
            goal.dueDate,
            goal.completedDate,
            goal.subGoals.map {
                MutableSubGoal(it)
            }.toMutableList(),
            goal.completed,
            goal.category,
            goal.favorited,
            goal.isOwnedByStudent,
            goal.pointValue
    )
}