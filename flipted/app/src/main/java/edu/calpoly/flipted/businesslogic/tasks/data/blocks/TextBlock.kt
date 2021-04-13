package edu.calpoly.flipted.businesslogic.tasks.data.blocks

import edu.calpoly.flipted.businesslogic.tasks.data.RubricRequirement

class TextBlock(
        val contents: String,
        val fontSize: Int,
        title: String? = null,
        requirement: RubricRequirement? = null
) : TaskBlock(requirement, title) {
        override val completed: TaskBlock
                get() = TextBlock(contents, fontSize, title, requirement?.completed)
        override val incompleted: TaskBlock
                get() = TextBlock(contents, fontSize, title, requirement?.incompleted)
}