package edu.calpoly.flipted.ui.tasks.viewholders

import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.quizzes.data.questions.FreeResponseQuestion
import edu.calpoly.flipted.businesslogic.quizzes.data.questions.MultipleChoiceQuestion
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.QuizBlock
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.TaskBlock
import edu.calpoly.flipted.ui.tasks.TaskViewModel

class QuizBlockViewHolder(view : View, val inflater: LayoutInflater) : TaskBlockViewHolder(view) {
    private val rootLayout : LinearLayout = view.findViewById(R.id.task_block_quiz_root)

    override fun bind(block: TaskBlock) {
        val quizBlock = block as QuizBlock

        val viewModel = TaskViewModel()

        quizBlock.questions.forEach { question ->
            when(question) {
                is MultipleChoiceQuestion -> {
                    val questionLayout = inflater.inflate(R.layout.task_question_mc, rootLayout, false)
                    val questionText : TextView = questionLayout.findViewById(R.id.mc_question)
                    val answers : RadioGroup = questionLayout.findViewById(R.id.answers)

                    questionText.text = question.question

                    question.options.forEach { answerOption ->
                        val answerLayout = inflater.inflate(R.layout.task_question_mc_answer_option, answers, false) as RadioButton
                        answerLayout.text = answerOption.displayPrompt
                        answers.addView(answerLayout)
                        answerLayout.setOnCheckedChangeListener { _, b ->
                            viewModel.submitQuestion()
                        }
                    }
                    rootLayout.addView(questionLayout)
                }
                is FreeResponseQuestion -> {
                    val questionLayout = inflater.inflate(R.layout.task_question_free_response, rootLayout, false)
                    val questionText : TextView = questionLayout.findViewById(R.id.task_question_free_response_prompt)

                    questionText.text = question.question
                    rootLayout.addView(questionLayout)
                }
                else -> throw IllegalArgumentException("Unknown question type")
            }
        }
    }
}