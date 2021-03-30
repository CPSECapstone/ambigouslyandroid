package com.example.flipted.businesslogic.goals

import java.util.*

class SaveNewCompletion(private val repo : GoalsRepo) {

    private fun validateCompletion(completion: GoalCompletion) : Boolean {
        if(completion.description.isBlank())
            return false
        if(completion.parentId < 0) // I'm pretty sure the UIDs will always be positive
            return false
        if(completion.completedDate.after(Date()))
            return false
        return true
    }

    suspend fun execute(completion : GoalCompletion) : Goal? =
        if (validateCompletion(completion))
            repo.saveNewCompletion(completion)
        else null

}