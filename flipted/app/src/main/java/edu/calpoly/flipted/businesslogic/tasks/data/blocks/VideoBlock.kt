package edu.calpoly.flipted.businesslogic.tasks.data.blocks

import edu.calpoly.flipted.businesslogic.tasks.data.RubricRequirement

class VideoBlock(
        val videoUrl: String,
        requirement: RubricRequirement? = null,
        title: String? = null
) : TaskBlock(requirement, title) {
        override val completed: TaskBlock
                get() = VideoBlock(videoUrl, requirement?.completed, title)
        override val incompleted: TaskBlock
                get() = VideoBlock(videoUrl, requirement?.incompleted, title)
}