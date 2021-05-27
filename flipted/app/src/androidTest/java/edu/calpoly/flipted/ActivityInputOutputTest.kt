package edu.calpoly.flipted

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import edu.calpoly.flipted.ui.MainActivity
import edu.calpoly.flipted.ui.goals.GoalsFragment
import org.hamcrest.CoreMatchers.allOf
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)

class ActivityInputOutputTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.flipteded", appContext.packageName)
    }

    @Test
    fun activityLaunch() {
        //onView(withId(R.id.navbar)).perform(click())
        onView(withText("Home")).perform(click())
        onView(withText("My Progress")).perform(click())
        //onView(withText( R.id.video_button)).perform(click())

        //assertEquals(StudentHomeFragment,)
    }

    @Test fun testGoalsFragment() {
        val scenario = launchFragmentInContainer<GoalsFragment>()
        onView(allOf(withId(R.id.newGoalButton), withText("Add New Goal")))
    }
}
