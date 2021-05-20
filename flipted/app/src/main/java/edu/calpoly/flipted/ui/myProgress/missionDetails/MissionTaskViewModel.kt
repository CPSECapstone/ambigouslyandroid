package edu.calpoly.flipted.ui.myProgress.missionDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.calpoly.flipted.backend.ApolloMissionsRepo
import edu.calpoly.flipted.backend.MockMissionsRepo
import edu.calpoly.flipted.businesslogic.missions.GetAllMissionProgress
import edu.calpoly.flipted.businesslogic.missions.MissionProgress


import kotlinx.coroutines.launch

open class MissionTaskViewModel : ViewModel() {

    private val _allMissions: MutableLiveData<Map<String, MissionProgress>> = MutableLiveData()

    val allMissions: LiveData<Map<String, MissionProgress>>
        get() = _allMissions

    private val repo = ApolloMissionsRepo()
    private val getProgress = GetAllMissionProgress(repo)


    fun fetchTaskStats() {
        viewModelScope.launch {
            val target = getProgress.execute("Integrated Science")
            _allMissions.value =  target.associateBy { it.mission.uid }

        }
    }

}