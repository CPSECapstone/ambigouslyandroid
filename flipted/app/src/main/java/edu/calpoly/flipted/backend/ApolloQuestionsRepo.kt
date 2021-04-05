package edu.calpoly.flipted.backend

import com.apollographql.apollo.ApolloClient
import edu.calpoly.flipted.businesslogic.mc_question.Question
import edu.calpoly.flipted.businesslogic.mc_question.Answer
import edu.calpoly.flipted.businesslogic.mc_question.QuestionsRepo

class ApolloQuestionsRepo : QuestionsRepo {
    private val apolloClient = ApolloClient.builder()
        .serverUrl("https://f6t0mvy5y0.execute-api.us-east-1.amazonaws.com/dev/graphql")
        .build()

    override suspend fun getAllQuestions(): List<Question>
		= listOf(Question("The center of an atom is called the ______.", 1, 1, mutableListOf(
                Answer("atomos", 1, isCorrect = false, isChecked = false),
                Answer("neutron", 1, isCorrect = false, isChecked = false),
                Answer("nucleus", 1, isCorrect = true, isChecked = false),
                Answer("nucleon", 1, isCorrect = false, isChecked = false))),
            Question("The basic unit of all organisms is ______.", 2, 2, mutableListOf(
                Answer("the nucleus", 1, isCorrect = false, isChecked = false),
                Answer("tissue", 1, isCorrect = false, isChecked = false),
                Answer("the cell", 1, isCorrect = true, isChecked = false),
                Answer("the organ", 1, isCorrect = false, isChecked = false),
                Answer("the atom", 1, isCorrect = false, isChecked = false))))


}