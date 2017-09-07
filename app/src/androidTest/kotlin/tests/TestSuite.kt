package tests

/**
 * Created by ksadowski on 27.08.2017.
 */

import android.util.Log
import com.example.avjindersinghsekhon.minimaltodo.MainActivity
import lib.*
import org.junit.Assert
import pages.*
import org.junit.Test

class TestSuite : FunctionalTest<MainActivity>(MainActivity::class.java) {

    val onTaskListPage = TaskListPage()
    val onTaskPage = TaskPage()
    val checkThat = Matchers()
    val onCalendarPage = CalendarPage()
    val onSettingsPage = SettingsPage()
    val onTopBarPage = TopBarPage()
    val e = Events()

    @Test
    fun test_01_new_task_alarm_08_10_2017_17_43() {
        checkThat.viewIsVisibleAndContainsTextFromStringResource(onTopBarPage.appNameStrRes)
        onTaskListPage.clickAddTaskFloatingButton()
        onTaskPage.setValuesForTaskWithReminder("Test",2017,10,8,17,43)
        checkThat.viewWithIdContainsText(onTaskPage.chosenReminderTimeTextViewId,"Reminder set for 8 Oct, 2017, 17:43 ")
        onTaskPage.clickSubmitTaskButton()
        checkThat.taskAtPositionContainsName(onTaskListPage.taskListRecyclerViewId,1,"Test")
        checkThat.taskAtPositionContainsDate(onTaskListPage.taskListRecyclerViewId,1,"Oct 8, 2017  17:43")
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
        onCalendarPage.setTimeInCalendar(13,0)
        onCalendarPage.clickOk()
        checkThat.viewWithIdContainsTextFromStringResource(onTaskPage.chosenReminderTimeTextViewId,onTaskPage.timeInPastResourceId)

    }

    @Test
    fun test_03_change_to_night_mode() {
        val theme1 = onSettingsPage.getTheme()
        Log.e("Test",theme1.toString())
        onTopBarPage.clickSettingsButton()
        onSettingsPage.clickSettingsOption()
        onSettingsPage.toggleNightModeSwitch()
        checkThat.viewIsVisibleAndContainsTextFromStringResource(onSettingsPage.nightModeOnStringRes)
        val theme2 = onSettingsPage.getTheme()
        Assert.assertNotEquals(theme2,theme1)
    }

    @Test
    fun test_04_multiple_tasks() {
        val names = Array(15, { i -> "Test"+i.toString() })
        names.forEach {
            onTaskListPage.clickAddTaskFloatingButton()
            onTaskPage.enterTaskName(it)
            onTaskPage.clickSubmitTaskButton()
        }
        checkThat.assertRecyclerViewItemsCount(onTaskListPage.taskListRecyclerViewId,15)
        checkThat.taskAtPositionContainsName(onTaskListPage.taskListRecyclerViewId,1,"Test0")
        checkThat.taskAtPositionContainsName(onTaskListPage.taskListRecyclerViewId,15,"Test14")
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

}