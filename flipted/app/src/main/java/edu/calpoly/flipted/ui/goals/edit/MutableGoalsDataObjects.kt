package edu.calpoly.flipted.ui.goals.edit

import edu.calpoly.flipted.businesslogic.goals.Goal
import edu.calpoly.flipted.businesslogic.goals.SubGoal
import java.util.*

class MutableSubGoal(
    var title: String,
    val id: String?,
    var dueDate: Date?,
    val completed: Boolean,
    val completedDate: Date?
){
    constructor(subGoal: SubGoal) : this(
        subGoal.title,
        subGoal.id,
        subGoal.dueDate,
        subGoal.completed,
        subGoal.completedDate
    )

    val lock: SubGoal?
        get() {
            val id = id
            val dueDate = dueDate
            if(id == null || dueDate == null)
                return null
            return SubGoal(title, id, dueDate, completed, completedDate)
        }

    private val myid = nextId
    companion object {
        private var nextId = 0
            get(){
                field += 1
                return field
            }
    }

    override fun equals(other: Any?): Boolean {
        return when(other){
            is MutableSubGoal -> other.title == title
                    && other.id == id
                    && other.dueDate == dueDate
                    && other.completed == completed
                    && other.completedDate == completedDate
                    && other.myid == myid
            else -> false

        }
    }
}