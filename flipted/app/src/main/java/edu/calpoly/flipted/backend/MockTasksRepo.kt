package edu.calpoly.flipted.backend

import edu.calpoly.flipted.businesslogic.quizzes.data.StudentAnswerInput
import edu.calpoly.flipted.businesslogic.quizzes.data.questions.FreeResponseQuestion
import edu.calpoly.flipted.businesslogic.quizzes.data.questions.MultipleChoiceAnswerOption
import edu.calpoly.flipted.businesslogic.quizzes.data.questions.MultipleChoiceQuestion
import edu.calpoly.flipted.businesslogic.tasks.TasksRepo
import edu.calpoly.flipted.businesslogic.tasks.data.*
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.ImageBlock
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.QuizBlock
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.TextBlock
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.VideoBlock
import java.text.SimpleDateFormat

class MockTasksRepo : TasksRepo {
    companion object {
        private val dateFormat = SimpleDateFormat("M-d-y")
        private var uid = 10
            get() {
                field += 1
                return field
            }

        private val uids : String
            get() {
                return uid.toString()
            }
    }

    private val mockedTask = Task(
        listOf(
            Page(listOf(
                TextBlock("What is a Test Bus?", 36),
                TextBlock("""
                    A Test Bus is an interface that exists between the user interface and the
                    core logic of an application. This facilitates unit testing by allowing
                    tests to directly access the underlying logic of the application without
                    having to futz around with the user interface.
                """.trimIndent(), 18),
                VideoBlock("https://www.youtube.com/watch?v=VJi2vmaQe6w", "Watch this video"),
                TextBlock("Look at This!", 36),
                ImageBlock("https://www.digitalhrtech.com/wp-content/uploads/2020/01/Learning-and-development-manager.png"),
                TextBlock("Check your knowledge", 24),
                QuizBlock(
                    listOf(
                        MultipleChoiceQuestion(
                            listOf(
                                MultipleChoiceAnswerOption("Class", uid),
                                MultipleChoiceAnswerOption("Interface", uid),
                                MultipleChoiceAnswerOption("Instance", uid)
                            ),"A test bus is a...", 2, uids, null),
                        MultipleChoiceQuestion(
                            listOf(
                                MultipleChoiceAnswerOption("the user and user interface", uid),
                                MultipleChoiceAnswerOption("the user interface and core logic", uid),
                                MultipleChoiceAnswerOption("the client device and server", uid),
                                MultipleChoiceAnswerOption("the operating system and running application", uid)
                            ), "The test bus exists between...", 2, uids, null)

                        ), 2, uids, 3)
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
                        """.trimIndent(), 6, uids, null)),
                        0, uids, 5)
            ), true)
        ), listOf(
            RubricRequirement("Read about the Test Bus", false, uids),
            RubricRequirement("Complete the summary task", false, uids)
        ), "c5110abd8c4", "Learn about the Test Bus", "Complete this task",
        10, null, null, dateFormat.parse("4-25-2021"), "", 0, null)

    private var savedProgress: MutableSet<String> = mutableSetOf()

    private var savedQuestionAnswers: MutableMap<String, StudentAnswerInput> = mutableMapOf()


    override suspend fun getTask(taskId: String) : Task {
        if(taskId != mockedTask.uid)
            throw IllegalArgumentException("No task with $taskId exists")
        return Task(mockedTask.pages, mockedTask.requirements.map {
            if(it.uid in savedProgress) it.completed else it.incompleted
        }, mockedTask.uid, mockedTask.name, mockedTask.instructions,
        mockedTask.points, mockedTask.startAt, mockedTask.endAt,
        mockedTask.dueDate, mockedTask.parentMissionId, mockedTask.parentMissionIndex, mockedTask.objectiveId)
    }

    override suspend fun saveRubricProgress(progress: TaskRubricProgress) {
        if(progress.task.uid != mockedTask.uid)
            throw IllegalArgumentException("No task with ${progress.task.uid} exists")
        progress.finishedRequirements.forEach{
            savedProgress.add(it.uid)
        }
    }

    override suspend fun saveQuizAnswer(answer: TaskQuizAnswer) {
        if(answer.task.uid != mockedTask.uid)
            throw IllegalArgumentException("No task with ${answer.task.uid} exists")
        savedQuestionAnswers[answer.answer.questionId] = answer.answer
    }

    override suspend fun submitTask(taskId : String) : TaskSubmissionResult {
        return TaskSubmissionResult(taskId, true, 8)
    }


}