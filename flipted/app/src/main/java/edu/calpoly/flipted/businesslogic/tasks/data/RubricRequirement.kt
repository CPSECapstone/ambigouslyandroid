package edu.calpoly.flipted.businesslogic.tasks.data

data class RubricRequirement(
        val description: String,
        val isComplete: Boolean,
        val pointValue: Int,
        val uid: Int
) {
        val completed : RubricRequirement
                get() = RubricRequirement(
                        description,
                        true,
                        pointValue,
                        uid
                )
        val incompleted : RubricRequirement
                get() = RubricRequirement(
                        description,
                        false,
                        pointValue,
                        uid
                )
}