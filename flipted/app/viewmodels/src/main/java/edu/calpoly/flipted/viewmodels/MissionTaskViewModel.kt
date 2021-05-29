package edu.calpoly.flipted.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.calpoly.flipted.businesslogic.missions.GetAllMissionProgress
import edu.calpoly.flipted.businesslogic.missions.MissionProgress
import edu.calpoly.flipted.businesslogic.missions.MissionsRepo
import kotlinx.coroutines.launch

class MissionTaskViewModel(repo: MissionsRepo) : ViewModel() {

    private val _allMissions: MutableLiveData<Map<String, MissionProgress>> = MutableLiveData()

    val allMissions: LiveData<Map<String, MissionProgress>>
        get() = _allMissions

    private val getProgress = GetAllMissionProgress(repo)


    fun fetchTaskStats() {
        viewModelScope.launch {
            val target = getProgress.execute("Integrated Science")
            _allMissions.value =  target.associateBy { it.mission.uid }

        }
    }

}