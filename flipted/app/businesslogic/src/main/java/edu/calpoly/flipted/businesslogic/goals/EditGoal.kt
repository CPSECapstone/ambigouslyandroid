package edu.calpoly.flipted.businesslogic.goals


class EditGoal(private val repo: GoalsRepo) {
    suspend fun execute(goal: Goal) : Goal {
        return repo.editGoal(goal)
    }
}
