package edu.calpoly.flipted.backend

import com.apollographql.apollo.ApolloClient
import edu.calpoly.flipted.businesslogic.mc_question.Question
import edu.calpoly.flipted.businesslogic.mc_question.Answer
import edu.calpoly.flipted.businesslogic.mc_question.QuestionsRepo

class ApolloQuestionsRepo : QuestionsRepo {
    private val apolloClient = ApolloClient.builder()
        .serverUrl("https://f6t0mvy5y0.execute-api.us-east-1.amazonaws.com/dev/graphql")
        .build()

    override suspend fun getAllQuestions(): List<Question> {
        val answerOne = Answer("atomos", 1, isCorrect = false, isChecked = false)
        val answerTwo = Answer("neutron", 1, isCorrect = false, isChecked = false)
        val answerThree = Answer("nucleus", 1, isCorrect = true, isChecked = false)
        val answerFour = Answer("nucleon", 1, isCorrect = false, isChecked = false)
        val answerList = mutableListOf<Answer>(answerOne, answerTwo, answerThree, answerFour)

        return listOf(Question("The center of an atom is called the ______.", 1, 1, answerList))
    }


}