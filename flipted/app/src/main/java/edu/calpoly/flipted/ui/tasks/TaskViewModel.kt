package edu.calpoly.flipted.ui.tasks

import androidx.lifecycle.*
import edu.calpoly.flipted.backend.ApolloTasksRepo
import edu.calpoly.flipted.businesslogic.errors.BackendError
import edu.calpoly.flipted.businesslogic.quizzes.data.StudentAnswerInput
import edu.calpoly.flipted.businesslogic.quizzes.data.questions.Question
import edu.calpoly.flipted.businesslogic.tasks.GetTask
import edu.calpoly.flipted.businesslogic.tasks.SaveTaskProgress
import edu.calpoly.flipted.businesslogic.tasks.SubmitTask
import edu.calpoly.flipted.businesslogic.tasks.data.*
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.QuizBlock
import kotlinx.coroutines.launch


class TaskViewModel : ViewModel() {
    private val _currTask: MutableLiveData<Task> = MutableLiveData()
    private val _currResponse: MutableLiveData<TaskSubmissionResult> = MutableLiveData()

    private val _lastError: MutableLiveData<BackendError?> = MutableLiveData()

    private val repo = ApolloTasksRepo()
    private val getTaskUseCase = GetTask(repo)
    private val submitTaskUseCase = SubmitTask(repo)
    private val saveTaskProgressUseCase = SaveTaskProgress(repo)


    val currTask: LiveData<Task?>
        get() = _currTask

    val currResponse: LiveData<TaskSubmissionResult?>
        get() = _currResponse

    val lastError: LiveData<BackendError?>
        get() = _lastError


    fun clearTask() {
        _currTask.value = null
        _currResponse.value = null
    }

    private val requirements: MutableLiveData<Map<String, RubricRequirement>> = MutableLiveData()
    private val questionAnswers = MutableLiveData<Map<String, StudentAnswerInput>>()
    private var allQuestions: List<Question> = listOf()

    fun fetchTask(taskId: String) {
        viewModelScope.launch {
            val task = try {
                getTaskUseCase.execute(taskId)
            } catch (e: RuntimeException) {
                _lastError.value = BackendError(e.message?: "Unknown error")
                return@launch
            }
            _currTask.value = task

            requirements.value = task.requirements.associateBy { it.uid }
            allQuestions = task.pages
                    .flatMap { it.blocks }
                    .filterIsInstance<QuizBlock>()
                    .flatMap { it.questions }
            questionAnswers.value = allQuestions
                    .mapNotNull { question ->
                        question.savedAnswer?.let { StudentAnswerInput(question.uid, it) }
                    }
                    .associateBy { it.questionId }
        }
    }

    fun saveRubricRequirement(requirement: RubricRequirement) {
        val task = currTask.value ?: throw IllegalStateException("No task")

        val updatedRequirementsMap = (requirements.value
                ?: mapOf()) + Pair(requirement.uid, requirement)

        val requirementProgress = TaskRubricProgress(updatedRequirementsMap.values.filter { it.isComplete }.toList(), task)
        viewModelScope.launch {
            try {
                saveTaskProgressUseCase.saveRubricProgress(requirementProgress)
                requirements.value = updatedRequirementsMap
            } catch (e: RuntimeException) {
                _lastError.value = BackendError(e.message ?: "Unknown error")
            }
        }
    }


    fun saveQuizAnswer(answer: StudentAnswerInput, block: QuizBlock) {
        val task = currTask.value ?: throw IllegalStateException("No task")

        val answerProgress = QuizBlockStudentAnswerInput(answer, task, block)

        viewModelScope.launch {
            val response = saveTaskProgressUseCase.saveQuizAnswer(answerProgress)
            if(response.isError)
                _lastError.value = response.error
            questionAnswers.value = (questionAnswers.value
                    ?: mapOf()) + Pair(answer.questionId, answer)

        }
    }


    fun submitTask(taskId: String) {
        viewModelScope.launch {
            try {
                _lastError.value = null
                _currResponse.value = submitTaskUseCase.execute(taskId)
            } catch (e: RuntimeException) {
                _lastError.value = BackendError(e.message ?: "Unknown error")
                _currResponse.value = TaskSubmissionResult(taskId, false, 0, 0,
                        listOf())
            }
        }
    }


    private val allRequirementsComplete = Transformations.map(requirements) { requirements ->
        requirements.values.fold(true) { acc, requirement ->
            acc && requirement.isComplete
        }
    }
    private val allQuestionsAnswered = Transformations.map(questionAnswers) { answers ->
        allQuestions.fold(true) { acc, question ->
            acc && answers.containsKey(question.uid)
        }
    }

    private val _eligibleForSubmission = MediatorLiveData<Boolean>()

    init {
        _eligibleForSubmission.addSource(allRequirementsComplete) {
            _eligibleForSubmission.value = (allQuestionsAnswered.value ?: false) && it
        }
        _eligibleForSubmission.addSource(allQuestionsAnswered) {
            _eligibleForSubmission.value = (allRequirementsComplete.value ?: false) && it
        }
    }

    val eligibleForSubmission: LiveData<Boolean>
        get() = _eligibleForSubmission

}