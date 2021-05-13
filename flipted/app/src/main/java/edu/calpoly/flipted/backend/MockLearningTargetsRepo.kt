package edu.calpoly.flipted.backend

import edu.calpoly.flipted.businesslogic.targets.*
import kotlinx.coroutines.delay

class MockLearningTargetsRepo: LearningTargetsRepo
{
    companion object {
        private var uid = 10
            get() {
                field += 1
                return field
            }
        private val uids
            get() = uid.toString()
    }
    private val targets : List<TargetProgress> = listOf(
        TargetProgress(
            LearningTarget(uids, "Learning target 1"),
            listOf(
                ObjectiveProgress(uids, "Objective 1", listOf(
                    TaskObjectiveProgress(uids, "Task 1", Mastery.NOT_MASTERED),
                    TaskObjectiveProgress(uids, "Something Else", Mastery.NOT_MASTERED),
                    TaskObjectiveProgress(uids, "Task 3.14", Mastery.NOT_MASTERED),
                    TaskObjectiveProgress(uids, "Task kasT", Mastery.NEARLY_MASTERED),
                    TaskObjectiveProgress(uids, "Do your homework", Mastery.NOT_GRADED),
                    TaskObjectiveProgress(uids, "Blah blah blah", Mastery.NOT_GRADED)
                )),
                ObjectiveProgress(uids, "Objective 2", listOf(
                    TaskObjectiveProgress(uids, "Task 42", Mastery.NOT_MASTERED),
                    TaskObjectiveProgress(uids, "NOTHING", Mastery.NEARLY_MASTERED),
                    TaskObjectiveProgress(uids, "Task 2.01", Mastery.NOT_MASTERED),
                    TaskObjectiveProgress(uids, "ksaT Task", Mastery.MASTERED),
                    TaskObjectiveProgress(uids, "I don't believe in homework", Mastery.NOT_GRADED),
                    TaskObjectiveProgress(uids, "NOPENOPENOPENOPE", Mastery.NOT_GRADED)
                ))
            )
        ),
        TargetProgress(
            LearningTarget(uids, "Learning target 1"),
            listOf(
                ObjectiveProgress(uids, "Objective 1", listOf(
                    TaskObjectiveProgress(uids, "FU", Mastery.MASTERED),
                    TaskObjectiveProgress(uids, "BLEH", Mastery.MASTERED),
                    TaskObjectiveProgress(uids, "Task 1337", Mastery.MASTERED),
                    TaskObjectiveProgress(uids, "H4X0R", Mastery.MASTERED),
                    TaskObjectiveProgress(uids, "aaAAAaa", Mastery.NEARLY_MASTERED),
                    TaskObjectiveProgress(uids, "0x13456babF", Mastery.NOT_GRADED)
                ))
            )
        ),
        TargetProgress(
            LearningTarget(uids, "Learning target 1"),
            listOf(
                ObjectiveProgress(uids, "Objective 1", listOf(
                    TaskObjectiveProgress(uids, "4 FU", Mastery.MASTERED),
                    TaskObjectiveProgress(uids, "4 BLEH", Mastery.MASTERED),
                    TaskObjectiveProgress(uids, "4 Task 1337", Mastery.MASTERED),
                    TaskObjectiveProgress(uids, "4 H4X0R", Mastery.MASTERED),
                    TaskObjectiveProgress(uids, "4 aaAAAaa", Mastery.NEARLY_MASTERED),
                    TaskObjectiveProgress(uids, "4 0x13456babF", Mastery.NOT_GRADED)
                )),
                ObjectiveProgress(uids, "Objective 1", listOf(
                    TaskObjectiveProgress(uids, "5 FU", Mastery.MASTERED),
                    TaskObjectiveProgress(uids, "5 BLEH", Mastery.MASTERED),
                    TaskObjectiveProgress(uids, "5 Task 1337", Mastery.MASTERED),
                    TaskObjectiveProgress(uids, "5 H4X0R", Mastery.MASTERED),
                    TaskObjectiveProgress(uids, "5 aaAAAaa", Mastery.NEARLY_MASTERED),
                    TaskObjectiveProgress(uids, "5 0x13456babF", Mastery.NOT_GRADED)
                ))
            )
        ),
        TargetProgress(
            LearningTarget(uids, "Learning target 1"),
            listOf(
                ObjectiveProgress(uids, "Objective 1", listOf(
                    TaskObjectiveProgress(uids, "6 FU", Mastery.MASTERED),
                    TaskObjectiveProgress(uids, "6 BLEH", Mastery.MASTERED),
                    TaskObjectiveProgress(uids, "6 Task 1337", Mastery.MASTERED),
                    TaskObjectiveProgress(uids, "6 H4X0R", Mastery.MASTERED),
                    TaskObjectiveProgress(uids, "6 aaAAAaa", Mastery.NEARLY_MASTERED),
                    TaskObjectiveProgress(uids, "6 0x13456babF", Mastery.NOT_GRADED)
                )),
                ObjectiveProgress(uids, "Objective 1", listOf(
                    TaskObjectiveProgress(uids, "7 FU", Mastery.MASTERED),
                    TaskObjectiveProgress(uids, "7 BLEH", Mastery.MASTERED),
                    TaskObjectiveProgress(uids, "7 Task 1337", Mastery.MASTERED),
                    TaskObjectiveProgress(uids, "7 H4X0R", Mastery.MASTERED),
                    TaskObjectiveProgress(uids, "7 aaAAAaa", Mastery.NEARLY_MASTERED),
                    TaskObjectiveProgress(uids, "7 0x13456babF", Mastery.NOT_GRADED)
                ))
            )
        )
    )
    
    override suspend fun getAllTargetProgress(courseId: String, studentId: String?): List<TargetProgress> {
        delay(1000)
        return targets
    }

}

