package com.edhou.taskmaster.activities


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
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
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val textView = onView(
                allOf(withId(R.id.myTasksHeading), withText("My Tasks"),
                        withParent(allOf(withId(R.id.mainActivity),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()))
        textView.check(matches(withText("My Tasks")))

        val materialButton = onView(
                allOf(withId(R.id.toSettingsButton), withText("Settings"),
                        childAtPosition(
                                allOf(withId(R.id.mainActivity),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()))
        materialButton.perform(click())

        val appCompatEditText = onView(
                allOf(withId(R.id.editUserName),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()))
        appCompatEditText.perform(replaceText("TestName"), closeSoftKeyboard())

        val materialButton2 = onView(
                allOf(withId(R.id.settingsSave), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()))
        materialButton2.perform(click())

        pressBack()

        val textView2 = onView(
                allOf(withId(R.id.myTasksHeading), withText("TestName's Tasks"),
                        withParent(allOf(withId(R.id.mainActivity),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()))
        textView2.check(matches(withText("TestName's Tasks")))

        val materialButton3 = onView(
                allOf(withId(R.id.addTaskLinkButton), withText("Add Task"),
                        childAtPosition(
                                allOf(withId(R.id.mainActivity),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()))
        materialButton3.perform(click())
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
