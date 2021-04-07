package edu.calpoly.flipted.businesslogic.quizzes


enum class ValidationResponseType {
    WARN,
    FAIL
}

data class ValidationResponse(
        val type : ValidationResponseType,
        val message : String = "",
        val subject : Question? = null,
        val subResponses : List<ValidationResponse>? = null
)

class ValidateQuizInput {
    fun execute(filledInQuestions : List<Question>,
                ignoreWarnings : List<ValidationResponse> = listOf())
    : List<ValidationResponse> {
        val warnings = mutableListOf<ValidationResponse>()

        if(filledInQuestions.isEmpty())
            return listOf(ValidationResponse(ValidationResponseType.FAIL, "Empty quiz"))

        filledInQuestions.forEach { question ->
            val (selectedAnswers, unselectedAnswers) = question.answers.partition { it.isChecked }

            //FIXME: We only currently support multiple-choice questions with one selected answer.
            //      Once we support multi-select questions, this assumption becomes wrong.
            if(selectedAnswers.count() > 1)
                return listOf(ValidationResponse(ValidationResponseType.FAIL, "Too many answers", question))
            if(selectedAnswers.count() < 1) {
                val response = ValidationResponse(ValidationResponseType.WARN, "No answer selected", question)
                if (!ignoreWarnings.contains(response))
                    warnings.add(response)
            }
        }
        if(warnings.isNotEmpty())
            return warnings

        return listOf()
    }
}