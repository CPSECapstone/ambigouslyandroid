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
import edu.calpoly.flipted.businesslogic.quizzes.ValidationResponse

class QuestionListAdapter(private val context: Context) : BaseAdapter() {

    var questionsData: List<Question> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var validationIssues : List<ValidationResponse> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val fillInView = convertView?:inflater.inflate(R.layout.mc_quiz_question, parent, false)
        val question = fillInView.findViewById<TextView>(R.id.mc_question)
        val answers = fillInView.findViewById<RadioGroup>(R.id.answers)
        val issueText = fillInView.findViewById<TextView>(R.id.question_issue_text)

        val data = getItem(position)

        val issue = validationIssues.find{issue -> issue.subject == data}
        if(issue != null) {
            issueText.text = issue.message
            issueText.visibility = View.VISIBLE
        } else {
            issueText.visibility = View.GONE
        }

        question.text = "${position+1}. ${data.title}"
        answers.removeAllViews()
        data.answers.forEach {
            val fillInAnswerView = inflater.inflate(R.layout.mc_quiz_answer_option, answers, false) as RadioButton
            fillInAnswerView.text = it.description
            fillInAnswerView.setOnCheckedChangeListener { _, b ->
                it.isChecked = b
            }
            answers.addView(fillInAnswerView)
            if(it.isChecked)
                answers.check(fillInAnswerView.id)
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