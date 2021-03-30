package edu.calpoly.flipted.businesslogic.goals

class GetAllGoals (private val repo : GoalsRepo) {
    suspend fun execute() : List<Goal> {
        return repo.getAllGoals();
    }
}