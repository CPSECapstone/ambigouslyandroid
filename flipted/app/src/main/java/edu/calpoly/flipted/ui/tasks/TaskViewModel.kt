package edu.calpoly.flipted.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.calpoly.flipted.backend.ApolloTasksRepo
import edu.calpoly.flipted.businesslogic.quizzes.data.StudentAnswerInput
import edu.calpoly.flipted.businesslogic.tasks.GetTask
import edu.calpoly.flipted.businesslogic.tasks.SaveTaskProgress
import edu.calpoly.flipted.businesslogic.tasks.SubmitTask
import edu.calpoly.flipted.businesslogic.tasks.data.*
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.QuizBlock
import kotlinx.coroutines.launch


class TaskViewModel : ViewModel(){
    private var _taskIsPending = false
    private val _currTask : MutableLiveData<Task> = MutableLiveData()
    private var _currResponse : MutableLiveData<TaskSubmissionResult> = MutableLiveData()
    private val _errorMessage : MutableLiveData<String> = MutableLiveData()
    val errorMessage : LiveData<String>
        get() = _errorMessage

    private val repo = ApolloTasksRepo()
    private val getTaskUseCase = GetTask(repo)
    private val submitTaskUseCase = SubmitTask(repo)
    private val saveTaskProgressUseCase = SaveTaskProgress(repo)


    val taskIsPending
        get() = _taskIsPending
    val currTask : LiveData<Task>
        get() = _currTask

    val currResponse : LiveData<TaskSubmissionResult>
        get() = _currResponse

    fun fetchTask(taskId : String) {
        _taskIsPending = true
        viewModelScope.launch {
            try {
                _currTask.value = getTaskUseCase.execute(taskId)
            } catch (e: RuntimeException) {
                _errorMessage.value = e.message
                return@launch
            }
            val task = _currTask.value
            task!!.requirements.forEach { r ->
                requirements[r.uid] = r
            }
            _taskIsPending = false
        }
    }

    private val requirements : MutableMap<String, RubricRequirement> = mutableMapOf()

    fun saveRubricRequirement(requirement: RubricRequirement) {
        val task = currTask.value ?: throw IllegalStateException("No task")

        requirements[requirement.uid] = requirement

        val requirementProgress = TaskRubricProgress(requirements.values.filter{it.isComplete}.toList(), task)
        viewModelScope.launch {
            try {
                saveTaskProgressUseCase.saveRubricProgress(requirementProgress)
            } catch (e: RuntimeException) {
                _errorMessage.value = e.message
            }
        }
    }

    private val questionAnswers = mutableMapOf<String, StudentAnswerInput>()

    fun saveQuizAnswer(answer: StudentAnswerInput, block: QuizBlock) {
        val task = currTask.value ?: throw IllegalStateException("No task")

        questionAnswers[answer.questionId] = answer

        val answerProgress = TaskQuizAnswer(answer, task, block)

        viewModelScope.launch {
            saveTaskProgressUseCase.saveQuizAnswer(answerProgress)
        }
    }

    fun getQuizAnswers() : Collection<StudentAnswerInput> = questionAnswers.values


    fun submitTask(taskId : String) {
        viewModelScope.launch {
            _currResponse.value = submitTaskUseCase.execute(taskId)
        }
    }

}