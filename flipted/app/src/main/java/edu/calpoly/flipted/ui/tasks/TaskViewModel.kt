package edu.calpoly.flipted.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.calpoly.flipted.backend.MockTasksRepo
import edu.calpoly.flipted.businesslogic.tasks.data.TaskRubricProgress
import edu.calpoly.flipted.backend.ApolloTasksRepo
import edu.calpoly.flipted.businesslogic.tasks.SubmitTaskProgress
import edu.calpoly.flipted.businesslogic.quizzes.data.StudentAnswerInput
import edu.calpoly.flipted.businesslogic.tasks.GetTask
import edu.calpoly.flipted.businesslogic.tasks.SaveTaskProgress
import edu.calpoly.flipted.businesslogic.tasks.SubmitTask
import edu.calpoly.flipted.businesslogic.tasks.data.Task
import edu.calpoly.flipted.businesslogic.tasks.data.TaskProgress
import edu.calpoly.flipted.businesslogic.tasks.data.TaskSubmissionResult
import kotlinx.coroutines.launch


class TaskViewModel : ViewModel(){
    private val _currTask : MutableLiveData<Task> = MutableLiveData()
    private val _currResponse : MutableLiveData<TaskSubmissionResult> = MutableLiveData()

    private val mockRepo = MockTasksRepo()
    private val repo = ApolloTasksRepo()
    private val getTaskUseCase = GetTask(mockRepo)
    private val submitTaskProgress = SubmitTaskProgress(repo)
    private val submitTaskUseCase = SubmitTask(mockRepo)
    private val saveTaskProgressUseCase = SaveTaskProgress(mockRepo)

    val currTask : LiveData<Task>
        get() = _currTask

    val currResponse : LiveData<TaskSubmissionResult>
        get() = _currResponse

    fun fetchTask(taskId : Int) {
        viewModelScope.launch {
            _currTask.value = getTaskUseCase.execute(taskId)
        }
    }


    fun submitProgress(taskProgress: TaskRubricProgress) {
        viewModelScope.launch {
            submitTaskProgress.execute(taskProgress)
        }
    }

    fun submitTask(taskId : String) {
        viewModelScope.launch {
            _currResponse.value = submitTaskUseCase.execute(taskId)
        }
    }

    private val questionAnswers = mutableMapOf<Int, StudentAnswerInput>()

    fun saveQuizAnswer(answer: StudentAnswerInput) {
        val task = currTask.value ?: throw IllegalStateException("No task")

        questionAnswers[answer.questionId] = answer

        val progress = TaskProgress(listOf(), listOf(answer), task)
        viewModelScope.launch {
            saveTaskProgressUseCase.execute(progress)
        }
    }

    fun getQuizAnswers() : Collection<StudentAnswerInput> = questionAnswers.values

}