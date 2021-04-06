package edu.calpoly.flipted.ui.quizzes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.calpoly.flipted.backend.ApolloQuestionsRepo
import edu.calpoly.flipted.businesslogic.quizzes.GetAllQuestions
import edu.calpoly.flipted.businesslogic.quizzes.Question
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {
    val quizData : LiveData<List<Question>>
        get() = _quizData

    private val repo = ApolloQuestionsRepo()
    private val _quizData = MutableLiveData<List<Question>>()
    private val getAllQuestions = GetAllQuestions(repo)

    fun fetchQuestions(id : Int) {
        viewModelScope.launch {
            _quizData.value = getAllQuestions.execute(id)
        }
    }
}