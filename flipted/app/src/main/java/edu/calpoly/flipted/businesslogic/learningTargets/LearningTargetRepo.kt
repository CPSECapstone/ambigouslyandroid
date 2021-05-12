package edu.calpoly.flipted.businesslogic.learningTargets

interface LearningTargetRepo {

    suspend fun getAllTargetProgress(courseId: String,studentId: String?): List<TargetProgress>

}