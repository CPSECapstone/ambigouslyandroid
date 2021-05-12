package edu.calpoly.flipted.businesslogic.learningTargets


//need to fix this
class GetAllTargetProgress (private val repo : LearningTargetRepo) {
    suspend fun execute(): List<TargetProgress> {
        return repo.getAllTargetProgress("test",null)
    }
}