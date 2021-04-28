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

class MockTasksRepo2 : TasksRepo {
    companion object {
        private val dateFormat = SimpleDateFormat("M-d-y")
        private var uid = 10
            get() {
                field += 1
                return field
            }
    }

    private val mockedTask1 = Task(
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
                ImageBlock(uid.toString(), "https://www.digitalhrtech.com/wp-content/uploads/2020/01/Learning-and-development-manager.png"),
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
                        ), 2)
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
                    0)
            ))
        ), dateFormat.parse("4-25-2021")!!, "Learn about the Test Bus", 10, 1, listOf(
            RubricRequirement("Read about the Test Bus", false, 20, uid),
            RubricRequirement("Answer multiple choice questions", false, 20, uid),
            RubricRequirement("Complete the summary task", false, 20, uid)
        ))

    private val mockedTask2 = Task(
        listOf(
            Page(listOf(
                TextBlock("What is a Test Driven Development?", 36),
                TextBlock("""
                    Test Driven Development (TDD) is both an analysis and design technique. 
                    It is when you write the test cases for code before writing the code 
                    itself. It helps us decide what we are programming and what we aren't 
                    programming. Picking what's in scope and, more importantly what's out 
                    of scope is critical to software development.
                """.trimIndent(), 18),
                TextBlock("Look at This!", 36),
                ImageBlock(uid.toString(), "https://marsner.com/wp-content/uploads/test-driven-development-TDD.png"),
                VideoBlock("https://www.youtube.com/watch?v=uGaNkTahrIw", "Watch this video!"),
                TextBlock("Check your knowledge", 24),
                QuizBlock(
                    listOf(
                        MultipleChoiceQuestion(
                            listOf(
                                MultipleChoiceAnswerOption("4", uid),
                                MultipleChoiceAnswerOption("5", uid),
                                MultipleChoiceAnswerOption("3", uid)
                            ),"There are ___ stages of TDD.", 2, uid),
                        MultipleChoiceQuestion(
                            listOf(
                                MultipleChoiceAnswerOption("Allows for repeatable tests", uid),
                                MultipleChoiceAnswerOption("Enables rapid change", uid),
                                MultipleChoiceAnswerOption("Provides documentation for different team members", uid),
                                MultipleChoiceAnswerOption("It is faster then coding without TDD", uid),
                                MultipleChoiceAnswerOption("Keeps code clear, simple, and testable", uid)
                            ), "Which of the following is NOT a benefit of TDD?", 2, uid)
                    ), 2)
            )), Page(listOf(
                TextBlock("Better Design", 36),
                TextBlock("""
                    We saw how test-first drives design decisions. Not only do the decisions come 
                    at a different time, the decisions themselves are different. Test-first code 
                    tends to be more cohesive and less coupled than code in which testing isn’t 
                    part of the intimate coding cycle. Some people worry that they can’t even get 
                    their code done, and now they have to test, too. You don’t have to be a great 
                    designer—you just have to be creatively lazy. 
                """.trimIndent(), 18),
                TextBlock("Show your understanding", 36),
                QuizBlock(
                    listOf(
                        FreeResponseQuestion("""
                            In your own words, summarize
                            what is TDD and why it is useful.
                        """.trimIndent(), 6, uid)),
                    0)
            ))
        ), dateFormat.parse("4-29-2021")!!, "What is TDD?", 10, 2, listOf(
            RubricRequirement("Read about TDD", false, 20, uid),
            RubricRequirement("Watch TDD video", false, 20, uid),
            RubricRequirement("Answer multiple choice questions", false, 20, uid),
            RubricRequirement("Complete free-response question", false, 20, uid)
        ))

    private var savedProgress: MutableSet<Int> = mutableSetOf()
    private var savedQuestionAnswers: MutableMap<Int, StudentAnswerInput> = mutableMapOf()

    override suspend fun getTask(taskId: Int) : Task {
        if(taskId == mockedTask1.uid) {
            return Task (mockedTask1.pages,
                mockedTask1.dueDate,
                mockedTask1.title,
                mockedTask1.points,
                mockedTask1.uid,
                mockedTask1.requirements.map {
                    it.completed
                })
        }
        else if(taskId == mockedTask2.uid) {
            return Task (mockedTask2.pages,
                mockedTask2.dueDate,
                mockedTask2.title,
                mockedTask2.points,
                mockedTask2.uid,
                mockedTask2.requirements.map {
                    it.completed
                })
        }
        else
            throw IllegalArgumentException("No task with $taskId exists")
    }

    override suspend fun saveProgress(progress: TaskProgress) {
        if(progress.task.uid != mockedTask1.uid && progress.task.uid != mockedTask2.uid)
            throw IllegalArgumentException("No task with ${progress.task.uid} exists")
        progress.finishedRequirements.forEach{
            savedProgress.add(it.uid)
        }
        progress.answeredQuestions.forEach {
            savedQuestionAnswers[it.questionId] = it
        }
    }

    override suspend fun submitTask(taskId : String) : TaskSubmissionResult {
        return TaskSubmissionResult(taskId, true, 8)
    }


}