package pages

import com.example.avjindersinghsekhon.minimaltodo.R
import com.example.avjindersinghsekhon.minimaltodo.AddToDoActivity

import lib.*

import android.app.Activity
import android.view.View

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.runner.lifecycle.Stage
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import android.support.test.InstrumentationRegistry.getInstrumentation

import com.wdullaer.materialdatetimepicker.date.DayPickerView
import com.wdullaer.materialdatetimepicker.date.DatePickerController
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog

import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

/**
 * Created by ksadowski on 06.09.2017.
 */
class CalendarPage {

    val e = Events()

    val datePickerYearId = R.id.date_picker_year
    val datePickerMonthId = R.id.date_picker_month
    val datePickerDayId = R.id.date_picker_day
    val okButtonId = R.id.ok
    val cancelButtonId = R.id.cancel
    val animatorId = R.id.animator
    val timePickerId = R.id.time_picker

    fun clickOk() {
        e.clickOnVisibleViewWithId(okButtonId)
    }

    fun clickCancel() {
        e.clickOnVisibleViewWithId(cancelButtonId)
    }

    fun setDate(year: Int, month: Int, day: Int, activity: Activity): ViewAction {
        return object : ViewAction {
            override fun perform(uiController: UiController, view: View) {
                val fragment = (activity as AddToDoActivity).fragmentManager.findFragmentByTag("DateFragment")
                (fragment as DatePickerController).onDayOfMonthSelected(year, month, day)
            }

            override fun getDescription(): String = "Set Date"

            override fun getConstraints(): Matcher<View> =
                    allOf(isAssignableFrom(DayPickerView::class.java), isDisplayed())

        }
    }


    fun setTime(hh: Int, mm: Int, activity: Activity): ViewAction {
        return object : ViewAction {
            override fun perform(uiController: UiController, view: View) {
                val fragment = view as RadialPickerLayout
                fragment.setTime(hh, 0)
                fragment.setCurrentItemShowing(TimePickerDialog.HOUR_INDEX, true)
                fragment.setTime(hh, mm)
                fragment.setCurrentItemShowing(TimePickerDialog.MINUTE_INDEX, true)

            }

            override fun getDescription(): String = "Set Time"

            override fun getConstraints(): Matcher<View> = allOf(isDisplayed())
        }
    }

    fun setDateInCalendar(year: Int, month: Int, day: Int) {

        val currentActivity = arrayOfNulls<Activity>(1)
        getInstrumentation().runOnMainSync {
            val resumedActivity = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
            val it = resumedActivity.iterator()
            currentActivity[0] = it.next()
        }
        onView(allOf(withParent(withId(animatorId)), isDisplayed())).perform(setDate(year, month - 1, day, currentActivity[0]!!))
    }

    fun setTimeInCalendar(hh: Int, mm: Int) {

        val currentActivity = arrayOfNulls<Activity>(1)
        getInstrumentation().runOnMainSync {
            val resumedActivity = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
            val it = resumedActivity.iterator()
            currentActivity[0] = it.next()
        }
        onView(allOf(withId(timePickerId), isDisplayed())).perform(setTime(hh, mm, currentActivity[0]!!))
    }

}