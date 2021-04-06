package edu.calpoly.flipted.ui.quizzes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.quizzes.Question

class QuestionListAdapter(private val context: Context) : BaseAdapter() {

    var questionsData: List<Question> = listOf()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val fillInView = convertView?:inflater.inflate(R.layout.mc_quiz_question, parent, false)
        val question = fillInView.findViewById<TextView>(R.id.mc_question)
        val answers = fillInView.findViewById<RadioGroup>(R.id.answers)

        val data = getItem(position)

        question.text = "${position+1}. ${data.title}"
        answers.removeAllViews()
        data.answers.forEach {
            val fillInAnswerView = inflater.inflate(R.layout.mc_quiz_answer_option, answers, false) as RadioButton
            fillInAnswerView.text = it.description
            answers.addView(fillInAnswerView)
        }
        return fillInView
    }

    override fun getItem(position: Int): Question {
        return questionsData[position]
    }

    override fun getItemId(position: Int): Long {
        return questionsData[position].uid.toLong()
    }

    override fun getCount(): Int {
        return questionsData.size
    }
}