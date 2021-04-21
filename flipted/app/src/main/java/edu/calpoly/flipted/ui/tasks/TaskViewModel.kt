package edu.calpoly.flipted.ui.tasks

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.calpoly.flipted.backend.MockTasksRepo
import edu.calpoly.flipted.businesslogic.quizzes.Answer
import edu.calpoly.flipted.businesslogic.quizzes.data.StudentAnswerInput
import edu.calpoly.flipted.businesslogic.quizzes.data.answers.AnswerType
import edu.calpoly.flipted.businesslogic.tasks.GetTask
import edu.calpoly.flipted.businesslogic.tasks.data.Task
import kotlinx.coroutines.launch


class TaskViewModel : ViewModel(){
    private val _currTask : MutableLiveData<Task> = MutableLiveData()

    private val mockRepo = MockTasksRepo()
    private val getTaskUseCase = GetTask(mockRepo)

    val currTask : LiveData<Task>
        get() = _currTask

    fun fetchTask(taskId : Int) {
        viewModelScope.launch {
            _currTask.value = getTaskUseCase.execute(taskId)
        }
    }

    private val questionAnswers = mutableMapOf<Int, StudentAnswerInput>()

    fun saveQuizAnswer(answer: StudentAnswerInput) {
        questionAnswers[answer.questionId] = answer
    }

    fun getQuizAnswers() : Collection<StudentAnswerInput> = questionAnswers.values
}