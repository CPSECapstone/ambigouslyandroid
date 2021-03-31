package edu.calpoly.flipted.ui.quizzes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flipteded.businesslogic.mc_question.GetAllQuestions
import com.example.flipteded.businesslogic.mc_question.Question
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {
    val quizData : LiveData<List<Question>>
        get() = _quizData

    private val _quizData = MutableLiveData<List<Question>>()
    private val getAllQuestions = GetAllQuestions()

    fun fetchQuestions() {
        viewModelScope.launch {
            _quizData.value = getAllQuestions.getAllQuestions()
        }
    }
}