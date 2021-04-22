package edu.calpoly.flipted.businesslogic.tasks.data

data class RubricRequirement(
        val description: String,
        val isComplete: Boolean,
        val uid: Int
) {
        val completed : RubricRequirement
                get() = RubricRequirement(
                        description,
                        true,
                        uid
                )
        val incompleted : RubricRequirement
                get() = RubricRequirement(
                        description,
                        false,
                        uid
                )
}