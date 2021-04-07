package edu.calpoly.flipted.backend

import android.util.Log
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import edu.calpoly.flipted.GetQuizQuery
import edu.calpoly.flipted.GetQuizQuestionsQuery
import edu.calpoly.flipted.businesslogic.mc_question.McQuestion
import edu.calpoly.flipted.businesslogic.mc_question.Option
import edu.calpoly.flipted.businesslogic.mc_question.QuestionsRepo
import java.util.*

class ApolloQuestionsRepo : ApolloRepo(), QuestionsRepo {

    override suspend fun getAllQuestions(id : String): List<McQuestion> {
        val response = try {
            apolloClient().query(GetQuizQuestionsQuery(id)).await()
        } catch(e: ApolloException) {
            e.printStackTrace()
            Log.e("ApolloQuestionsRepo", "Error when querying backend", e)
            return listOf()
        }

        if(response.hasErrors() || response.data == null) {
            Log.e("ApolloQuestionsRepo", "Error when querying backend: bad response")
            return listOf()
        }
/*
        return try {
            response.data!!.quiz!!.questions!!.map {
                McQuestion(it!!.description, it.id!!, it.quizId!!,
                    it.options!!.map { opt ->
                        Option(
                            opt!!.id,
                            opt!!.description)
                    },
                    it.answers!!, it.points!!
                        .toMutableList())
            }
            */
        return try {
            response.data!!.questions!!.map {
                McQuestion(it!!.description, it!!.id, id,
                    it.options!!.map { opt ->
                        Option(
                            opt!!.id,
                            opt.description)
                    },
                    it.answers as List<String>?, it.points
                        )
            }
        } catch(e : NullPointerException) {
            Log.e("ApolloQuestionsRepo", "Failed null check when processing received data")
            listOf()
        }
    }


}