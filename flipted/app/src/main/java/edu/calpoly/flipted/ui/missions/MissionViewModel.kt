package edu.calpoly.flipted.ui.missions

import androidx.lifecycle.*
import edu.calpoly.flipted.backend.ApolloTasksRepo
import edu.calpoly.flipted.businesslogic.targets.TaskObjectiveProgress
import edu.calpoly.flipted.businesslogic.tasks.GetObjectiveProgress
import edu.calpoly.flipted.businesslogic.tasks.GetTaskInfo
import edu.calpoly.flipted.businesslogic.tasks.data.Task
import kotlinx.coroutines.launch


class MissionsViewModel : ViewModel() {
    private val _currTaskInfo: MutableLiveData<Task> = MutableLiveData()
    private val _taskObjectives: MutableLiveData<List<TaskObjectiveProgress>> = MutableLiveData()

    private val repo = ApolloTasksRepo()
    private val getTaskInfoUseCase = GetTaskInfo(repo)
    private val getObjectiveProgressUseCase = GetObjectiveProgress(repo)


    val currTaskInfo: LiveData<Task?>
        get() = _currTaskInfo

    val taskObjectives: LiveData<List<TaskObjectiveProgress>>
        get() = _taskObjectives

    fun fetchTaskInfo(taskId: String) {
        viewModelScope.launch {
            val task = getTaskInfoUseCase.execute(taskId)

            _currTaskInfo.value = task
            _taskObjectives.value = getObjectiveProgressUseCase.execute(taskId)
        }
    }
}