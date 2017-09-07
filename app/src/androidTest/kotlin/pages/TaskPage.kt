package pages

import com.example.avjindersinghsekhon.minimaltodo.R
import lib.Events

/**
 * Created by ksadowski on 27.08.2017.
 */
class TaskPage {

    val e = Events()
    val onTaskListPage = TaskListPage()
    val onCalendarPage = CalendarPage()

    val taskNameEditTextId = R.id.userToDoEditText
    val saveTaskButtonId = R.id.makeToDoFloatingActionButton
    val remindMeSwitchId = R.id.toDoHasDateSwitchCompat
    val dateEditTextId = R.id.newTodoDateEditText
    val timeEditTextId = R.id.newTodoTimeEditText
    val chosenReminderTimeTextViewId = R.id.newToDoDateTimeReminderTextView
    val toolbarId = R.id.toolbar
    val rustyMachineToastText = "My time-machine is a bit rusty"
    val timeInPastResourceId = R.string.date_error_check_again

    fun clickCloseButton(){
        e.clickOnVisibleElementWithParentIdAndContentDescription(toolbarId,"Navigate up")
    }

    fun clickSubmitTaskButton(){
        e.clickOnVisibleViewWithId(saveTaskButtonId)
    }

    fun enterTaskName(text: String){
        e.typeTextInVisibleViewWithTextAndId(text, taskNameEditTextId)
    }

    fun clickReminderSwitch(){
        e.clickOnVisibleViewWithId(remindMeSwitchId)
    }

    fun clickDatePicker(){
        e.clickOnVisibleViewWithId(dateEditTextId)
    }

    fun clickTimePicker(){
        e.clickOnVisibleViewWithId(timeEditTextId)
    }

    fun setValuesForTaskWithReminder(text: String, year: Int, month: Int, day: Int, hh: Int, mm: Int){
        enterTaskName(text)
        clickReminderSwitch()
        clickDatePicker()
        onCalendarPage.setDateInCalendar(year,month,day)
        onCalendarPage.clickOk()
        clickTimePicker()
        onCalendarPage.setTimeInCalendar(hh,mm)
        onCalendarPage.clickOk()
    }




}