package edu.calpoly.flipted.businesslogic.targets


//need to fix this
class GetAllTargetProgress (private val repo : LearningTargetsRepo) {
    suspend fun execute(): List<TargetProgress> {
        return repo.getAllTargetProgress("test",null)
    }
}