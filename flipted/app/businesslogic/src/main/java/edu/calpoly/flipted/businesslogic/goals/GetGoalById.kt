package edu.calpoly.flipted.businesslogic.goals

class GetGoalById(private val repo: GoalsRepo) {
    suspend fun execute(goalId: String) : Goal {
        return repo.getGoalById(goalId)
    }
}