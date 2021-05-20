package edu.calpoly.flipted.ui.myProgress.targets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.calpoly.flipted.backend.MockLearningTargetsRepo
import edu.calpoly.flipted.businesslogic.targets.GetAllTargetProgress
import edu.calpoly.flipted.businesslogic.targets.LearningTarget
import edu.calpoly.flipted.businesslogic.targets.TargetProgress
import kotlinx.coroutines.launch

class TargetsViewModel : ViewModel() {
    private val _allProgress : MutableLiveData<Map<String, TargetProgress>> = MutableLiveData()

    private val repo = MockLearningTargetsRepo()
    private val getAllTargetProgressUseCase = GetAllTargetProgress(repo)

    val allProgress: LiveData<Map<String, TargetProgress>>
        get() = _allProgress

    fun fetchAllTargetProgress() {
        viewModelScope.launch {
            val progress = getAllTargetProgressUseCase.execute()
            _allProgress.value = progress.associateBy {it.target.uid}
        }
    }

}