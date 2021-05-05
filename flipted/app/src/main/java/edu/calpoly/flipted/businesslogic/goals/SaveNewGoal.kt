package edu.calpoly.flipted.businesslogic.goals

class SaveNewGoal(private val repo: GoalsRepo) {
    suspend fun execute(goal: UnsavedNewGoal) : Goal {
        return repo.saveNewGoal(goal)
    }
}