package edu.calpoly.flipted.backend

import edu.calpoly.flipted.businesslogic.missions.*
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
        listOf(
            uids.let{TaskStats(SparseTask(it, "Pea pod phenotypes", "Do da task", 10, null), TaskSubmissionResult(it, true, 9, 18, listOf())) },
            uids.let{TaskStats(SparseTask(it, "Dominant alleles", "Something", 300, null), TaskSubmissionResult(it, true, 20, 20, listOf()))},
            uids.let{TaskStats(SparseTask(it, "Recessive alleles", "Bleh", 1, null), TaskSubmissionResult(it, true, 2, 14, listOf()))},
            uids.let{TaskStats(SparseTask(it, "Gene expression", "none", 20, null), TaskSubmissionResult(it, true, 57, 100, listOf()))},
            TaskStats(SparseTask(uids, "Co-dominance", "", 4, null), null)
        ).let{ taskStats ->
            MissionProgress(
                Mission("m1", "Mendelian Genetics", "", taskStats.map{it.task}),
                taskStats
            )
        },
        listOf(
            uids.let{TaskStats(SparseTask(it, "The Circulatory System", "abc", 2, null), TaskSubmissionResult(it, true, 8, 10, listOf()))},
            uids.let{TaskStats(SparseTask(it, "The Structure of Hemoglobin", "def", 3, null), TaskSubmissionResult(it, true, 20, 75, listOf()))},
            uids.let{TaskStats(SparseTask(it, "Genetic Testing", "ghi", 4, null), TaskSubmissionResult(it, true, 17, 18, listOf()))}
        ).let{ taskStats ->
            MissionProgress(
                Mission("m2", "Genetic Disease", "", taskStats.map{it.task}),
                    taskStats
                )
        },
        listOf(
            TaskStats(SparseTask(uids, "The challenges we face, and how GMOs can help", "jkl", 5, null), null),
            TaskStats(SparseTask(uids, "What are the different kinds of GMO?", "mno", 6, null), null),
            TaskStats(SparseTask(uids, "How are GMOs made?", "pqr", 7, null), null),
            TaskStats(SparseTask(uids, "What are the risks of GMOs?", "stu", 8, null), null)
        ).let { taskStats ->
            MissionProgress(
                Mission("m3",  "GMOs", "", taskStats.map{it.task}),
                taskStats
            )
        }

    )

    override suspend fun getAllMissionProgress(courseId: String): List<MissionProgress> {
        delay(1000)
        return data
    }
}