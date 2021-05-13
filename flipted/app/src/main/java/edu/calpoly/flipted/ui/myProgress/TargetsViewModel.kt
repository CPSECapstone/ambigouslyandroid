package edu.calpoly.flipted.ui.myProgress

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.calpoly.flipted.backend.ApolloLearningTargetRepo
import edu.calpoly.flipted.backend.ApolloTasksRepo
import edu.calpoly.flipted.backend.MockLearningTargetRepo
import edu.calpoly.flipted.businesslogic.learningTargets.GetAllTargetProgress
import edu.calpoly.flipted.businesslogic.learningTargets.LearningTarget
import edu.calpoly.flipted.businesslogic.learningTargets.TargetProgress
import edu.calpoly.flipted.businesslogic.tasks.GetTask
import edu.calpoly.flipted.businesslogic.tasks.SaveTaskProgress
import edu.calpoly.flipted.businesslogic.tasks.SubmitTask
import edu.calpoly.flipted.businesslogic.tasks.data.RubricRequirement
import edu.calpoly.flipted.businesslogic.tasks.data.Task
import kotlinx.coroutines.launch

class TargetsViewModel : ViewModel() {
    private val _allProgress : MutableLiveData<List<TargetProgress>> = MutableLiveData()
    private val _targetMap : MutableLiveData<MutableMap<LearningTarget, Boolean>> = MutableLiveData()
    private val _selectedTargetList : MutableLiveData<MutableList<LearningTarget>> = MutableLiveData()

    private val repo = MockLearningTargetRepo()
    private val getAllTargetProgressUseCase = GetAllTargetProgress(repo)

    private val _allSelected : MutableLiveData<Boolean> = MutableLiveData(true)

    val selectedTargetList: LiveData<MutableList<LearningTarget>?>
        get() = _selectedTargetList

    val targetMap: LiveData<MutableMap<LearningTarget, Boolean>?>
        get() = _targetMap

    val allSelected: LiveData<Boolean>
        get() = _allSelected

    val allProgress: LiveData<List<TargetProgress>>
        get() = _allProgress

    fun fetchAllTargetProgress() {
        viewModelScope.launch {
            val progress = getAllTargetProgressUseCase.execute()
            _allProgress.value = progress

            val targets = mutableMapOf<LearningTarget, Boolean>()

            val currTargets = progress.map{
                it.target
            }

            targets.putAll(currTargets.associateBy({it}, {false}))

            _targetMap.value = targets

            _selectedTargetList.value = currTargets.toMutableList()
        }
    }

    fun toggleAllTargets() {
        var isSelected = allSelected.value ?: throw IllegalStateException("No selection assignment")
        var targets = _selectedTargetList.value ?: throw IllegalStateException("No targets")
        var targetsMap = _targetMap.value ?: throw IllegalStateException("No targets Map")

        if (isSelected.not()) {
            // Reselect all targets
            targets = targetsMap.keys.toMutableList()
            targetsMap = targetsMap.mapValues{false}.toMutableMap()
            isSelected = isSelected.not()
            _targetMap.value = targetsMap
            _selectedTargetList.value = targets
            _allSelected.value = isSelected
        }
    }


    fun updateSelectedTargets(targetId: String) {
        val isAllSelected = allSelected.value ?: throw IllegalStateException("No selection assignment")
        var targets = _selectedTargetList.value ?: throw IllegalStateException("No targets")
        val targetsMap = _targetMap.value ?: throw IllegalStateException("No targets Map")
        val currChecked = targetsMap.filterKeys { it.uid.compareTo(targetId) == 0}.toList()[0]
        val key = currChecked.first
        val isChecked = currChecked.second


        if (isAllSelected) {
            _allSelected.value = isAllSelected.not()
            targets = mutableListOf(key)
            targetsMap.put(key, true)
        }
        else {
            if (isChecked) {
                targets.remove(key)
                if (targets.size == 0) {
                    toggleAllTargets()
                    targets = _selectedTargetList.value!!
                }
            }
            else {
                targets.add(key)
            }
            targetsMap.put(key, isChecked.not())
        }
        _selectedTargetList.value = targets
    }

}