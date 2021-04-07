package edu.calpoly.flipted.backend

import android.util.Log
import com.apollographql.apollo.ApolloClient
import edu.calpoly.flipted.businesslogic.quizzes.Question
import edu.calpoly.flipted.businesslogic.quizzes.Answer
import edu.calpoly.flipted.businesslogic.quizzes.QuestionsRepo
import edu.calpoly.flipted.businesslogic.quizzes.Quiz

class MockQuestionsRepo : QuestionsRepo {
    override suspend fun getQuestionsForTask(taskId : Int): Quiz
		= Quiz(1, listOf(Question("The center of an atom is called the ______.", 1, 1, mutableListOf(
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

    override suspend fun saveCompletionForQuiz(quiz : Quiz) {
        Log.i("MockQuestionsRepo", "Saving completion for quiz ${quiz.uid}")
    }
}