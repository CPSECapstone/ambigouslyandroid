package edu.calpoly.flipted.businesslogic.goals

interface GoalsRepo {
    suspend fun getAllGoals() : List<Goal>
    suspend fun getGoalById(id : String) : Goal?
    suspend fun saveNewGoal(goal : Goal) : Goal
    suspend fun editGoal(goal : Goal) : Goal
}