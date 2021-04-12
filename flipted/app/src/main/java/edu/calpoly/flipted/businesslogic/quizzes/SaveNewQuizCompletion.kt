package edu.calpoly.flipted.businesslogic.quizzes

class SaveNewQuizCompletion(
    private val repo: QuestionsRepo
) {
    suspend fun execute(quiz : Quiz) : QuizResult {
        val validationResponses = ValidateQuizInput.execute(quiz.questions)
        if(validationResponses.find { it.type == ValidationResponseType.FAIL } != null) {
            throw IllegalArgumentException("Quiz failed validation")
        }
        return repo.saveCompletionForQuiz(quiz)
    }
}