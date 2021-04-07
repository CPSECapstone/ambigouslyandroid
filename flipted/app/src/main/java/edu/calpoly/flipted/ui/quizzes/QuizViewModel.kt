package edu.calpoly.flipted.ui.quizzes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.calpoly.flipted.backend.MockQuestionsRepo
import edu.calpoly.flipted.businesslogic.quizzes.GetQuiz
import edu.calpoly.flipted.businesslogic.quizzes.Question
import edu.calpoly.flipted.businesslogic.quizzes.Quiz
import edu.calpoly.flipted.businesslogic.quizzes.SaveNewQuizCompletion
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {
    val quizData : LiveData<Quiz>
        get() = _quizData

    private val repo = MockQuestionsRepo()
    private val _quizData = MutableLiveData<Quiz>()
    private val getAllQuestions = GetQuiz(repo)
    private val saveQuizCompletion = SaveNewQuizCompletion(repo)

    fun fetchQuestions(id : Int) {
        viewModelScope.launch {
            _quizData.value = getAllQuestions.execute(id)
        }
    }

    fun saveQuizCompletion(quiz : Quiz) {
        viewModelScope.launch {
            saveQuizCompletion.execute(quiz)
        }
    }
}