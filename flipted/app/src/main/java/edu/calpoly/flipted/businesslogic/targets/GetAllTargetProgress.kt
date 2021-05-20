package edu.calpoly.flipted.businesslogic.targets

class GetAllTargetProgress (private val repo : LearningTargetsRepo) {
    suspend fun execute(): List<TargetProgress> {
        return repo.getAllTargetProgress("Integrated Science",null)
    }
}