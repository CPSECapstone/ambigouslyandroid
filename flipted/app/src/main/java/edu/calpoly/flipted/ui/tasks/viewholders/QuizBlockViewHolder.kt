package edu.calpoly.flipted.ui.tasks.viewholders

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.quizzes.data.StudentAnswerInput
import edu.calpoly.flipted.businesslogic.quizzes.data.answers.FreeResponseAnswer
import edu.calpoly.flipted.businesslogic.quizzes.data.answers.MultipleChoiceAnswer
import edu.calpoly.flipted.businesslogic.quizzes.data.questions.FreeResponseQuestion
import edu.calpoly.flipted.businesslogic.quizzes.data.questions.MultipleChoiceQuestion
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.QuizBlock
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.TaskBlock
import edu.calpoly.flipted.ui.tasks.TaskViewModel
import java.lang.IllegalStateException

class QuizBlockViewHolder(view: View, val inflater: LayoutInflater, private val viewModel: TaskViewModel) : TaskBlockViewHolder(view) {
    private val rootLayout: LinearLayout = view.findViewById(R.id.task_block_quiz_root)
    private val colorOfBlock: LinearLayout = view.findViewById(R.id.task_block_quiz_root)

    override fun bind(block: TaskBlock, position: Int) {
        val quizBlock = block as QuizBlock

        if (position % 2 != 0)
            colorOfBlock.setBackgroundColor(Color.parseColor("#F2F2F2"))
        else
            colorOfBlock.setBackgroundColor(Color.parseColor("#FFFFFF"))

        rootLayout.removeAllViews()

        quizBlock.questions.forEach { question ->
            when (question) {
                is MultipleChoiceQuestion -> {
                    val questionLayout = inflater.inflate(R.layout.task_question_mc, rootLayout, false)
                    val questionText: TextView = questionLayout.findViewById(R.id.mc_question)
                    val answers: RadioGroup = questionLayout.findViewById(R.id.answers)
                    questionText.text = question.question


                    question.options.forEach { answerOption ->
                        val answerLayout = inflater.inflate(R.layout.task_question_mc_answer_option, answers, false) as RadioButton
                        //val answerLayout: RadioButton = answerLinearLayouts.findViewById(R.id.question_answer)
                        answerLayout.text = answerOption.displayPrompt

                        answerLayout.setOnCheckedChangeListener { _, isChecked ->
                            if (isChecked)
                                viewModel.saveQuizAnswer(StudentAnswerInput(question.uid, MultipleChoiceAnswer(answerOption)), quizBlock)
                        }

                        answers.addView(answerLayout)

                        question.savedAnswer?.let {
                            if (answerOption.id == it.chosenAnswer.id) {
                                answers.check(answerLayout.id)
                            }
                        }


                    }

                    rootLayout.addView(questionLayout)
                }
                is FreeResponseQuestion -> {
                    val questionLayout = inflater.inflate(R.layout.task_question_free_response, rootLayout, false)
                    val questionText: TextView = questionLayout.findViewById(R.id.task_question_free_response_prompt)
                    val answerBox: EditText = questionLayout.findViewById(R.id.task_question_free_response_answer)

                    questionText.text = question.question



                    answerBox.setText(question.savedAnswer?.response ?: "")
                    answerBox.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                                p0: CharSequence?, p1: Int, p2: Int, p3: Int
                        ) {
                        }

                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                        override fun afterTextChanged(p0: Editable?) {
                            viewModel.saveQuizAnswer(StudentAnswerInput(question.uid, FreeResponseAnswer(p0.toString())), quizBlock)
                        }

                    })


                    rootLayout.addView(questionLayout)
                }
                else -> throw IllegalArgumentException("Unknown question type")
            }
        }
    }
}