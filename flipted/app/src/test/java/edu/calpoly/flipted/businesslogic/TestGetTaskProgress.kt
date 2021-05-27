package edu.calpoly.flipted.businesslogic


import edu.calpoly.flipted.businesslogic.missions.GetPercent
import org.junit.Test
import org.junit.Assert.*

class TestGetTaskProgress {

    @Test
    fun zeroPointTask() {
        val pointsAwarded = 1
        val pointsPossible = 0
        assertEquals(100F, GetPercent.getTaskPercent(pointsPossible,pointsAwarded))
    }

    @Test
    fun normalPointTask() {
        val pointsAwarded = 10
        val pointsPossible = 20
        assertEquals(50F, GetPercent.getTaskPercent(pointsPossible,pointsAwarded))
    }

    @Test
    fun extraPointTask() {
        val pointsAwarded = 21
        val pointsPossible = 20
        assertEquals(104.99999F, GetPercent.getTaskPercent(pointsPossible,pointsAwarded))
    }

}