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
                uids.let{
                    ObjectiveProgress(it, "Objective 1", listOf(
                        TaskObjectiveProgress(uids, "Task 1", it, "Objective 1", Mastery.NOT_MASTERED),
                        TaskObjectiveProgress(uids, "Something Else", it, "Objective 1", Mastery.NOT_MASTERED),
                        TaskObjectiveProgress(uids, "Task 3.14", it, "Objective 1", Mastery.NOT_MASTERED),
                        TaskObjectiveProgress(uids, "Task kasT", it, "Objective 1", Mastery.NEARLY_MASTERED),
                        TaskObjectiveProgress(uids, "Do your homework", it, "Objective 1", Mastery.NOT_GRADED),
                        TaskObjectiveProgress(uids, "Blah blah blah", it, "Objective 1", Mastery.NOT_GRADED)
                    ))
                },
                uids.let{
                    ObjectiveProgress(it, "Objective 2", listOf(
                        TaskObjectiveProgress(uids, "Task 42", it, "Objective 2", Mastery.NOT_MASTERED),
                        TaskObjectiveProgress(uids, "NOTHING", it, "Objective 2", Mastery.NEARLY_MASTERED),
                        TaskObjectiveProgress(uids, "Task 2.01", it, "Objective 2", Mastery.NOT_MASTERED),
                        TaskObjectiveProgress(uids, "ksaT Task", it, "Objective 2", Mastery.MASTERED),
                        TaskObjectiveProgress(uids, "I don't believe in homework", it, "Objective 2", Mastery.NOT_GRADED),
                        TaskObjectiveProgress(uids, "NOPENOPENOPENOPE", it, "Objective 2", Mastery.NOT_GRADED)
                    ))
                }
            )
        ),
        TargetProgress(
            LearningTarget(uids, "Learning target 2"),
            listOf(
                uids.let{
                    ObjectiveProgress(it, "Objective 1", listOf(
                        TaskObjectiveProgress(uids, "FU", it, "Objective 1", Mastery.MASTERED),
                        TaskObjectiveProgress(uids, "BLEH", it, "Objective 1", Mastery.MASTERED),
                        TaskObjectiveProgress(uids, "Task 1337", it, "Objective 1", Mastery.MASTERED),
                        TaskObjectiveProgress(uids, "H4X0R", it, "Objective 1", Mastery.MASTERED),
                        TaskObjectiveProgress(uids, "aaAAAaa", it, "Objective 1", Mastery.NEARLY_MASTERED),
                        TaskObjectiveProgress(uids, "0x13456babF", it, "Objective 1", Mastery.NOT_GRADED)
                    ))
                }
            )
        ),
        TargetProgress(
            LearningTarget(uids, "Learning target 3"),
            listOf(
                uids.let{
                    ObjectiveProgress(it, "Objective 1", listOf(
                        TaskObjectiveProgress(uids, "4 FU", it, "Objective 1", Mastery.MASTERED),
                        TaskObjectiveProgress(uids, "4 BLEH", it, "Objective 1", Mastery.MASTERED),
                        TaskObjectiveProgress(uids, "4 Task 1337", it, "Objective 1", Mastery.MASTERED),
                        TaskObjectiveProgress(uids, "4 H4X0R", it, "Objective 1", Mastery.MASTERED),
                        TaskObjectiveProgress(uids, "4 aaAAAaa", it, "Objective 1", Mastery.NEARLY_MASTERED),
                        TaskObjectiveProgress(uids, "4 0x13456babF", it, "Objective 1", Mastery.NOT_GRADED)
                    ))
                },
                uids.let{
                    ObjectiveProgress(it, "Objective 1", listOf(
                        TaskObjectiveProgress(uids, "5 FU", it, "Objective 1", Mastery.MASTERED),
                        TaskObjectiveProgress(uids, "5 BLEH", it, "Objective 1", Mastery.MASTERED),
                        TaskObjectiveProgress(uids, "5 Task 1337", it, "Objective 1", Mastery.MASTERED),
                        TaskObjectiveProgress(uids, "5 H4X0R", it, "Objective 1", Mastery.MASTERED),
                        TaskObjectiveProgress(uids, "5 aaAAAaa", it, "Objective 1", Mastery.NEARLY_MASTERED),
                        TaskObjectiveProgress(uids, "5 0x13456babF", it, "Objective 1", Mastery.NOT_GRADED)
                    ))
                }
            )
        ),
        TargetProgress(
            LearningTarget(uids, "Learning target 4"),
            listOf(
                uids.let{
                    ObjectiveProgress(it, "Objective 1", listOf(
                        TaskObjectiveProgress(uids, "6 FU", it, "Objective 1", Mastery.MASTERED),
                        TaskObjectiveProgress(uids, "6 BLEH", it, "Objective 1", Mastery.MASTERED),
                        TaskObjectiveProgress(uids, "6 Task 1337", it, "Objective 1", Mastery.MASTERED),
                        TaskObjectiveProgress(uids, "6 H4X0R", it, "Objective 1", Mastery.MASTERED),
                        TaskObjectiveProgress(uids, "6 aaAAAaa", it, "Objective 1", Mastery.NEARLY_MASTERED),
                        TaskObjectiveProgress(uids, "6 0x13456babF", it, "Objective 1", Mastery.NOT_GRADED)
                    ))
                },
                uids.let{
                    ObjectiveProgress(it, "Objective 1", listOf(
                        TaskObjectiveProgress(uids, "7 FU", it, "Objective 1", Mastery.MASTERED),
                        TaskObjectiveProgress(uids, "7 BLEH", it, "Objective 1", Mastery.MASTERED),
                        TaskObjectiveProgress(uids, "7 Task 1337", it, "Objective 1", Mastery.MASTERED),
                        TaskObjectiveProgress(uids, "7 H4X0R", it, "Objective 1", Mastery.MASTERED),
                        TaskObjectiveProgress(uids, "7 aaAAAaa", it, "Objective 1", Mastery.NEARLY_MASTERED),
                        TaskObjectiveProgress(uids, "7 0x13456babF", it, "Objective 1", Mastery.NOT_GRADED)
                    ))
                }
            )
        )
    )
    
    override suspend fun getAllTargetProgress(courseId: String, studentId: String?): List<TargetProgress> {
        delay(1000)
        return targets
    }

}

