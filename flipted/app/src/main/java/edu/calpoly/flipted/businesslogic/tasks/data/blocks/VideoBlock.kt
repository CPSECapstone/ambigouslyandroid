package edu.calpoly.flipted.businesslogic.tasks.data.blocks

import edu.calpoly.flipted.businesslogic.tasks.data.RubricRequirement

class VideoBlock(
        val videoUrl: String,
        title: String? = null
) : TaskBlock(title)