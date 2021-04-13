package edu.calpoly.flipted.businesslogic

import edu.calpoly.flipted.businesslogic.mc_question.*
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
        val repo = TestQuizzes.MockQuizRepo()
        val useCase = GetAllQuestions(repo)

        repo.data.add()


    }
}