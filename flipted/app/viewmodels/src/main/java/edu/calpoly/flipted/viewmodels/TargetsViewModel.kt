package edu.calpoly.flipted.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.calpoly.flipted.businesslogic.targets.GetAllTargetProgress
import edu.calpoly.flipted.businesslogic.targets.LearningTargetsRepo
import edu.calpoly.flipted.businesslogic.targets.TargetProgress
import kotlinx.coroutines.launch

class TargetsViewModel(repo: LearningTargetsRepo) : ViewModel() {
    private val _allProgress : MutableLiveData<Map<String, TargetProgress>> = MutableLiveData()

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