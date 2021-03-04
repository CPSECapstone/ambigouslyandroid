package com.example.flipteded.businesslogic.goals

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

    suspend fun execute(completion : GoalCompletion) : Boolean {
        if(!validateCompletion(completion))
            return false; // We might want more granular error reporting in the future
        repo.saveNewCompletion(completion)
        return true
    }

}