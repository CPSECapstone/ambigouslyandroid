package edu.calpoly.flipted.backend

import edu.calpoly.flipted.businesslogic.missions.Mission
import edu.calpoly.flipted.businesslogic.missions.MissionProgress
import edu.calpoly.flipted.businesslogic.missions.MissionsRepo
import edu.calpoly.flipted.businesslogic.missions.TaskStats
import edu.calpoly.flipted.businesslogic.tasks.data.TaskSubmissionResult
import kotlinx.coroutines.delay

class MockMissionsRepo : MissionsRepo {
    companion object {
        private var uid: Long = 10
            get() {
                field += 1
                return field
            }
        private val uids: String
            get() = uid.toString()
    }
    private val data = listOf(
        MissionProgress(
            Mission("m1", "Mendelian Genetics"),
            listOf(
                uids.let{TaskStats(it, "Pea pod phenotypes", TaskSubmissionResult(it, true, 9, 18, listOf()))},
                uids.let{TaskStats(it, "Dominant alleles", TaskSubmissionResult(it, true, 20, 20, listOf()))},
                uids.let{TaskStats(it, "Recessive alleles", TaskSubmissionResult(it, true, 2, 14, listOf()))},
                uids.let{TaskStats(it, "Gene expression", TaskSubmissionResult(it, true, 57, 100, listOf()))},
                TaskStats(uids, "Co-dominance", null)
            )
        ),
        MissionProgress(
            Mission("m2", "Genetic Disease"),
            listOf(
                uids.let{TaskStats(it, "The Circulatory System", TaskSubmissionResult(it, true, 8, 10, listOf()))},
                uids.let{TaskStats(it, "The Structure of Hemoglobin", TaskSubmissionResult(it, true, 20, 75, listOf()))},
                uids.let{TaskStats(it, "Genetic Testing", TaskSubmissionResult(it, true, 17, 18, listOf()))}
            )
        ),
        MissionProgress(
            Mission("m3",  "GMOs"),
            listOf(
                TaskStats(uids, "The challenges we face, and how GMOs can help", null),
                TaskStats(uids, "What are the different kinds of GMO?", null),
                TaskStats(uids, "How are GMOs made?", null),
                TaskStats(uids, "What are the risks of GMOs?", null)
            )
        )
    )

    override suspend fun getAllMissionProgress(courseId: String): List<MissionProgress> {
        delay(1000)
        return data
    }
}