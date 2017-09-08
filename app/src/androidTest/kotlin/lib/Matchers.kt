package lib

import android.app.Activity
import android.support.annotation.IdRes
import android.support.annotation.StringRes

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.espresso.contrib.RecyclerViewActions.*
import android.support.v7.widget.RecyclerView
import com.forkingcode.espresso.contrib.DescendantViewActions
import android.view.View
import android.view.ViewGroup
import com.example.avjindersinghsekhon.minimaltodo.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import android.support.design.widget.TextInputLayout
import android.support.test.espresso.matcher.RootMatchers
import android.widget.EditText
import org.hamcrest.CoreMatchers


/**
 * Created by ksadowski on 27.08.2017.
 */
class Matchers {

    fun childAtPosition(parentMatcher: Matcher<View>, position: Int): Matcher<View> {

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

    fun hasTextInputLayoutErrorText(expectedErrorText: String): Matcher<View> {

        return object : TypeSafeMatcher<View>() {

            override fun matchesSafely(view: View) : Boolean {
                if (!(view is EditText)) {
                    return false;
                }

                val error = (view as EditText).getError();

                if (error == null) {
                    return false;
                }

                val hint = error.toString();
                return expectedErrorText.equals(hint);
            }

            override fun describeTo(description: Description) {
            }
        }
    }

    fun <T: Activity> nextOpenActivityIs(clazz: Class <T>) {
        intended(IntentMatchers.hasComponent(clazz.name))
    }

    fun viewIsVisibleAndContainsText(text: String) {
        onView(withText(text)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    fun viewIsVisibleAndContainsTextFromStringResource(@StringRes stringRes: Int) {
        onView(withText(stringRes)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    fun viewWithIdContainsText(@IdRes viewId: Int, text: String) {
        onView(allOf(withId(viewId), isDisplayed())).check(matches(withText(text)))
    }

    fun viewWithIdContainsTextFromStringResource(@IdRes viewId: Int, @StringRes stringRes: Int) {
        onView(allOf(withId(viewId), isDisplayed())).check(matches(withText(stringRes)))
    }

    fun taskAtPositionContainsDate(@IdRes recyclerViewId: Int, position: Int, date: String, @IdRes dependantViewId: Int = R.id.todoListItemTimeTextView) {
        onView(withId(recyclerViewId)).perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(position-1,
                        DescendantViewActions.checkDescendantViewAction(
                                withId(dependantViewId), matches(withText(date))))
        )
    }

    fun taskAtPositionContainsName(@IdRes recyclerViewId: Int, position: Int, name: String, @IdRes dependantViewId: Int = R.id.toDoListItemTextview) {
        onView(withId(recyclerViewId)).perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(position-1,
                        DescendantViewActions.checkDescendantViewAction(
                                withId(dependantViewId), matches(withText(name))))
        )
    }

    fun assertRecyclerViewItemsCount(@IdRes recyclerViewId: Int, expectedValue: Int) {
        onView(withId(recyclerViewId)).check(RecyclerViewItemCountAssertion(expectedValue))
    }

    fun assertToastWithTextFromStringResourceOnActivity(text: String, activity: Activity) {
        onView(withText(text)).inRoot(RootMatchers.withDecorView(CoreMatchers.not((activity.getWindow().getDecorView())))).check(matches(isDisplayed()))
    }

    fun assertErrorTextOnViewWithId(@IdRes viewId: Int, expectedString: String) {
        onView(allOf(withId(viewId), isDisplayed())).check(matches(hasTextInputLayoutErrorText(expectedString)))
    }



}