package edu.calpoly.flipted.businesslogic.tasks.data.blocks

import edu.calpoly.flipted.businesslogic.tasks.data.RubricRequirement

class QuestionBlock(
        val question: String,
        val uid: Integer,
        requirement: RubricRequirement,
        title: String?
) : TaskBlock(requirement, title)