package edu.calpoly.flipted.businesslogic.goals

interface GoalsRepo {
    suspend fun getAllGoals() : List<Goal>
    suspend fun getGoalById(id : Int) : Goal?
    //suspend fun saveNewCompletion(completion : GoalCompletion) : Goal?
    suspend fun editGoal()
}