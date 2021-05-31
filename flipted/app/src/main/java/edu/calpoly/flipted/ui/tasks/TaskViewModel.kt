package edu.calpoly.flipted.ui.tasks

import androidx.lifecycle.*
import edu.calpoly.flipted.backend.ApolloTasksRepo
import edu.calpoly.flipted.businesslogic.quizzes.data.StudentAnswerInput
import edu.calpoly.flipted.businesslogic.quizzes.data.questions.Question
import edu.calpoly.flipted.businesslogic.targets.TaskObjectiveProgress
import edu.calpoly.flipted.businesslogic.tasks.*
import edu.calpoly.flipted.businesslogic.tasks.data.*
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.QuizBlock
import kotlinx.coroutines.launch


class TaskViewModel : ViewModel() {
    private val _currTask: MutableLiveData<Task> = MutableLiveData()
    private val _currResponse: MutableLiveData<TaskSubmissionResult> = MutableLiveData()
    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    private val _taskObjectives: MutableLiveData<List<TaskObjectiveProgress>> = MutableLiveData()
    val taskAndResponseValid: MediatorLiveData<Boolean> = MediatorLiveData()

    private val repo = ApolloTasksRepo()
    private val getTaskUseCase = GetTask(repo)
    private val submitTaskUseCase = SubmitTask(repo)
    private val saveTaskProgressUseCase = SaveTaskProgress(repo)
    private val getObjectiveProgressUseCase = GetObjectiveProgress(repo)
    private val retrieveTaskSubmissionUseCase = RetrieveTaskSubmission(repo)


    val currTask: LiveData<Task?>
        get() = _currTask

    val currResponse: LiveData<TaskSubmissionResult?>
        get() = _currResponse

    val errorMessage: LiveData<String>
        get() = _errorMessage

    val taskObjectives: LiveData<List<TaskObjectiveProgress>>
        get() = _taskObjectives

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
                _errorMessage.value = e.message
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
                _errorMessage.value = e.message
            }
        }
    }


    fun saveQuizAnswer(answer: StudentAnswerInput, block: QuizBlock) {
        val task = currTask.value ?: throw IllegalStateException("No task")

        val answerProgress = TaskQuizAnswer(answer, task, block)

        viewModelScope.launch {
            try {
                saveTaskProgressUseCase.saveQuizAnswer(answerProgress)
                questionAnswers.value = (questionAnswers.value
                        ?: mapOf()) + Pair(answer.questionId, answer)
            } catch (e: RuntimeException) {
                _errorMessage.value = e.message
            }
        }
    }


    fun submitTask(taskId: String) {
        viewModelScope.launch {
            try {
                _errorMessage.value = ""
                _currResponse.value = submitTaskUseCase.execute(taskId)
                _taskObjectives.value = getObjectiveProgressUseCase.execute(taskId)
            } catch (e: RuntimeException) {
                _errorMessage.value = e.message
                _currResponse.value = TaskSubmissionResult(taskId, false, 0, 0, "",
                        listOf())
                _taskObjectives.value = listOf()
            }
        }
    }

    fun retrieveTaskSubmission(taskId: String) {
        viewModelScope.launch {
            _currTask.value = getTaskUseCase.execute(taskId)
            _currResponse.value = retrieveTaskSubmissionUseCase.execute(taskId)

            taskAndResponseValid.removeSource(_currResponse)
            taskAndResponseValid.removeSource(_currTask)
            taskAndResponseValid.addSource(_currResponse) {
                val currResponse = _currResponse.value
                val currTask = _currTask.value
                taskAndResponseValid.value = if (currResponse != null && currTask != null) {
                    (currResponse.taskId == currTask.uid) && (currResponse.taskId == taskId) && (currTask.uid == taskId)
                }
                else {
                    false
                }

            }

            taskAndResponseValid.addSource(_currTask) {
                val currResponse = _currResponse.value
                val currTask = _currTask.value
                taskAndResponseValid.value = if (currResponse != null && currTask != null) {
                    (currResponse.taskId == currTask.uid) && (currResponse.taskId == taskId) && (currTask.uid == taskId)
                }
                else {
                    false
                }
            }
        }
    }

    fun setTaskObjectives(objectives: List<TaskObjectiveProgress>) {
        _taskObjectives.value = objectives
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