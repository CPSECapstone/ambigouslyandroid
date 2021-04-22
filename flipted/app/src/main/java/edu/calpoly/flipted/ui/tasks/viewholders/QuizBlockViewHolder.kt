package edu.calpoly.flipted.ui.tasks.viewholders

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.quizzes.data.StudentAnswerInput
import edu.calpoly.flipted.businesslogic.quizzes.data.answers.FreeResponseAnswer
import edu.calpoly.flipted.businesslogic.quizzes.data.answers.MultipleChoiceAnswer
import edu.calpoly.flipted.businesslogic.quizzes.data.questions.FreeResponseQuestion
import edu.calpoly.flipted.businesslogic.quizzes.data.questions.MultipleChoiceQuestion
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.QuizBlock
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.TaskBlock
import edu.calpoly.flipted.ui.tasks.TaskViewModel

class QuizBlockViewHolder(view : View, val inflater: LayoutInflater, private val viewModel: TaskViewModel) : TaskBlockViewHolder(view) {
    private val rootLayout : LinearLayout = view.findViewById(R.id.task_block_quiz_root)

    override fun bind(block: TaskBlock) {
        val quizBlock = block as QuizBlock

        rootLayout.removeAllViews()

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

                        answerLayout.setOnCheckedChangeListener { _, isChecked ->
                            if(isChecked)
                                viewModel.saveQuizAnswer(StudentAnswerInput(question.uid, MultipleChoiceAnswer(answerOption)))
                        }

                        answers.addView(answerLayout)
                    }
                    rootLayout.addView(questionLayout)
                }
                is FreeResponseQuestion -> {
                    val questionLayout = inflater.inflate(R.layout.task_question_free_response, rootLayout, false)
                    val questionText : TextView = questionLayout.findViewById(R.id.task_question_free_response_prompt)
                    val answerBox : EditText = questionLayout.findViewById(R.id.task_question_free_response_answer)

                    questionText.text = question.question

                    answerBox.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            p0: CharSequence?, p1: Int, p2: Int, p3: Int
                        ) {}
                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                        override fun afterTextChanged(p0: Editable?) {
                            viewModel.saveQuizAnswer(StudentAnswerInput(question.uid, FreeResponseAnswer(p0.toString())))
                        }

                    })

                    rootLayout.addView(questionLayout)
                }
                else -> throw IllegalArgumentException("Unknown question type")
            }
        }
    }
}