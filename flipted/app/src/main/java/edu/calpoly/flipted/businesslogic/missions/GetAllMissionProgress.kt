package edu.calpoly.flipted.businesslogic.missions

class GetAllMissionProgress(private val repo: MissionsRepo) {
    suspend fun execute(courseId: String) : List<MissionProgress> {
        return repo.getAllMissionProgress(courseId)
    }
}