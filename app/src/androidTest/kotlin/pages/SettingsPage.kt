package pages

import android.app.Activity
import android.content.res.Resources
import android.support.test.InstrumentationRegistry
import com.example.avjindersinghsekhon.minimaltodo.R
import lib.Events
import lib.Matchers
import org.hamcrest.Matchers.*
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.espresso.Espresso.onView
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import android.support.test.runner.lifecycle.Stage

/**
 * Created by ksadowski on 06.09.2017.
 */

class SettingsPage {

    val settingsMenuItemId = R.id.title
    val settingsMenuItemTextStringRes = R.string.action_settings
    val widget_frame = android.R.id.widget_frame
    val nightModeOnStringRes = R.string.night_mode_on_summary
    val nightModeOffStringRes = R.string.night_mode_off_summary

    val e = Events()
    val m = Matchers()

    fun clickSettingsOption(){
        e.clickOnVisibleViewWithIdAndStringResource(settingsMenuItemId,settingsMenuItemTextStringRes)
    }

    fun toggleNightModeSwitch(){
        onView(allOf(((m.childAtPosition(withId(widget_frame),0))), isDisplayed())).perform(click())
    }

    fun getTheme(): Resources.Theme {
        val currentActivity = arrayOfNulls<Activity>(1)
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            val resumedActivity = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
            val it = resumedActivity.iterator()
            currentActivity[0] = it.next()
        }
        return currentActivity[0]!!.theme
    }
}