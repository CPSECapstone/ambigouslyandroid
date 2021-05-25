package edu.calpoly.flipted.ui.myProgress.missions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.calpoly.flipted.backend.ApolloMissionsRepo
import edu.calpoly.flipted.backend.MockMissionsRepo
import edu.calpoly.flipted.businesslogic.missions.GetAllMissionProgress
import edu.calpoly.flipted.businesslogic.missions.MissionProgress
import kotlinx.coroutines.launch

class MissionsViewModel : ViewModel() {
    private val _missionsProgress: MutableLiveData<Map<String, MissionProgress>> = MutableLiveData()

    val missionsProgress: LiveData<Map<String, MissionProgress>>
        get() = _missionsProgress

    private val repo = ApolloMissionsRepo()
    private val getProgress = GetAllMissionProgress(repo)

    fun fetchMissionsProgress() {
        viewModelScope.launch {
            _missionsProgress.value = getProgress
                .execute("Integrated Science")
                .associateBy{ it.mission.uid }
        }
    }
}