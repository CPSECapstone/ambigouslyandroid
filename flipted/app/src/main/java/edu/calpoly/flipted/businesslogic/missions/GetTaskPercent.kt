package edu.calpoly.flipted.businesslogic.missions

object GetPercent {
    fun getTaskPercent(pointsPossible: Int, pointsAwarded: Int): Float {
        return if(pointsPossible == 0) {
            100F
        } else {
            (pointsAwarded.toFloat() / pointsPossible.toFloat())*100
        }
    }

}