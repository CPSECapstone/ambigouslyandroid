package edu.calpoly.flipted.ui.tasks.viewholders

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.material.color.MaterialColors.getColor
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

class ResultBlockViewHolder(view: View, val inflater: LayoutInflater, private val viewModel: TaskViewModel, val context: Context) : TaskBlockViewHolder(view) {
    private val rootLayout: LinearLayout = view.findViewById(R.id.task_block_quiz_root)

    override fun bind(block: TaskBlock, position: Int) {
        val quizBlock = block as QuizBlock

        rootLayout.removeAllViews()

        quizBlock.questions.forEach { question ->
            when (question) {
                is MultipleChoiceQuestion -> {
                    val questionLayout = inflater.inflate(R.layout.task_question_mc, rootLayout, false)
                    val questionText: TextView = questionLayout.findViewById(R.id.mc_question)
                    val answers: RadioGroup = questionLayout.findViewById(R.id.answers)
                    val questionNum: TextView = questionLayout.findViewById(R.id.results_mc_question_num)

                    questionText.text = question.question
                    questionNum.visibility = View.VISIBLE
                    questionNum.text = "Question ${position + 1}:"

                    val currResult = viewModel.currResponse.value
                    if (currResult != null) {

                        val resultList = currResult.results
                        val questionResult = resultList.find { it.questionId == question.uid }
                                ?: return@forEach

                        question.options.forEach { answerOption ->
                            val answerLayout = inflater.inflate(R.layout.task_question_mc_answer_result, answers, false)

                            val result = answerLayout.findViewById(R.id.result_radio) as RadioButton

                            val score = questionLayout.findViewById(R.id.question_score) as TextView

                            score.text = "${questionResult.pointsAwarded} / ${question.pointValue} points"
                            result.text = answerOption.displayPrompt

                            // Check if the current answerOption is a correct answer
                            if (questionResult.correctAnswer.contains(answerOption.id.toString())) {
                                result.setBackgroundResource(R.drawable.quiz_box_correct)
                                if (questionResult.correctAnswer.contains(questionResult.studentAnswer)) {
                                    result.isChecked = true

                                }


                            }
                            // Check if the current answerOption is what the student selected
                            else if (answerOption.id == questionResult.studentAnswer.toInt()) {
                                result.isChecked = true
                                result.setBackgroundResource(R.drawable.quiz_box_incorrect)
                            }

                            result.setEnabled(false)
                            answers.addView(answerLayout)
                            score.visibility = View.VISIBLE

                        }


                        rootLayout.addView(questionLayout)
                    }
                }
                is FreeResponseQuestion -> {
                    val questionLayout = inflater.inflate(R.layout.task_question_free_response, rootLayout, false)
                    val questionText: TextView = questionLayout.findViewById(R.id.task_question_free_response_prompt)
                    val answerBox: EditText = questionLayout.findViewById(R.id.task_question_free_response_answer)
                    val questionNum: TextView = questionLayout.findViewById(R.id.results_fr_question_num)
                    val feedbackTitle: TextView = questionLayout.findViewById(R.id.task_fr_feedback_title)
                    val feedback: TextView = questionLayout.findViewById(R.id.task_fr_feedback)

                    questionText.text = question.question
                    questionNum.visibility = View.VISIBLE
                    questionNum.text = "Question ${position + 1}:"


                    val currResult = viewModel.currResponse.value

                    if (currResult != null) {
                        val resultList = currResult.results
                        val questionResult = resultList.find { it.questionId == question.uid }
                                ?: return@forEach

                        answerBox.setVisibility(View.GONE)
                        val score = questionLayout.findViewById(R.id.fr_score) as TextView
                        val resultText = questionLayout.findViewById(R.id.task_question_free_response_result) as TextView
                        val studentAnswer = questionLayout.findViewById(R.id.task_question_free_response_student_answer) as TextView
                        score.text = "${questionResult.pointsAwarded} / ${question.pointValue} points"
                        studentAnswer.text = questionResult.studentAnswer
                        resultText.setVisibility(View.VISIBLE)
                        studentAnswer.setVisibility(View.VISIBLE)
                        score.setVisibility(View.VISIBLE)


                        rootLayout.addView(questionLayout)
                    }
                }
            }
        }
    }
}