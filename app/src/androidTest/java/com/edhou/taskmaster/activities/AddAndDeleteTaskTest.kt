package com.edhou.taskmaster.activities


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.edhou.taskmaster.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AddAndDeleteTaskTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun addAndDeleteTaskTest() {
        val button = onView(
                allOf(withId(R.id.addTaskLinkButton), withText("ADD TASK"),
                        withParent(allOf(withId(R.id.mainActivity),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()))
        button.check(matches(isDisplayed()))

        val materialButton = onView(
                allOf(withId(R.id.addTaskLinkButton), withText("Add Task"),
                        childAtPosition(
                                allOf(withId(R.id.mainActivity),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()))
        materialButton.perform(click())

        val appCompatEditText = onView(
                allOf(withId(R.id.editTaskName),
                        childAtPosition(
                                allOf(withId(R.id.constraintLayout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()))
        appCompatEditText.perform(replaceText("Test123"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
                allOf(withId(R.id.editTaskDescription),
                        childAtPosition(
                                allOf(withId(R.id.constraintLayout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()))
        appCompatEditText2.perform(replaceText("Test1234"), closeSoftKeyboard())

        val materialButton2 = onView(
                allOf(withId(R.id.addTaskButton), withText("Submit Task"),
                        childAtPosition(
                                allOf(withId(R.id.constraintLayout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()))
        materialButton2.perform(click())

        val textView = onView(
                allOf(withId(R.id.taskName), withText("Test123"),
                        withParent(withParent(withId(R.id.tasksRecyclerView))),
                        isDisplayed()))
        textView.check(matches(withText("Test123")))

        val textView2 = onView(
                allOf(withId(R.id.taskDescription), withText("Test1234"),
                        withParent(withParent(withId(R.id.tasksRecyclerView))),
                        isDisplayed()))
        textView2.check(matches(withText("Test1234")))

        val textView3 = onView(
                allOf(withId(R.id.taskStatus), withText("New task"),
                        withParent(withParent(withId(R.id.tasksRecyclerView))),
                        isDisplayed()))
        textView3.check(matches(withText("New task")))

        val recyclerView = onView(
                allOf(withId(R.id.tasksRecyclerView),
                        childAtPosition(
                                withId(R.id.mainActivity),
                                4)))
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val textView4 = onView(
                allOf(withId(R.id.taskName), withText("Test123"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()))
        textView4.check(matches(withText("Test123")))

        val textView5 = onView(
                allOf(withId(R.id.taskDescription), withText("Test1234"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()))
        textView5.check(matches(withText("Test1234")))

        val button2 = onView(
                allOf(withId(R.id.deleteTaskButton), withText("DELETE TASK"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()))
        button2.check(matches(isDisplayed()))

        val materialButton3 = onView(
                allOf(withId(R.id.deleteTaskButton), withText("Delete task"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()))
        materialButton3.perform(click())

        val recyclerView2 = onView(
                allOf(withId(R.id.tasksRecyclerView),
                        withParent(allOf(withId(R.id.mainActivity),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()))
        recyclerView2.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
