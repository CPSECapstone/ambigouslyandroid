package edu.calpoly.flipted.businesslogic.missions

interface MissionsRepo {
    suspend fun getAllMissionProgress(courseId: String) : List<MissionProgress>
}