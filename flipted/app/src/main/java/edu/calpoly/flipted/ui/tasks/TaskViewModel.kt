package edu.calpoly.flipted.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.calpoly.flipted.backend.MockTasksRepo
import edu.calpoly.flipted.businesslogic.tasks.GetTask
import edu.calpoly.flipted.businesslogic.tasks.SubmitTask
import edu.calpoly.flipted.businesslogic.tasks.data.Task
import kotlinx.coroutines.launch


class TaskViewModel : ViewModel(){
    private val _currTask : MutableLiveData<Task> = MutableLiveData()

    private val mockRepo = MockTasksRepo()
    private val getTaskUseCase = GetTask(mockRepo)
    private val submitTaskUseCase = SubmitTask(mockRepo)

    val currTask : LiveData<Task>
        get() = _currTask

    fun fetchTask(taskId : Int) {
        viewModelScope.launch {
            _currTask.value = getTaskUseCase.execute(taskId)
        }
    }

    fun submitQuestion() {
        viewModelScope.launch {

        }
    }

    fun submitTask(taskId : String) {
        viewModelScope.launch {
            submitTaskUseCase.execute(taskId)
        }
    }
}