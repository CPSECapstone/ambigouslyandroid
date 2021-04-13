package edu.calpoly.flipted.businesslogic.tasks.data.blocks

import edu.calpoly.flipted.businesslogic.tasks.data.RubricRequirement

abstract class TaskBlock(
        open val requirement: RubricRequirement? = null,
        val title: String? = null
) {
        abstract val completed : TaskBlock
        abstract val incompleted : TaskBlock
}