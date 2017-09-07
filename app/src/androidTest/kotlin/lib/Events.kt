package lib

import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import com.example.avjindersinghsekhon.minimaltodo.R

import org.hamcrest.Matchers.*
import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.util.Log


/**
 * Created by ksadowski on 27.08.2017.
 */

class Events {

    fun findElement(@IdRes viewId: Int){
        onView(withId(viewId)).check(matches(isDisplayed()))
    }

    fun clickOnVisibleViewWithId(@IdRes viewId: Int){
        onView(allOf(withId(viewId),isDisplayed())).perform(click())
    }

    fun clickOnVisibleViewWithText(text: String){
        onView(allOf(withText(text),isDisplayed())).perform(click())
    }

    fun clickOnVisibleViewWithIdAndText(@IdRes viewId: Int, text: String){
        onView(allOf(withText(text),withId(viewId),isDisplayed())).perform(click())
    }

    fun clickOnVisibleViewWithIdAndStringResource(@IdRes viewId: Int, @StringRes stringRes: Int){
        onView(allOf(withText(stringRes),withId(viewId),isDisplayed())).perform(click())
    }

    fun clickOnVisibleElementWithParentIdAndContentDescription(@IdRes parentViewId: Int, description: String){
        onView(
                allOf(withContentDescription(description),
                        withParent(withId(parentViewId)),
                        isDisplayed())).perform(click())
    }

    fun clearViewWithId(@IdRes viewId: Int){
        onView(withId(viewId)).perform(clearText())
    }

    fun typeTextInVisibleViewWithTextAndId(text: String, @IdRes viewId: Int){
        onView(allOf(withId(viewId), isDisplayed())).perform(typeText(text))
    }

    fun swipeLeftVisibleViewWithTextandId(text: String, @IdRes viewId: Int){
        onView(allOf(withId(viewId),isDisplayed(),withText(text))).perform(swipeLeft())
    }

}
