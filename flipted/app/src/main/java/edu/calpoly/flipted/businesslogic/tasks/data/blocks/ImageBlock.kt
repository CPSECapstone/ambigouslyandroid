package edu.calpoly.flipted.businesslogic.tasks.data.blocks

import edu.calpoly.flipted.businesslogic.tasks.data.RubricRequirement

class ImageBlock(
        val uid: String,
        val imageUrl: String,
        title: String? = null
) : TaskBlock(title)