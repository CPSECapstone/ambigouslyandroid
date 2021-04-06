package edu.calpoly.flipted.businesslogic.quizzes

import org.junit.Test
import org.junit.Assert.*

class TestValidateQuizInput {

    @Test
    fun `question with no answers selected`() {
        val validator = ValidateQuizInput()

        val successResponse = ValidationResponse(ValidationResponseType.SUCCESS)

        val quiz1 = listOf(
            Question("The center of an atom is called the ______.", 1, 1, mutableListOf(
                Answer("atomos", 1, isCorrect = false, isChecked = true),
                Answer("neutron", 1, isCorrect = false, isChecked = false),
                Answer("nucleus", 1, isCorrect = true, isChecked = false),
                Answer("nucleon", 1, isCorrect = false, isChecked = false))),
            Question("The basic unit of all organisms is ______.", 2, 2, mutableListOf(
                Answer("the nucleus", 1, isCorrect = false, isChecked = false),
                Answer("tissue", 1, isCorrect = false, isChecked = false),
                Answer("the cell", 1, isCorrect = true, isChecked = false),
                Answer("the organ", 1, isCorrect = false, isChecked = false),
                Answer("the atom", 1, isCorrect = false, isChecked = false))))
        val quiz1Responses = listOf(ValidationResponse(ValidationResponseType.WARN, "No answer selected", quiz1[1]))

        assertEquals(
                ValidationResponse(ValidationResponseType.GROUP, "", null, quiz1Responses),
                validator.execute(quiz1)
        )
        assertEquals(successResponse, validator.execute(quiz1, quiz1Responses))

        val quiz2 = listOf(
                Question("The center of an atom is called the ______.", 1, 1, mutableListOf(
                        Answer("atomos", 1, isCorrect = false, isChecked = false),
                        Answer("neutron", 1, isCorrect = false, isChecked = false),
                        Answer("nucleus", 1, isCorrect = true, isChecked = false),
                        Answer("nucleon", 1, isCorrect = false, isChecked = false))),
                Question("The basic unit of all organisms is ______.", 2, 2, mutableListOf(
                        Answer("the nucleus", 1, isCorrect = false, isChecked = false),
                        Answer("tissue", 1, isCorrect = false, isChecked = true),
                        Answer("the cell", 1, isCorrect = true, isChecked = false),
                        Answer("the organ", 1, isCorrect = false, isChecked = false),
                        Answer("the atom", 1, isCorrect = false, isChecked = false))))
        val quiz2Responses = listOf(ValidationResponse(ValidationResponseType.WARN, "No answer selected", quiz2[0]))

        assertEquals(successResponse, validator.execute(quiz2, quiz2Responses))
        assertEquals(
                ValidationResponse(ValidationResponseType.GROUP, "", null, quiz2Responses),
                validator.execute(quiz2)
        )

        val quiz3 = listOf(
                Question("The center of an atom is called the ______.", 1, 1, mutableListOf(
                        Answer("atomos", 1, isCorrect = false, isChecked = false),
                        Answer("neutron", 1, isCorrect = false, isChecked = false),
                        Answer("nucleus", 1, isCorrect = true, isChecked = false),
                        Answer("nucleon", 1, isCorrect = false, isChecked = false))),
                Question("The basic unit of all organisms is ______.", 2, 2, mutableListOf(
                        Answer("the nucleus", 1, isCorrect = false, isChecked = false),
                        Answer("tissue", 1, isCorrect = false, isChecked = false),
                        Answer("the cell", 1, isCorrect = true, isChecked = false),
                        Answer("the organ", 1, isCorrect = false, isChecked = false),
                        Answer("the atom", 1, isCorrect = false, isChecked = true))))
        val quiz3Responses = listOf(ValidationResponse(ValidationResponseType.WARN, "No answer selected", quiz3[0]))

        assertEquals(
                ValidationResponse(ValidationResponseType.GROUP, "", null, quiz3Responses),
                validator.execute(quiz3)
        )
        assertEquals(successResponse, validator.execute(quiz3, quiz3Responses))

        val quiz4 = listOf(
                Question("The center of an atom is called the ______.", 1, 1, mutableListOf(
                        Answer("atomos", 1, isCorrect = false, isChecked = false),
                        Answer("neutron", 1, isCorrect = false, isChecked = false),
                        Answer("nucleus", 1, isCorrect = true, isChecked = false),
                        Answer("nucleon", 1, isCorrect = false, isChecked = false))),
                Question("The basic unit of all organisms is ______.", 2, 2, mutableListOf(
                        Answer("the nucleus", 1, isCorrect = false, isChecked = false),
                        Answer("tissue", 1, isCorrect = false, isChecked = false),
                        Answer("the cell", 1, isCorrect = true, isChecked = false),
                        Answer("the organ", 1, isCorrect = false, isChecked = false),
                        Answer("the atom", 1, isCorrect = false, isChecked = false))))
        val quiz4Responses = listOf(
                ValidationResponse(ValidationResponseType.WARN, "No answer selected", quiz4[0]),
                ValidationResponse(ValidationResponseType.WARN, "No answer selected", quiz4[1]))

        assertEquals(
                ValidationResponse(ValidationResponseType.GROUP, "", null, quiz4Responses),
                validator.execute(quiz4)
        )
        assertEquals(successResponse, validator.execute(quiz4, quiz4Responses))
    }

