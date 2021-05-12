package edu.calpoly.flipted.businesslogic.quizzes

import org.junit.Test
import org.junit.Assert.*
/*
class TestValidateQuizInput {

    @Test
    fun `question with no answers selected`() {
        val successResponse = listOf<ValidationResponse>()

        val quiz1 = listOf(
            Question("The center of an atom is called the ______.", 1, 1, mutableListOf(
                Answer("atomos", 1, isChecked = true),
                Answer("neutron", 1, isChecked = false),
                Answer("nucleus", 1, isChecked = false),
                Answer("nucleon", 1, isChecked = false))),
            Question("The basic unit of all organisms is ______.", 2, 2, mutableListOf(
                Answer("the nucleus", 1, isChecked = false),
                Answer("tissue", 1, isChecked = false),
                Answer("the cell", 1, isChecked = false),
                Answer("the organ", 1, isChecked = false),
                Answer("the atom", 1, isChecked = false))))
        val quiz1Responses = listOf(ValidationResponse(ValidationResponseType.WARN, "No answer selected", quiz1[1]))


        assertEquals(successResponse, ValidateQuizInput.execute(quiz1, quiz1Responses))

        val quiz2 = listOf(
                Question("The center of an atom is called the ______.", 1, 1, mutableListOf(
                        Answer("atomos", 1, isChecked = false),
                        Answer("neutron", 1, isChecked = false),
                        Answer("nucleus", 1, isChecked = false),
                        Answer("nucleon", 1, isChecked = false))),
                Question("The basic unit of all organisms is ______.", 2, 2, mutableListOf(
                        Answer("the nucleus", 1, isChecked = false),
                        Answer("tissue", 1, isChecked = true),
                        Answer("the cell", 1, isChecked = false),
                        Answer("the organ", 1, isChecked = false),
                        Answer("the atom", 1, isChecked = false))))
        val quiz2Responses = listOf(ValidationResponse(ValidationResponseType.WARN, "No answer selected", quiz2[0]))

        assertEquals(successResponse, ValidateQuizInput.execute(quiz2, quiz2Responses))
        assertEquals(quiz2Responses, ValidateQuizInput.execute(quiz2))

        val quiz3 = listOf(
                Question("The center of an atom is called the ______.", 1, 1, mutableListOf(
                        Answer("atomos", 1, isChecked = false),
                        Answer("neutron", 1, isChecked = false),
                        Answer("nucleus", 1, isChecked = false),
                        Answer("nucleon", 1, isChecked = false))),
                Question("The basic unit of all organisms is ______.", 2, 2, mutableListOf(
                        Answer("the nucleus", 1, isChecked = false),
                        Answer("tissue", 1, isChecked = false),
                        Answer("the cell", 1, isChecked = false),
                        Answer("the organ", 1, isChecked = false),
                        Answer("the atom", 1, isChecked = true))))
        val quiz3Responses = listOf(ValidationResponse(ValidationResponseType.WARN, "No answer selected", quiz3[0]))

        assertEquals(quiz3Responses, ValidateQuizInput.execute(quiz3))
        assertEquals(successResponse, ValidateQuizInput.execute(quiz3, quiz3Responses))

        val quiz4 = listOf(
                Question("The center of an atom is called the ______.", 1, 1, mutableListOf(
                        Answer("atomos", 1, isChecked = false),
                        Answer("neutron", 1, isChecked = false),
                        Answer("nucleus", 1, isChecked = false),
                        Answer("nucleon", 1, isChecked = false))),
                Question("The basic unit of all organisms is ______.", 2, 2, mutableListOf(
                        Answer("the nucleus", 1, isChecked = false),
                        Answer("tissue", 1, isChecked = false),
                        Answer("the cell", 1, isChecked = false),
                        Answer("the organ", 1, isChecked = false),
                        Answer("the atom", 1, isChecked = false))))
        val quiz4Responses = listOf(
                ValidationResponse(ValidationResponseType.WARN, "No answer selected", quiz4[0]),
                ValidationResponse(ValidationResponseType.WARN, "No answer selected", quiz4[1]))

        assertEquals(quiz4Responses, ValidateQuizInput.execute(quiz4))
        assertEquals(successResponse, ValidateQuizInput.execute(quiz4, quiz4Responses))
    }

    @Test
    fun `question with more than one answer selected`() {

        val quiz1 = listOf(
                Question("The center of an atom is called the ______.", 1, 1, mutableListOf(
                        Answer("atomos", 1, isChecked = true),
                        Answer("neutron", 1, isChecked = false),
                        Answer("nucleus", 1, isChecked = true),
                        Answer("nucleon", 1, isChecked = false))),
                Question("The basic unit of all organisms is ______.", 2, 2, mutableListOf(
                        Answer("the nucleus", 1, isChecked = true),
                        Answer("tissue", 1, isChecked = false),
                        Answer("the cell", 1, isChecked = false),
                        Answer("the organ", 1, isChecked = false),
                        Answer("the atom", 1, isChecked = false))))
        val quiz1Response = ValidationResponse(ValidationResponseType.FAIL, "Too many answers", quiz1[0])

        assertEquals(listOf(quiz1Response), ValidateQuizInput.execute(quiz1))
        assertEquals(listOf(quiz1Response), ValidateQuizInput.execute(quiz1, listOf(quiz1Response)))

        val quiz2 = listOf(
                Question("The center of an atom is called the ______.", 1, 1, mutableListOf(
                        Answer("atomos", 1, isChecked = true),
                        Answer("neutron", 1, isChecked = false),
                        Answer("nucleus", 1, isChecked = false),
                        Answer("nucleon", 1, isChecked = false))),
                Question("The basic unit of all organisms is ______.", 2, 2, mutableListOf(
                        Answer("the nucleus", 1, isChecked = false),
                        Answer("tissue", 1, isChecked = false),
                        Answer("the cell", 1, isChecked = false),
                        Answer("the organ", 1, isChecked = true),
                        Answer("the atom", 1, isChecked = true))))
        val quiz2Response = ValidationResponse(ValidationResponseType.FAIL, "Too many answers", quiz2[1])

        assertEquals(listOf(quiz2Response), ValidateQuizInput.execute(quiz2))
        assertEquals(listOf(quiz2Response), ValidateQuizInput.execute(quiz2, listOf(quiz2Response)))
    }

    @Test
    fun `quiz with no questions`() {
        assertEquals(listOf(ValidationResponse(ValidationResponseType.FAIL, "Empty quiz")), ValidateQuizInput.execute(listOf()))
    }
}
*/
