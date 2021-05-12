package edu.calpoly.flipted.ui.myProgress

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.calpoly.flipted.backend.ApolloTasksRepo
import edu.calpoly.flipted.backend.MockLearningTargetRepo
import edu.calpoly.flipted.businesslogic.learningTargets.GetAllTargetProgress
import edu.calpoly.flipted.businesslogic.tasks.GetTask
import edu.calpoly.flipted.businesslogic.tasks.SaveTaskProgress
import edu.calpoly.flipted.businesslogic.tasks.SubmitTask

class TargetsViewModel : ViewModel() {
    private val _targetList : MutableLiveData<List<String>> = MutableLiveData()
    private val _selectedTargetList : MutableLiveData<List<String>> = MutableLiveData()

    private val repo = MockLearningTargetRepo()
    private val getAllTargetProgressUseCase = GetAllTargetProgress(repo)



}