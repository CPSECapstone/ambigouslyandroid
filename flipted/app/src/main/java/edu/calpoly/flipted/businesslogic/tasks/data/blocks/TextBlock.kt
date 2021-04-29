package edu.calpoly.flipted.businesslogic.tasks.data.blocks

import edu.calpoly.flipted.businesslogic.tasks.data.RubricRequirement

class TextBlock(
        val contents: String,
        val fontSize: Int,
        title: String? = null
) : TaskBlock(title)