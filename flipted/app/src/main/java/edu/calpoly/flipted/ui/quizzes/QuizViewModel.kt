package edu.calpoly.flipted.ui.quizzes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.calpoly.flipted.backend.MockQuestionsRepo
import edu.calpoly.flipted.businesslogic.quizzes.*
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {
    val quizData : LiveData<Quiz>
        get() = _quizData

    val quizResult : LiveData<QuizResult>
        get() = _quizResult

    private val _quizData = MutableLiveData<Quiz>()
    private val _quizResult = MutableLiveData<QuizResult>()

    private val repo = MockQuestionsRepo()
    private val getAllQuestions = GetQuiz(repo)
    private val getQuizResult = GetQuizResult(repo)
    private val saveQuizCompletion = SaveNewQuizCompletion(repo)

    fun fetchQuestions(id : Int) {
        viewModelScope.launch {
            _quizData.value = getAllQuestions.execute(id)
        }
    }

    fun fetchResult(resultId : Int) {
        viewModelScope.launch {
            _quizResult.value = getQuizResult.execute(resultId)
        }
    }


    fun submitQuizForGrading(quiz : Quiz) {
        viewModelScope.launch {
            _quizResult.value = saveQuizCompletion.execute(quiz)
        }
    }
}