package edu.calpoly.flipted.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.calpoly.flipted.backend.MockTasksRepo
import edu.calpoly.flipted.backend.ApolloTasksRepo
import edu.calpoly.flipted.businesslogic.tasks.GetTask
import edu.calpoly.flipted.businesslogic.tasks.SubmitTaskProgress
import edu.calpoly.flipted.businesslogic.tasks.data.Task
import edu.calpoly.flipted.businesslogic.tasks.data.TaskProgress
import kotlinx.coroutines.launch


class TaskViewModel : ViewModel(){
    private val _currTask : MutableLiveData<Task> = MutableLiveData()

    private val mockRepo = MockTasksRepo()
    private val repo = ApolloTasksRepo()
    private val getTaskUseCase = GetTask(mockRepo)
    private val submitTaskProgress = SubmitTaskProgress(repo)

    val currTask : LiveData<Task>
        get() = _currTask

    fun fetchTask(taskId : Int) {
        viewModelScope.launch {
            _currTask.value = getTaskUseCase.execute(taskId)
        }
    }

    fun submitProgress(taskProgress: TaskProgress) {
        viewModelScope.launch {
            submitTaskProgress.execute(taskProgress)
        }
    }
}