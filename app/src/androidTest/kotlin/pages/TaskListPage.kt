package pages

import android.app.Activity
import android.support.annotation.IdRes
import com.example.avjindersinghsekhon.minimaltodo.R
import android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import android.support.test.InstrumentationRegistry.getInstrumentation;
import android.support.test.espresso.Espresso
import lib.*
import android.support.test.espresso.contrib.RecyclerViewActions.*
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.espresso.Espresso.onView
import android.support.v7.widget.RecyclerView
import com.forkingcode.espresso.contrib.DescendantViewActions
import org.hamcrest.Matchers.allOf


/**
 * Created by ksadowski on 27.08.2017.
 */
class TaskListPage {

    val e = Events()

    val addTaskFloatingButtonId = R.id.addToDoItemFAB
    val taskNameTextViewId = R.id.toDoListItemTextview
    val taskReminderTextViewId = R.id.todoListItemTimeTextView
    val taskListRecyclerViewId = R.id.toDoRecyclerView
    val emptyTaskListTextRes = R.string.no_to_dos


    fun clickAddTaskFloatingButton(){
        e.clickOnVisibleViewWithId(addTaskFloatingButtonId)
    }


    fun removeTaskWithText(text: String){
        e.swipeLeftVisibleViewWithTextandId(text, taskNameTextViewId)
    }

    fun clickTaskAtPositionWithName(position: Int, text: String) {
        onView(withId(taskListRecyclerViewId)).perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(position-1,
                        DescendantViewActions.performDescendantAction(allOf(withId(taskNameTextViewId),withText(text)), click()))
        )
        Espresso.closeSoftKeyboard()
    }

    fun clickTaskAtPosition(position: Int) {
        onView(withId(taskListRecyclerViewId)).perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(position-1,
                        DescendantViewActions.performDescendantAction(allOf(withId(taskNameTextViewId)), click()))
        )
        Espresso.closeSoftKeyboard()
    }

}