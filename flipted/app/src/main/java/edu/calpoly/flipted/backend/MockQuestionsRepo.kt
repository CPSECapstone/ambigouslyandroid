package edu.calpoly.flipted.backend

import android.util.Log
import com.apollographql.apollo.ApolloClient
import edu.calpoly.flipted.businesslogic.quizzes.*

class MockQuestionsRepo : QuestionsRepo {

    private val mockQuiz = Quiz(1, listOf(Question("The center of an atom is called the ______.", 1, 1, mutableListOf(
            Answer("atomos", 1, isChecked = false),
            Answer("neutron", 1, isChecked = false),
            Answer("nucleus", 1, isChecked = false),
            Answer("nucleon", 1, isChecked = false))),
        Question("The basic unit of all organisms is ______.", 2, 2, mutableListOf(
            Answer("the nucleus", 1, isChecked = false),
            Answer("tissue", 1, isChecked = false),
            Answer("the cell", 1, isChecked = false),
            Answer("the organ", 1, isChecked = false),
            Answer("the atom", 1, isChecked = false)))))

    private val mockResult = QuizResult(1,
            listOf(Question("The center of an atom is called the ______.", 1, 1, mutableListOf(
                    Answer("atomos", 1, isChecked = false, isCorrect = false),
                    Answer("neutron", 1, isChecked = false, isCorrect = false),
                    Answer("nucleus", 1, isChecked = true, isCorrect = true),
                    Answer("nucleon", 1, isChecked = false, isCorrect = false))),
                Question("The basic unit of all organisms is ______.", 2, 2, mutableListOf(
                    Answer("the nucleus", 1, isChecked = false, isCorrect = false),
                    Answer("tissue", 1, isChecked = false, isCorrect = false),
                    Answer("the cell", 1, isChecked = false, isCorrect = true),
                    Answer("the organ", 1, isChecked = false, isCorrect = false),
                    Answer("the atom", 1, isChecked = true, isCorrect = false)))),
            1)

    override suspend fun getQuiz(quizId : Int): Quiz = mockQuiz

    override suspend fun saveCompletionForQuiz(quiz : Quiz) : QuizResult {
        Log.i("MockQuestionsRepo", "Saving completion for quiz ${quiz.uid}")
        return mockResult
    }

    override suspend fun getQuizResult(resultId: Int): QuizResult {
        if(resultId != mockResult.uid)
            throw IllegalArgumentException("No mock exists for result with id $resultId")
        return mockResult
    }
}