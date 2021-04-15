package edu.calpoly.flipted.businesslogic

import edu.calpoly.flipted.businesslogic.mc_question.*
import edu.calpoly.flipted.businesslogic.mc_question.McQuestion
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.Assert.*
import java.util.*

class TestQuizzes {
    class MockQuizRepo : QuestionsRepo {
        val data = mutableListOf<McQuestion>()
        override suspend fun getAllQuestions(id: String) = data
    }


    @Test
    fun getAllQuestionsTest() = runBlockingTest {
        val repo = MockQuizRepo()
        val useCase = GetAllQuestions(repo)
        val taskId = "30b931d1f75"

        val options_one = listOf(Option(0, "atamos"),
            Option(1, "neutron"),
            Option(2, "nucleon"),
            Option(3, "nucleus"))


        assertEquals(listOf<McQuestion>(), useCase.execute(taskId))
        repo.data.add(McQuestion("The center of an atom is called the ______.", "1a860b", taskId,
        options_one, listOf(3), 2))

        assertEquals(listOf(
            McQuestion("The center of an atom is called the ______.", "1a860b", taskId,
                options_one, listOf(3), 2)
        ), useCase.execute(taskId))

        val options_two = listOf(Option(0, "positive"),
            Option(1, "neutral"),
            Option(2, "negative"))

        repo.data.add(McQuestion("An electron has ______ charge.", "2ef967", taskId,
            options_two, listOf(2), 2))

        assertEquals(listOf(
            McQuestion("The center of an atom is called the ______.", "1a860b", taskId,
                options_one, listOf(3), 2),
            McQuestion("An electron has ______ charge.", "2ef967", taskId,
                options_two, listOf(2), 2)
        ), useCase.execute(taskId))




    }
}