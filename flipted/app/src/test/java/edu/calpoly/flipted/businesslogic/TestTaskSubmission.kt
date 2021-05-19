package edu.calpoly.flipted.businesslogic


import edu.calpoly.flipted.businesslogic.tasks.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.Assert.*


class TestTaskSubmissionTargets {
    class MockLearningTargetsRepo : LearningTargetsRepo  {
        val data = mutableListOf<TargetProgress>()
        override suspend fun getAllTargetProgress(courseId: String, studentId: String?) = data
    }

    @Test
    fun getAllTargetProgressTest() = runBlockingTest {
        val repo = MockLearningTargetsRepo()
        val useCase = GetAllTargetProgress(repo)

        val learningTarget = LearningTarget("abc123", "test")

        val taskObjectiveProgress1 = TaskObjectiveProgress("test","task 1.0", Mastery.NOT_GRADED)
        val taskObjectiveProgress2 = TaskObjectiveProgress("test","task 2.0", Mastery.NOT_MASTERED)

        val objectiveProgress1 = ObjectiveProgress("test","Learning Objective 1", listOf(taskObjectiveProgress1,taskObjectiveProgress2))

        assertEquals(listOf<TargetProgress>(), useCase.execute())
        repo.data.add(TargetProgress(learningTarget, listOf(objectiveProgress1)))
        assertEquals(listOf(TargetProgress(learningTarget, listOf(objectiveProgress1))),useCase.execute())
    }


}