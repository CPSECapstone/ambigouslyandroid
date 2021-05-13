package edu.calpoly.flipted.ui.myProgress

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
    private val _allProgress : MutableLiveData<List<TargetProgress>> = MutableLiveData()
    private val _selectedLearningTargets : MutableLiveData<Set<LearningTarget>> = MutableLiveData()

    private val repo = MockLearningTargetsRepo()
    private val getAllTargetProgressUseCase = GetAllTargetProgress(repo)

    val selectedLearningTargets: LiveData<Set<LearningTarget>>
        get() = _selectedLearningTargets

    val allProgress: LiveData<List<TargetProgress>>
        get() = _allProgress

    fun fetchAllTargetProgress() {
        viewModelScope.launch {
            val progress = getAllTargetProgressUseCase.execute()
            _allProgress.value = progress

            val targets = mutableMapOf<LearningTarget, Boolean>()
            _selectedLearningTargets.value = setOf()
        }
    }

    fun clearSelection() {
        _selectedLearningTargets.value = setOf()
    }


    fun toggleSelectTarget(target: LearningTarget) {
        val currSelectedTargets = _selectedLearningTargets.value ?: setOf()
        _selectedLearningTargets.value = if(currSelectedTargets.contains(target))
            currSelectedTargets - target
        else
            currSelectedTargets + target
    }

}