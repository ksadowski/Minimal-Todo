package tests

/**
 * Created by ksadowski on 27.08.2017.
 */

import android.util.Log
import com.example.avjindersinghsekhon.minimaltodo.MainActivity
import lib.*
import org.junit.After
import org.junit.Assert
import pages.*
import org.junit.Test
import java.util.Date
import java.text.SimpleDateFormat

class TestSuite : FunctionalTest<MainActivity>(MainActivity::class.java) {

    val onTaskListPage = TaskListPage()
    val onTaskPage = TaskPage()
    val checkThat = Matchers()
    val onCalendarPage = CalendarPage()
    val onSettingsPage = SettingsPage()
    val onTopBarPage = TopBarPage()
    val date = Date()

    val hourFormatter = SimpleDateFormat("h")
    val minutesFormatter = SimpleDateFormat("m")

    val h = hourFormatter.format(date).toInt()
    val m = minutesFormatter.format(date).toInt()



    @Test
    fun test_01_new_task_alarm_08_10_2017_17_43() {
        checkThat.viewIsVisibleAndContainsTextFromStringResource(onTopBarPage.appNameStrRes)

        onTaskListPage.clickAddTaskFloatingButton()
        onTaskPage.setValuesForTaskWithReminder("Test",2017,10,8,17,43)

        checkThat.viewWithIdContainsText(onTaskPage.chosenReminderTimeTextViewId,"Reminder set for 8 Oct, 2017, 17:43 ")

        onTaskPage.clickSubmitTaskButton()

        checkThat.taskAtPositionContainsName(onTaskListPage.taskListRecyclerViewId,1,"Test")
        checkThat.taskAtPositionContainsDate(onTaskListPage.taskListRecyclerViewId,1,"Oct 8, 2017  17:43")

        onTaskListPage.cleanUp(onTaskListPage)
    }

    @Test
    fun test_02_new_task_alarm_10_08_2017_past_time() {
        onTaskListPage.clickAddTaskFloatingButton()
        onTaskPage.enterTaskName("Test")
        onTaskPage.clickReminderSwitch()
        onTaskPage.clickDatePicker()
        onCalendarPage.setDateInCalendar(2017,8,10)
        onCalendarPage.clickOk()
        Thread.sleep(1000)

        checkThat.assertToastWithTextFromStringResourceOnActivity(onTaskPage.rustyMachineToastText,testRule.activity)
        Thread.sleep(1000)

        onTaskPage.clickTimePicker()
        onCalendarPage.setTimeInCalendar(h-1,m)
        onCalendarPage.clickOk()

        checkThat.viewWithIdContainsTextFromStringResource(onTaskPage.chosenReminderTimeTextViewId,onTaskPage.timeInPastResourceId)

    }

    @Test
    fun test_03_change_to_night_mode() {
        val theme1 = onSettingsPage.getTheme()

        onTopBarPage.clickSettingsButton()
        onSettingsPage.clickSettingsOption()
        onSettingsPage.toggleNightModeSwitch()

        checkThat.viewIsVisibleAndContainsTextFromStringResource(onSettingsPage.nightModeOnStringRes)

        val theme2 = onSettingsPage.getTheme()

        Assert.assertNotEquals(theme2,theme1)

    }

    @Test
    fun test_04_multiple_tasks() {
        val names = Array(15, { i -> "Test"+(i+1).toString() })

        names.forEach {
            onTaskListPage.clickAddTaskFloatingButton()
            onTaskPage.enterTaskName(it)
            onTaskPage.clickSubmitTaskButton()
        }

        checkThat.assertRecyclerViewItemsCount(onTaskListPage.taskListRecyclerViewId,15)
        checkThat.taskAtPositionContainsName(onTaskListPage.taskListRecyclerViewId,1,"Test1")
        checkThat.taskAtPositionContainsName(onTaskListPage.taskListRecyclerViewId,15,"Test15")

        onTaskListPage.cleanUp(onTaskListPage)

    }

    @Test
    fun test_05_remove_tasks() {
        onTaskListPage.clickAddTaskFloatingButton()
        onTaskPage.enterTaskName("1st Task on list - to be removed")
        onTaskPage.clickSubmitTaskButton()
        onTaskListPage.clickAddTaskFloatingButton()
        onTaskPage.enterTaskName("2nd Task on list - to be removed as last one")
        onTaskPage.clickSubmitTaskButton()
        onTaskListPage.clickAddTaskFloatingButton()
        onTaskPage.enterTaskName("3rd Task on list - to be removed")
        onTaskPage.clickSubmitTaskButton()
        onTaskListPage.clickAddTaskFloatingButton()
        onTaskPage.enterTaskName("4th Task on list - to be removed")
        onTaskPage.clickSubmitTaskButton()

        checkThat.assertRecyclerViewItemsCount(onTaskListPage.taskListRecyclerViewId,4)

        onTaskListPage.removeTaskWithText("1st Task on list - to be removed")

        checkThat.assertRecyclerViewItemsCount(onTaskListPage.taskListRecyclerViewId,3)
        checkThat.taskAtPositionContainsName(onTaskListPage.taskListRecyclerViewId,1,"2nd Task on list - to be removed as last one")
        Thread.sleep(1000)

        onTaskListPage.removeTaskWithText("3rd Task on list - to be removed")

        checkThat.assertRecyclerViewItemsCount(onTaskListPage.taskListRecyclerViewId,2)
        checkThat.taskAtPositionContainsName(onTaskListPage.taskListRecyclerViewId,1,"2nd Task on list - to be removed as last one")
        checkThat.taskAtPositionContainsName(onTaskListPage.taskListRecyclerViewId,2,"4th Task on list - to be removed")
        Thread.sleep(1000)

        onTaskListPage.removeTaskWithText("4th Task on list - to be removed")

        checkThat.assertRecyclerViewItemsCount(onTaskListPage.taskListRecyclerViewId,1)
        checkThat.taskAtPositionContainsName(onTaskListPage.taskListRecyclerViewId,1,"2nd Task on list - to be removed as last one")
        Thread.sleep(1000)

        onTaskListPage.removeTaskWithText("2nd Task on list - to be removed as last one")
        Thread.sleep(1000)

        checkThat.viewIsVisibleAndContainsTextFromStringResource(onTaskListPage.emptyTaskListTextRes)
    }

    @Test
    fun test_06_add_task_with_empty_name() {
        onTaskListPage.clickAddTaskFloatingButton()
        onTaskPage.enterTaskName("")
        onTaskPage.clickSubmitTaskButton()

        checkThat.assertErrorTextOnViewWithId(onTaskPage.taskNameEditTextId,"Please enter a Todo")
    }

    @Test
    fun test_07_edit_task_name() {
        onTaskListPage.clickAddTaskFloatingButton()
        onTaskPage.enterTaskName("Test Edit")
        onTaskPage.clickSubmitTaskButton()
        onTaskListPage.clickTaskAtPositionWithName(1,"Test Edit")
        onTaskPage.editTaskName("New Name")
        onTaskPage.clickSubmitTaskButton()

        checkThat.taskAtPositionContainsName(onTaskListPage.taskListRecyclerViewId,1,"New Name")

        onTaskListPage.cleanUp(onTaskListPage)
    }
}