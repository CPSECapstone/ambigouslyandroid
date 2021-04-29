package edu.calpoly.flipted.businesslogic.goals


class EditGoal(private val repo: GoalsRepo) {
    suspend fun execute(oldGoal: Goal) : Goal {
        return repo.editGoal(oldGoal)
    }
}
