package edu.calpoly.flipted.businesslogic.targets

interface LearningTargetsRepo {

    suspend fun getAllTargetProgress(courseId: String,studentId: String?): List<TargetProgress>

}