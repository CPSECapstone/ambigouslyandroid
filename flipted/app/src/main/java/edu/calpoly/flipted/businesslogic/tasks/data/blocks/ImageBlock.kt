package edu.calpoly.flipted.businesslogic.tasks.data.blocks

import edu.calpoly.flipted.businesslogic.tasks.data.RubricRequirement

class ImageBlock(
        val imageUrl: String,
        title: String? = null,
        requirement: RubricRequirement? = null
) : TaskBlock(requirement, title) {
        override val completed: TaskBlock
                get() = ImageBlock(imageUrl, title, requirement?.completed)
        override val incompleted: TaskBlock
                get() = ImageBlock(imageUrl, title, requirement?.incompleted)
}