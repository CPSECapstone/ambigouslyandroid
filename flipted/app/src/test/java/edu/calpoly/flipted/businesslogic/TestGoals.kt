package edu.calpoly.flipted.businesslogic

import edu.calpoly.flipted.businesslogic.goals.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.Assert.*
import java.util.*

class TestGoals {
    class MockGoalsRepo : GoalsRepo {
        val data = mutableListOf<Goal>()
        override suspend fun getAllGoals() = data

        override suspend fun getGoalById(id: Int) = data.find {it.uid == id}

        override suspend fun saveNewCompletion(completion: GoalCompletion): Goal? {
            val goal = data.find{it.uid == completion.parentId}
            goal?.completions?.add(completion)
            return goal
        }

    }

    @Test
    fun getAllGoalsTest() = runBlockingTest {
        val repo = MockGoalsRepo()
        val useCase = GetAllGoals(repo)

        val date = Date()
        val date2 = Date(1600000000)

        assertEquals(listOf<Goal>(), useCase.execute())
        repo.data.add(Goal("Test", 2, date, 5, "testunit", mutableListOf()))
        assertEquals(
            listOf(Goal("Test", 2, date, 5, "testunit", mutableListOf())),
            useCase.execute())
        repo.data[0].completions.add(GoalCompletion("Test Completion", 2, date2))
        assertEquals(
            listOf(
                Goal("Test", 2, date, 5, "testunit", mutableListOf(
                GoalCompletion("Test Completion", 2, date2)
                ))
            ),
            useCase.execute())
        repo.data.add(Goal("Test 2", 4, date, 8, "testunit", mutableListOf()))
        assertEquals(
            listOf(
                Goal("Test", 2, date, 5, "testunit", mutableListOf(
                    GoalCompletion("Test Completion", 2, date2)
                )),
                Goal("Test 2", 4, date, 8, "testunit", mutableListOf())
            ),
            useCase.execute())
    }

    @Test
    fun saveCompletionTest() = runBlockingTest {
        val repo = MockGoalsRepo()
        val useCase = SaveNewCompletion(repo)

        val date = Date()
        val date2 = Date(1600000000)

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, 14)

        val futureDate = calendar.time

        repo.data.add(Goal("Test", 2, date, 5, "testunit", mutableListOf()))
        repo.data.add(Goal("Test 2", 4, date, 8, "testunit", mutableListOf()))

        assertEquals(
            listOf(
                Goal("Test", 2, date, 5, "testunit", mutableListOf()),
                Goal("Test 2", 4, date, 8, "testunit", mutableListOf())
            ),
            repo.data)

        assertNotNull(useCase.execute(GoalCompletion("Test Completion", 2, date2)))
        assertEquals(
            listOf(
                Goal("Test", 2, date, 5, "testunit", mutableListOf(
                    GoalCompletion("Test Completion", 2, date2)
                )),
                Goal("Test 2", 4, date, 8, "testunit", mutableListOf())
            ),
            repo.data)

        assertNull(useCase.execute(GoalCompletion("  ", 2, date2)))
        assertNull(useCase.execute(
            GoalCompletion("Test Completion", -1, date2)
        ))
        assertNull(useCase.execute(
            GoalCompletion("Test Completion", 2, futureDate)
        ))
    }
}