package edu.calpoly.flipted.backend

import android.util.Log
import edu.calpoly.flipted.businesslogic.learningTargets.*
import kotlinx.coroutines.delay
import edu.calpoly.flipted.type.Mastery

class MockLearningTargetRepo: LearningTargetRepo
{

    private var uid = 10
        get() {
            field += 1
            return field
        }
    private val uids
        get() = uid.toString()

    private val targetMap : MutableMap<String, TargetProgress> = mutableMapOf()


    // not sure about target name, how would we get Learning Target #1...
    private val learningTarget1 = LearningTarget(uids, uids)
    private val learningTarget2 = LearningTarget(uids, uids)
    private val learningTarget3 = LearningTarget(uids, uids)

    private val taskObjectiveProgress1 = TaskObjectiveProgress(uids,"task 1.0",Mastery.MASTERED)
    private val taskObjectiveProgress2 = TaskObjectiveProgress(uids,"task 2.0",Mastery.NOT_MASTERED)
    private val taskObjectiveProgress3 = TaskObjectiveProgress(uids,"task 3.0",Mastery.MASTERED)
    private val taskObjectiveProgress4 = TaskObjectiveProgress(uids,"task 1.1",Mastery.NEARLY_MASTERED)
    private val taskObjectiveProgress5 = TaskObjectiveProgress(uids,"task 2.1",Mastery.MASTERED)
    private val taskObjectiveProgress6 = TaskObjectiveProgress(uids,"task 3.1",Mastery.NOT_MASTERED)
    private val taskObjectiveProgress7 = TaskObjectiveProgress(uids,"task 1.2",Mastery.NEARLY_MASTERED)
    private val taskObjectiveProgress8 = TaskObjectiveProgress(uids,"task 1.1",Mastery.NEARLY_MASTERED)
    private val taskObjectiveProgress9 = TaskObjectiveProgress(uids,"task 2.1",Mastery.MASTERED)
    private val taskObjectiveProgress10 = TaskObjectiveProgress(uids,"task 3.1",Mastery.NOT_MASTERED)
    private val taskObjectiveProgress11 = TaskObjectiveProgress(uids,"task 1.3",Mastery.NEARLY_MASTERED)
    private val taskObjectiveProgress12 = TaskObjectiveProgress(uids,"task 2.3",Mastery.MASTERED)
    private val taskObjectiveProgress13 = TaskObjectiveProgress(uids,"task 3.3",Mastery.NOT_MASTERED)

    private val objectiveProgress1 = ObjectiveProgress(uids,"Learning Objective 1", listOf(taskObjectiveProgress1,taskObjectiveProgress2))
    private val objectiveProgress2 = ObjectiveProgress(uids,"Learning Objective 2", listOf(taskObjectiveProgress3))
    private val objectiveProgress3 = ObjectiveProgress(uids,"Learning Objective 3", listOf(taskObjectiveProgress4,taskObjectiveProgress5,taskObjectiveProgress6))
    private val objectiveProgress4 = ObjectiveProgress(uids,"Learning Objective 4", listOf(taskObjectiveProgress7,taskObjectiveProgress13))
    private val objectiveProgress5 = ObjectiveProgress(uids,"Learning Objective 5", listOf(taskObjectiveProgress8,taskObjectiveProgress11,taskObjectiveProgress12))
    private val objectiveProgress6 = ObjectiveProgress(uids,"Learning Objective 6", listOf(taskObjectiveProgress9,taskObjectiveProgress10))

    init {
        listOf(
                TargetProgress(learningTarget1, listOf(objectiveProgress1,objectiveProgress2)),
                TargetProgress(learningTarget2, listOf(objectiveProgress3,objectiveProgress4,objectiveProgress5)),
                TargetProgress(learningTarget3, listOf(objectiveProgress6))
        )
    }

    // not sure how to do this if it should return List
    override suspend fun getAllTargetProgress(courseId: String, studentId: String?): List<TargetProgress> {
        delay(1000)
        val progress = listOf(
                TargetProgress(learningTarget1, listOf(objectiveProgress1,objectiveProgress2)),
                TargetProgress(learningTarget2, listOf(objectiveProgress3,objectiveProgress4,objectiveProgress5)),
                TargetProgress(learningTarget3, listOf(objectiveProgress6))
        )
        return progress
    }

}