    @Test
    fun `question with more than one answer selected`() {
        val validator = ValidateQuizInput()

        val quiz1 = listOf(
                Question("The center of an atom is called the ______.", 1, 1, mutableListOf(
                        Answer("atomos", 1, isCorrect = false, isChecked = true),
                        Answer("neutron", 1, isCorrect = false, isChecked = false),
                        Answer("nucleus", 1, isCorrect = true, isChecked = true),
                        Answer("nucleon", 1, isCorrect = false, isChecked = false))),
                Question("The basic unit of all organisms is ______.", 2, 2, mutableListOf(
                        Answer("the nucleus", 1, isCorrect = false, isChecked = true),
                        Answer("tissue", 1, isCorrect = false, isChecked = false),
                        Answer("the cell", 1, isCorrect = true, isChecked = false),
                        Answer("the organ", 1, isCorrect = false, isChecked = false),
                        Answer("the atom", 1, isCorrect = false, isChecked = false))))
        val quiz1Response = ValidationResponse(ValidationResponseType.FAIL, "Too many answers", quiz1[0])

        assertEquals(quiz1Response, validator.execute(quiz1))
        assertEquals(quiz1Response, validator.execute(quiz1, listOf(quiz1Response)))

        val quiz2 = listOf(
                Question("The center of an atom is called the ______.", 1, 1, mutableListOf(
                        Answer("atomos", 1, isCorrect = false, isChecked = true),
                        Answer("neutron", 1, isCorrect = false, isChecked = false),
                        Answer("nucleus", 1, isCorrect = true, isChecked = false),
                        Answer("nucleon", 1, isCorrect = false, isChecked = false))),
                Question("The basic unit of all organisms is ______.", 2, 2, mutableListOf(
                        Answer("the nucleus", 1, isCorrect = false, isChecked = false),
                        Answer("tissue", 1, isCorrect = false, isChecked = false),
                        Answer("the cell", 1, isCorrect = true, isChecked = false),
                        Answer("the organ", 1, isCorrect = false, isChecked = true),
                        Answer("the atom", 1, isCorrect = false, isChecked = true))))
        val quiz2Response = ValidationResponse(ValidationResponseType.FAIL, "Too many answers", quiz2[1])

        assertEquals(quiz2Response, validator.execute(quiz2))
        assertEquals(quiz2Response, validator.execute(quiz2, listOf(quiz2Response)))
    }

    @Test
    fun `quiz with no questions`() {
        val validator = ValidateQuizInput()

        assertEquals(ValidationResponse(ValidationResponseType.FAIL, "Empty quiz"), validator.execute(listOf()))
    }
}