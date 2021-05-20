package edu.calpoly.flipted.businesslogic


import edu.calpoly.flipted.businesslogic.targets.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.Assert.*


class TestLearningTargets {
    class MockLearningTargetsRepo : LearningTargetsRepo  {
        val data = mutableListOf<TargetProgress>()
        override suspend fun getAllTargetProgress(courseId: String, studentId: String?) = data
    }


    @ExperimentalCoroutinesApi
    @Test
    fun getAllTargetProgressEmpty() = runBlockingTest {
        val repo = MockLearningTargetsRepo()
        val useCase = GetAllTargetProgress(repo)

        assertEquals(listOf<TargetProgress>(), useCase.execute())
    }


    @ExperimentalCoroutinesApi
    @Test
    fun getAllTargetProgressSameData() = runBlockingTest {
        val repo = MockLearningTargetsRepo()
        val useCase = GetAllTargetProgress(repo)

        val learningTarget = LearningTarget("abc123", "test")

        val taskObjectiveProgress1 = TaskObjectiveProgress("test","task 1.0", "test", "Learning Objective 1", Mastery.NOT_GRADED)
        val taskObjectiveProgress2 = TaskObjectiveProgress("test","task 2.0", "test", "Learning Objective 1", Mastery.NOT_MASTERED)

        val objectiveProgress1 = ObjectiveProgress("test","Learning Objective 1", listOf(taskObjectiveProgress1,taskObjectiveProgress2))

        assertEquals(listOf<TargetProgress>(), useCase.execute())
        repo.data.add(TargetProgress(learningTarget, listOf(objectiveProgress1)))
        assertEquals(listOf(TargetProgress(learningTarget, listOf(objectiveProgress1))),useCase.execute())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getAllTargetProgressTestDifferentObjectives() = runBlockingTest {
        val repo = MockLearningTargetsRepo()
        val useCase = GetAllTargetProgress(repo)

        val learningTarget = LearningTarget("abc123", "test")

        val taskObjectiveProgress1 = TaskObjectiveProgress("test","task 1.0", "test", "Learning Objective 1", Mastery.NOT_GRADED)
        val taskObjectiveProgress2 = TaskObjectiveProgress("test","task 2.0", "test", "Learning Objective 1", Mastery.NOT_MASTERED)

        val objectiveProgress1 = ObjectiveProgress("test","Learning Objective 1", listOf(taskObjectiveProgress1,taskObjectiveProgress2))
        val objectiveProgress2 = ObjectiveProgress("test","Learning Objective 2", listOf(taskObjectiveProgress1,taskObjectiveProgress2))

        repo.data.add(TargetProgress(learningTarget, listOf(objectiveProgress1)))
        assertNotEquals(listOf(TargetProgress(learningTarget, listOf(objectiveProgress2))),useCase.execute())
    }


    @ExperimentalCoroutinesApi
    @Test
    fun getAllTargetProgressTestMultipleObjectiveProgress() = runBlockingTest {
        val repo = MockLearningTargetsRepo()
        val useCase = GetAllTargetProgress(repo)

        val learningTarget = LearningTarget("abc123", "test")

        val taskObjectiveProgress1 = TaskObjectiveProgress("test","task 1.0", "test", "Learning Objective 1", Mastery.NOT_GRADED)
        val taskObjectiveProgress2 = TaskObjectiveProgress("test","task 2.0", "test", "Learning Objective 1", Mastery.NOT_MASTERED)

        val objectiveProgress1 = ObjectiveProgress("test","Learning Objective 1", listOf(taskObjectiveProgress1,taskObjectiveProgress2))
        val objectiveProgress2 = ObjectiveProgress("test","Learning Objective 2", listOf(taskObjectiveProgress1,taskObjectiveProgress2))
        val objectiveProgress3 = ObjectiveProgress("test","Learning Objective 3", listOf(taskObjectiveProgress1,taskObjectiveProgress2))
        val objectiveProgress4 = ObjectiveProgress("test","Learning Objective 4", listOf(taskObjectiveProgress1,taskObjectiveProgress2))

        repo.data.add(TargetProgress(learningTarget, listOf(objectiveProgress1,objectiveProgress2,objectiveProgress3,objectiveProgress4)))
        assertEquals(listOf(TargetProgress(learningTarget, listOf(objectiveProgress1,objectiveProgress2,objectiveProgress3,objectiveProgress4))),useCase.execute())
    }


    @ExperimentalCoroutinesApi
    @Test
    fun getAllTargetProgressTestMultipleTaskObjectiveProgress() = runBlockingTest {
        val repo = MockLearningTargetsRepo()
        val useCase = GetAllTargetProgress(repo)

        val learningTarget = LearningTarget("abc123", "test")

        val taskObjectiveProgress1 = TaskObjectiveProgress("test","task 1.0", "test", "Learning Objective 1", Mastery.MASTERED)
        val taskObjectiveProgress2 = TaskObjectiveProgress("test","task 2.0", "test", "Learning Objective 4", Mastery.NOT_MASTERED)
        val taskObjectiveProgress3 = TaskObjectiveProgress("test","task 3.0", "test", "Learning Objective 1", Mastery.NOT_GRADED)
        val taskObjectiveProgress4 = TaskObjectiveProgress("test","task 4.0", "test", "Learning Objective 3", Mastery.NOT_MASTERED)
        val taskObjectiveProgress5 = TaskObjectiveProgress("test","task 5.0", "test", "Learning Objective 2", Mastery.NOT_GRADED)
        val taskObjectiveProgress6 = TaskObjectiveProgress("test","task 6.0", "test", "Learning Objective 1", Mastery.NEARLY_MASTERED)
        val taskObjectiveProgress7 = TaskObjectiveProgress("test","task 7.0", "test", "Learning Objective 3", Mastery.NOT_GRADED)
        val taskObjectiveProgress8 = TaskObjectiveProgress("test","task 8.0", "test", "Learning Objective 4", Mastery.NEARLY_MASTERED)

        val objectiveProgress1 = ObjectiveProgress("test","Learning Objective 1", listOf(taskObjectiveProgress1,taskObjectiveProgress2,taskObjectiveProgress3))
        val objectiveProgress2 = ObjectiveProgress("test","Learning Objective 2", listOf(taskObjectiveProgress1,taskObjectiveProgress2,taskObjectiveProgress6))
        val objectiveProgress3 = ObjectiveProgress("test","Learning Objective 3", listOf(taskObjectiveProgress4,taskObjectiveProgress5,taskObjectiveProgress7,taskObjectiveProgress8))
        val objectiveProgress4 = ObjectiveProgress("test","Learning Objective 4", listOf())

        repo.data.add(TargetProgress(learningTarget, listOf(objectiveProgress1,objectiveProgress2,objectiveProgress3,objectiveProgress4)))
        assertEquals(listOf(TargetProgress(learningTarget, listOf(objectiveProgress1,objectiveProgress2,objectiveProgress3,objectiveProgress4))),useCase.execute())
    }



}