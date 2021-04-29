package edu.calpoly.flipted.businesslogic.goals
/*
import java.util.*

class SaveNewCompletion(private val repo : GoalsRepo) {

    private fun validateCompletion(completion: Goal) : Boolean {
        if(completion.title.isBlank())
            return false
        if(completion.uid < 0) // I'm pretty sure the UIDs will always be positive
            return false
        if(completion.completedDate!!.after(Date()))
            return false
        return true
    }

    suspend fun execute(completion : Goal) : Goal? =
        if (validateCompletion(completion))
            repo.saveNewCompletion(completion)
        else null

}
*/
