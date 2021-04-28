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
                    if (viewModel.currResponse.value != null) {

                        val currResult = viewModel.currResponse.value ?: throw IllegalStateException("No response found")
                        val resultList = currResult.results
                        val questionResult = resultList.find{it.questionId == question.uid}

                        if (questionResult == null) {
                            throw IllegalStateException("Question not found")
                        }

                        question.options.forEach { answerOption ->
                            val answerLayout = inflater.inflate(R.layout.task_question_mc_answer_result, answers, false)

                            val result = answerLayout.findViewById(R.id.result_radio) as RadioButton

                            val score = questionLayout.findViewById(R.id.question_score) as TextView

                            score.text = questionResult.pointsAwarded.toString() + "/" + question.pointValue.toString() + " points"
                            result.text = answerOption.displayPrompt
                            val resultText = answerLayout.findViewById(R.id.result_text) as TextView

                            // Check if the current answerOption is a correct answer
                            if (questionResult.correctAnswer.contains(answerOption.id.toString())) {
                                result.setChecked(true)
                                if (questionResult.correctAnswer.contains(questionResult.studentAnswer)) {
                                    resultText.text = "Correct!"
                                    resultText.setTextColor(Color.parseColor("#66a266"))
                                    resultText.setVisibility(View.VISIBLE)
                                }
                                else {
                                    resultText.text = "Correct Response"
                                    resultText.setTextColor(Color.parseColor("#bcd9ea"))
                                    resultText.setVisibility(View.VISIBLE)
                                }


                            }
                            // Check if the current answerOption is what the student selected
                            else if (answerOption.id == questionResult.studentAnswer.toInt()) {
                                result.setChecked(true)
                                resultText.text = "Incorrect Response"
                                resultText.setTextColor(Color.parseColor("#d03128"))
                                resultText.setVisibility(View.VISIBLE)
                            }

                            result.setEnabled(false)
                            answers.addView(answerLayout)
                            score.setVisibility(View.VISIBLE)

                        }
                    }
                    else {

                        question.options.forEach { answerOption ->
                            val answerLayout = inflater.inflate(R.layout.task_question_mc_answer_option, answers, false) as RadioButton
                            answerLayout.text = answerOption.displayPrompt

                            answerLayout.setOnCheckedChangeListener { _, isChecked ->
                                if(isChecked)
                                    viewModel.saveQuizAnswer(StudentAnswerInput(question.uid, MultipleChoiceAnswer(answerOption)), quizBlock)
                            }

                            answers.addView(answerLayout)

                            question.savedAnswer?.let {
                                if(answerOption.id == it.chosenAnswer.id) {
                                    answers.check(answerLayout.id)
                                }
                            }


                        }
                    }
                    rootLayout.addView(questionLayout)
                }
                is FreeResponseQuestion -> {
                    val questionLayout = inflater.inflate(R.layout.task_question_free_response, rootLayout, false)
                    val questionText : TextView = questionLayout.findViewById(R.id.task_question_free_response_prompt)
                    val answerBox : EditText = questionLayout.findViewById(R.id.task_question_free_response_answer)

                    questionText.text = question.question

                    if (viewModel.isSubmitted) {

                        val currResult = viewModel.currResponse.value ?: throw IllegalStateException("No response found")
                        val resultList = currResult.results
                        val questionResult = resultList.find{it.questionId == question.uid}

                        if (questionResult == null) {
                            throw IllegalStateException("Question not found")
                        }
                        answerBox.setVisibility(View.GONE)
                        val score = questionLayout.findViewById(R.id.fr_score) as TextView
                        val resultText = questionLayout.findViewById(R.id.task_question_free_response_result) as TextView
                        val studentAnswer = questionLayout.findViewById(R.id.task_question_free_response_student_answer) as TextView
                        score.text = questionResult.pointsAwarded.toString() + "/" + question.pointValue.toString() + " points"
                        studentAnswer.text = questionResult.studentAnswer
                        resultText.setVisibility(View.VISIBLE)
                        studentAnswer.setVisibility(View.VISIBLE)
                        score.setVisibility(View.VISIBLE)
                    }
                    else {

                        answerBox.setText(question.savedAnswer?.response ?: "")
                        answerBox.addTextChangedListener(object : TextWatcher {
                            override fun beforeTextChanged(
                                    p0: CharSequence?, p1: Int, p2: Int, p3: Int
                            ) {}
                            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                            override fun afterTextChanged(p0: Editable?) {
                                viewModel.saveQuizAnswer(StudentAnswerInput(question.uid, FreeResponseAnswer(p0.toString())), quizBlock)
                            }

                        })
                    }

                    rootLayout.addView(questionLayout)
                }
                else -> throw IllegalArgumentException("Unknown question type")
            }
        }
    }
}