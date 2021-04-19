package edu.calpoly.flipted.backend

import edu.calpoly.flipted.businesslogic.quizzes.data.questions.FreeResponseQuestion
import edu.calpoly.flipted.businesslogic.quizzes.data.questions.MultipleChoiceAnswerOption
import edu.calpoly.flipted.businesslogic.quizzes.data.questions.MultipleChoiceQuestion
import edu.calpoly.flipted.businesslogic.tasks.TasksRepo
import edu.calpoly.flipted.businesslogic.tasks.data.*
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.*
import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat

class MockTasksRepo : TasksRepo {
    companion object {
        private val dateFormat = SimpleDateFormat("M-d-y")
        private var uid = 10
            get() {
                field += 1
                return field
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
                VideoBlock("https://www.youtube.com/watch?v=VJi2vmaQe6w", null, "Watch this video"),
                TextBlock("Check your knowledge", 24),
                QuizBlock(
                    listOf(
                        MultipleChoiceQuestion(
                            listOf(
                                MultipleChoiceAnswerOption("Class", uid),
                                MultipleChoiceAnswerOption("Interface", uid),
                                MultipleChoiceAnswerOption("Instance", uid)
                            ),"A test bus is a...", 2, uid),
                        MultipleChoiceQuestion(
                            listOf(
                                MultipleChoiceAnswerOption("the user and user interface", uid),
                                MultipleChoiceAnswerOption("the user interface and core logic", uid),
                                MultipleChoiceAnswerOption("the client device and server", uid),
                                MultipleChoiceAnswerOption("the operating system and running application", uid)
                            ), "The test bus exists between...", 2, uid)
                        ), 2,
                    RubricRequirement("Check your knowledge: What is a Test Bus?", false, uid))
            )), Page(listOf(
                TextBlock("Why are they useful?", 36),
                TextBlock("""
                    Unit tests that interact with a user interface tend to be complex, difficult to
                    write, difficult to maintain, and easy to break. Since a user interface will
                    typically change much more frequently than the underlying logic, writing a
                    test that directly accesses the underlying logic reduces the amount of work
                    required every time the user interface is updated.
                """.trimIndent(), 18)
            )), Page(listOf(
                TextBlock("Summarize the reading", 36),
                QuizBlock(
                    listOf(
                        FreeResponseQuestion("""
                            In your own words, summarize
                            what is a Test Bus and why they are useful.
                        """.trimIndent(), 6, uid)),
                    0,
                    RubricRequirement("Summarize the reading", false, uid))
            ))
        ), dateFormat.parse("4-25-2021")!!, "Learn about the Test Bus", 10, 1)

    private var savedProgress: MutableSet<Int> = mutableSetOf()

    override suspend fun getTask(taskId: Int) : Task {
        if(taskId != mockedTask.uid)
            throw IllegalArgumentException("No task with $taskId exists")
        return Task(
            mockedTask.pages.map { page ->
                Page(page.blocks.map { block ->
                    if(savedProgress.contains(block.requirement?.uid))
                        block.completed
                    else
                        block.incompleted
                })
            }, mockedTask.dueDate, mockedTask.title, mockedTask.points, mockedTask.uid)
    }

    override suspend fun saveProgress(progress: TaskProgress) {
        if(progress.task.uid != mockedTask.uid)
            throw IllegalArgumentException("No task with ${progress.task.uid} exists")
        progress.finishedRequirements.forEach{
            savedProgress.add(it.uid)
        }
    }
}