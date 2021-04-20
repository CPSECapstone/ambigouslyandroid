package edu.calpoly.flipted.ui.tasks.viewholders

import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.quizzes.data.questions.MultipleChoiceQuestion
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.QuizBlock
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.TaskBlock

class QuizBlockViewHolder(view : View, val inflater: LayoutInflater) : TaskBlockViewHolder(view) {
    private val rootLayout : LinearLayout = view.findViewById(R.id.task_block_quiz_root)

    override fun bind(block: TaskBlock) {
        val quizBlock = block as QuizBlock

        quizBlock.questions.forEach { question ->
            when(question) {
                is MultipleChoiceQuestion -> {
                    val questionLayout = inflater.inflate(R.layout.mc_quiz_question, rootLayout, false)
                    val questionText : TextView = questionLayout.findViewById(R.id.mc_question)
                    val answers : RadioGroup = questionLayout.findViewById(R.id.answers)

                    questionText.text = question.question

                    question.options.forEach { answerOption ->
                        val answerLayout = inflater.inflate(R.layout.mc_quiz_answer_option, answers, false) as RadioButton
                        answerLayout.text = answerOption.displayPrompt
                        answers.addView(answerLayout)
                    }
                    rootLayout.addView(questionLayout)
                }
                else -> throw IllegalArgumentException("Unknown question type")
            }
        }
    }
}