package edu.calpoly.flipted.businesslogic.targets

import org.junit.Assert.*
import org.junit.Test


class CalculateObjectiveMasteryTest {

    private fun calculate(vararg inputs: Mastery)
        = CalculateObjectiveMastery.execute(ObjectiveProgress(
            "",
            "",
            inputs.map {
                TaskObjectiveProgress("", "", it)
            }
    ))

    @Test
    fun `test single masteries`() {
        assertEquals(Mastery.NOT_MASTERED, calculate(Mastery.NOT_MASTERED))
        assertEquals(Mastery.NEARLY_MASTERED, calculate(Mastery.NEARLY_MASTERED))
        assertEquals(Mastery.MASTERED, calculate(Mastery.MASTERED))
    }

    @Test
    fun `test combinations of two masteries`() {
        assertEquals(Mastery.NOT_MASTERED, calculate(Mastery.NOT_MASTERED, Mastery.NOT_MASTERED))
        assertEquals(Mastery.NOT_MASTERED, calculate(Mastery.NOT_MASTERED, Mastery.NEARLY_MASTERED))
        assertEquals(Mastery.NEARLY_MASTERED, calculate(Mastery.NOT_MASTERED, Mastery.MASTERED))
        assertEquals(Mastery.NEARLY_MASTERED, calculate(Mastery.NEARLY_MASTERED, Mastery.NEARLY_MASTERED))
        assertEquals(Mastery.MASTERED, calculate(Mastery.NEARLY_MASTERED, Mastery.MASTERED))
        assertEquals(Mastery.MASTERED, calculate(Mastery.MASTERED, Mastery.MASTERED))
    }

    @Test
    fun `test some not graded masteries`() {
        assertEquals(Mastery.NOT_MASTERED, calculate(Mastery.NOT_MASTERED, Mastery.NOT_GRADED))
        assertEquals(Mastery.NEARLY_MASTERED, calculate(Mastery.NEARLY_MASTERED, Mastery.NOT_GRADED))
        assertEquals(Mastery.MASTERED, calculate(Mastery.MASTERED, Mastery.NOT_GRADED))

        assertEquals(Mastery.NOT_MASTERED, calculate(Mastery.NOT_MASTERED, Mastery.NOT_GRADED, Mastery.NOT_MASTERED, Mastery.NOT_GRADED))
        assertEquals(Mastery.NOT_MASTERED, calculate(Mastery.NOT_MASTERED, Mastery.NOT_GRADED, Mastery.NEARLY_MASTERED, Mastery.NOT_GRADED))
        assertEquals(Mastery.NEARLY_MASTERED, calculate(Mastery.NOT_MASTERED, Mastery.NOT_GRADED, Mastery.MASTERED, Mastery.NOT_GRADED))
        assertEquals(Mastery.NEARLY_MASTERED, calculate(Mastery.NEARLY_MASTERED, Mastery.NOT_GRADED, Mastery.NEARLY_MASTERED, Mastery.NOT_GRADED))
        assertEquals(Mastery.MASTERED, calculate(Mastery.NEARLY_MASTERED, Mastery.NOT_GRADED, Mastery.MASTERED, Mastery.NOT_GRADED))
        assertEquals(Mastery.MASTERED, calculate(Mastery.MASTERED, Mastery.NOT_GRADED, Mastery.MASTERED, Mastery.NOT_GRADED))
    }

    @Test
    fun `test all not graded masteries`() {
        assertEquals(Mastery.NOT_GRADED, calculate())
        assertEquals(Mastery.NOT_GRADED, calculate(Mastery.NOT_GRADED))
        assertEquals(Mastery.NOT_GRADED, calculate(Mastery.NOT_GRADED, Mastery.NOT_GRADED))
    }
}