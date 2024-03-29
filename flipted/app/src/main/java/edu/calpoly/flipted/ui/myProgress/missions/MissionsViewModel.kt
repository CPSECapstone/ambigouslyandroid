package edu.calpoly.flipted.ui.myProgress.missions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.calpoly.flipted.businesslogic.missions.GetAllMissionProgress
import edu.calpoly.flipted.businesslogic.missions.MissionProgress
import edu.calpoly.flipted.businesslogic.missions.MissionsRepo
import edu.calpoly.flipted.businesslogic.missions.TaskStats
import edu.calpoly.flipted.businesslogic.targets.TaskObjectiveProgress
import edu.calpoly.flipted.businesslogic.tasks.GetObjectiveProgress
import edu.calpoly.flipted.businesslogic.tasks.TasksRepo
import kotlinx.coroutines.launch
import java.lang.RuntimeException


class MissionsViewModel(missionsRepo: MissionsRepo, tasksRepo: TasksRepo) : ViewModel() {
    private val _missionsProgress: MutableLiveData<Map<String, MissionProgress>> = MutableLiveData()
    private val _currMissionId: MutableLiveData<String> = MutableLiveData()
    private val _currTaskInfo: MutableLiveData<TaskStats> = MutableLiveData()
    private val _taskObjectives: MutableLiveData<List<TaskObjectiveProgress>> = MutableLiveData()
    private val _lastError: MutableLiveData<RuntimeException?> = MutableLiveData()

    private val getObjectiveProgressUseCase = GetObjectiveProgress(tasksRepo)


    val missionsProgress: LiveData<Map<String, MissionProgress>>
        get() = _missionsProgress

    val currTaskInfo: LiveData<TaskStats>
        get() = _currTaskInfo

    val taskObjectives: LiveData<List<TaskObjectiveProgress>>
        get() = _taskObjectives

    val lastError: LiveData<RuntimeException?>
        get() = _lastError

    private val getProgress = GetAllMissionProgress(missionsRepo)

    fun fetchMissionsProgress() {
        viewModelScope.launch {
            try {
                _missionsProgress.value = getProgress
                    .execute("Integrated Science")
                    .associateBy { it.mission.uid }
            } catch(e: RuntimeException) {
                _lastError.value = e
            }
        }
    }

    fun fetchTaskInfo(taskId: String) {
        viewModelScope.launch {
            val missions = _missionsProgress.value ?: throw IllegalStateException("No missionsProgress value")
            val currMission = missions[_currMissionId.value] ?: throw IllegalStateException("No mission found")
            val task = currMission.progress.first{it.task.id == taskId}
            val objectives = try {
                 getObjectiveProgressUseCase.execute(taskId)
            } catch(e: RuntimeException) {
                _lastError.value = e
                return@launch
            }
            _currTaskInfo.value = task
            _taskObjectives.value = objectives

        }
    }

    fun setCurrMissionId(missionId: String) {
        _currMissionId.value = missionId
    }
}