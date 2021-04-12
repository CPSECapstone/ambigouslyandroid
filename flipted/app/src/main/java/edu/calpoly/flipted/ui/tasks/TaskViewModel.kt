package edu.calpoly.flipted.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.calpoly.flipted.businesslogic.tasks.Block
import edu.calpoly.flipted.businesslogic.tasks.FreeResponseQuestion
import edu.calpoly.flipted.businesslogic.tasks.Question
import edu.calpoly.flipted.businesslogic.tasks.QuizBlock
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel(){

    private val _blocks : MutableLiveData<List<Block>> = MutableLiveData()
    val blocks : LiveData<List<Block>>
        get() = _blocks

    fun fetchBlocks() {
        //viewModelScope.launch {
            _blocks.value = listOf<Block>(QuizBlock(null, listOf<Question>(
                FreeResponseQuestion("What is your name?", 1, 1)), 0, 1))
        //}
    }
}