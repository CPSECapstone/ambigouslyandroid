package edu.calpoly.flipted.ui.quizzes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.calpoly.flipted.backend.ApolloQuestionsRepo
import edu.calpoly.flipted.businesslogic.mc_question.GetAllQuestions
import edu.calpoly.flipted.businesslogic.mc_question.McQuestion
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {
    val quizData : LiveData<List<McQuestion>>
        get() = _quizData

    private val repo = ApolloQuestionsRepo()
    private val _quizData = MutableLiveData<List<McQuestion>>()
    private val getAllQuestions = GetAllQuestions(repo)

    fun fetchQuestions(id : String?) {
        viewModelScope.launch {
            if (id == null) {
                Log.e("QuizViewModel", "Error: task id is null")
                //TODO: We need better error handling
                return@launch
            }
            _quizData.value = getAllQuestions.execute(id)
        }
    }
}