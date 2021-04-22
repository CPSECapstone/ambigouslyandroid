package edu.calpoly.flipted.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.calpoly.flipted.backend.MockTasksRepo
import edu.calpoly.flipted.backend.ApolloTasksRepo
import edu.calpoly.flipted.businesslogic.quizzes.data.StudentAnswerInput
import edu.calpoly.flipted.businesslogic.tasks.GetTask
import edu.calpoly.flipted.businesslogic.tasks.SaveTaskProgress
import edu.calpoly.flipted.businesslogic.tasks.SubmitTask
import edu.calpoly.flipted.businesslogic.tasks.data.*
import kotlinx.coroutines.launch


class TaskViewModel : ViewModel(){
    private val _currTask : MutableLiveData<Task> = MutableLiveData()
    private val _currResponse : MutableLiveData<TaskSubmissionResult> = MutableLiveData()

    private val mockRepo = MockTasksRepo()
    private val repo = ApolloTasksRepo()
    private val getTaskUseCase = GetTask(mockRepo)
    private val submitTaskUseCase = SubmitTask(mockRepo)
    private val saveTaskProgressUseCase = SaveTaskProgress(mockRepo)

    val currTask : LiveData<Task>
        get() = _currTask

    val currResponse : LiveData<TaskSubmissionResult>
        get() = _currResponse

    fun fetchTask(taskId : String) {
        viewModelScope.launch {
            _currTask.value = getTaskUseCase.execute(taskId)
        }
    }

    private val requirements : MutableMap<String, RubricRequirement> = mutableMapOf()

    fun saveRubricRequirement(requirement: RubricRequirement) {
        val task = currTask.value ?: throw IllegalStateException("No task")
        requirements[requirement.uid] = requirement

        val requirementProgress = TaskRubricProgress(requirements.values.filter{it.isComplete}.toList(), task)
        viewModelScope.launch {
            saveTaskProgressUseCase.saveRubricProgress(requirementProgress)
        }
    }
    private val questionAnswers = mutableMapOf<Int, StudentAnswerInput>()

    fun saveQuizAnswer(answer: StudentAnswerInput) {
        val task = currTask.value ?: throw IllegalStateException("No task")

        questionAnswers[answer.questionId] = answer

        val answerProgress = TaskQuizAnswer(answer, task)

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