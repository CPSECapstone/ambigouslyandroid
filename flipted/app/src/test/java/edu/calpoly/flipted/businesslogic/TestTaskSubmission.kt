package edu.calpoly.flipted.businesslogic


import edu.calpoly.flipted.backend.MockTasksRepo
import edu.calpoly.flipted.businesslogic.quizzes.data.questions.FreeResponseQuestion
import edu.calpoly.flipted.businesslogic.quizzes.data.questions.MultipleChoiceAnswerOption
import edu.calpoly.flipted.businesslogic.quizzes.data.questions.MultipleChoiceQuestion
import edu.calpoly.flipted.businesslogic.tasks.*
import edu.calpoly.flipted.businesslogic.tasks.SaveTaskProgress
import edu.calpoly.flipted.businesslogic.tasks.data.*
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.ImageBlock
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.QuizBlock
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.TextBlock
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.VideoBlock
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.Assert.*
import java.util.*


class TestTaskSubmissionTargets {
    class MockTasksRepo : TasksRepo  {
        val data = mutableListOf<TaskRubricProgress>()
        override suspend fun saveRubricProgress(progress: TaskRubricProgress) {
            data.add(progress)
        }
        override suspend fun getTask(taskId: String): Task {
            TODO("Not yet implemented")
        }
        override suspend fun saveQuizAnswer(answer: TaskQuizAnswer) {
            TODO("Not yet implemented")
        }

        override suspend fun submitTask(taskId: String): TaskSubmissionResult {
            TODO("Not yet implemented")
        }
    }

    @Test
    fun getAllTargetProgressTest() = runBlockingTest {
        val repo = MockTasksRepo()
        val useCase = SaveTaskProgress(repo)

        val RubricRequirement1 = RubricRequirement("test",true, "1")
        val RubricRequirement2 = RubricRequirement("test",false, "1")

        val task = Task(
            listOf(
                Page(listOf(
                    // TextBlock("What is a Test Bus?", 36),
                    TextBlock("""
                    A Test Bus is an interface that exists between the user interface and the
                    core logic of an application. This facilitates unit testing by allowing
                    tests to directly access the underlying logic of the application without
                    having to futz around with the user interface.
                """.trimIndent(), 18,"What is a Test Bus?"),
                    VideoBlock("https://www.youtube.com/watch?v=VJi2vmaQe6w", "Watch this video"),
                    ImageBlock("https://www.digitalhrtech.com/wp-content/uploads/2020/01/Learning-and-development-manager.png","Time to Learn!"),
                    QuizBlock(
                        listOf(
                            MultipleChoiceQuestion(
                                listOf(
                                    MultipleChoiceAnswerOption("Class", edu.calpoly.flipted.backend.MockTasksRepo.uid),
                                    MultipleChoiceAnswerOption("Interface", edu.calpoly.flipted.backend.MockTasksRepo.uid),
                                    MultipleChoiceAnswerOption("Instance", edu.calpoly.flipted.backend.MockTasksRepo.uid)
                                ),"A test bus is a...", 2, edu.calpoly.flipted.backend.MockTasksRepo.uids, null),
                            MultipleChoiceQuestion(
                                listOf(
                                    MultipleChoiceAnswerOption("the user and user interface", edu.calpoly.flipted.backend.MockTasksRepo.uid),
                                    MultipleChoiceAnswerOption("the user interface and core logic", edu.calpoly.flipted.backend.MockTasksRepo.uid),
                                    MultipleChoiceAnswerOption("the client device and server", edu.calpoly.flipted.backend.MockTasksRepo.uid),
                                    MultipleChoiceAnswerOption("the operating system and running application", edu.calpoly.flipted.backend.MockTasksRepo.uid)
                                ), "The test bus exists between...", 2, edu.calpoly.flipted.backend.MockTasksRepo.uids, null)

                        ), 2, edu.calpoly.flipted.backend.MockTasksRepo.uids, 3)
                ), true), Page(listOf(
                    TextBlock("Why are they useful?", 36),
                    TextBlock("""
                    Unit tests that interact with a user interface tend to be complex, difficult to
                    write, difficult to maintain, and easy to break. Since a user interface will
                    typically change much more frequently than the underlying logic, writing a
                    test that directly accesses the underlying logic reduces the amount of work
                    required every time the user interface is updated.
                """.trimIndent(), 18)
                ), true), Page(listOf(
                    TextBlock("Summarize the reading", 36),
                    QuizBlock(
                        listOf(
                            FreeResponseQuestion("""
                            In your own words, summarize
                            what is a Test Bus and why they are useful.
                        """.trimIndent(), 6, edu.calpoly.flipted.backend.MockTasksRepo.uids, null)
                        ),
                        0, edu.calpoly.flipted.backend.MockTasksRepo.uids, 5)
                ), true)
            ), listOf(
                RubricRequirement("Read about the Test Bus", false,
                    "1"
                ),
                RubricRequirement("Complete the summary task", false,
                    "2"
                )
            ), "4f681550ba9", "Learn about the Test Bus", "Complete this task",
            10, null, null, Date("4-25-2021"), "", 0, null)

        val reqs = mutableListOf<TaskRubricProgress>()
        reqs.add(TaskRubricProgress(listOf(RubricRequirement1), task))
        reqs.add(TaskRubricProgress(listOf(RubricRequirement2), task)

        assertEquals(listOf<TaskRubricProgress>(), useCase.execute())
        repo.data.addAll(reqs)
        assertEquals(listOf(TaskRubricProgress(listOf(RubricRequirement1), task), TaskRubricProgress(listOf(RubricRequirement2), task)),useCase.execute())
    }


}