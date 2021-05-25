package edu.calpoly.flipted.ui.missions

import androidx.lifecycle.*
import edu.calpoly.flipted.backend.ApolloMissionsRepo
import edu.calpoly.flipted.backend.ApolloTasksRepo
import edu.calpoly.flipted.backend.MockMissionsRepo
import edu.calpoly.flipted.businesslogic.missions.GetAllMissionProgress
import edu.calpoly.flipted.businesslogic.missions.TaskStats
import edu.calpoly.flipted.businesslogic.targets.TaskObjectiveProgress
import edu.calpoly.flipted.businesslogic.tasks.GetObjectiveProgress
import edu.calpoly.flipted.businesslogic.tasks.GetTaskInfo
import edu.calpoly.flipted.businesslogic.tasks.data.Task
import kotlinx.coroutines.launch


class MissionsViewModel : ViewModel() {
    private val _currTaskInfo: MutableLiveData<TaskStats> = MutableLiveData()
    private val _taskObjectives: MutableLiveData<List<TaskObjectiveProgress>> = MutableLiveData()

    private val repo = MockMissionsRepo()
    private val tasksRepo = ApolloTasksRepo()
    private val getProgress = GetAllMissionProgress(repo)
    private val getTaskInfoUseCase = GetTaskInfo(tasksRepo)
    private val getObjectiveProgressUseCase = GetObjectiveProgress(tasksRepo)


    val currTaskInfo: LiveData<TaskStats?>
        get() = _currTaskInfo

    val taskObjectives: LiveData<List<TaskObjectiveProgress>>
        get() = _taskObjectives

    fun fetchTaskInfo(taskId: String) {
        viewModelScope.launch {
            val target = getProgress.execute("Integrated Science")
            //"da0719ba103"
            val currMission = target.filter{it.mission.uid == "m1"}.toList()[0]
            val task = currMission.progress.filter {it.task.id == "11"}.toList()[0]
            //val objectives = getObjectiveProgressUseCase.execute(taskId)

            _currTaskInfo.value = task
            _taskObjectives.value = listOf() //objectives

        }
    }
}