package pages

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.matcher.ViewMatchers
import com.example.avjindersinghsekhon.minimaltodo.R
import lib.Events
import lib.Matchers
import org.hamcrest.Matchers.*
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.espresso.Espresso.onView


/**
 * Created by ksadowski on 06.09.2017.
 */
class TopBarPage {

    val appNameStrRes = R.string.app_name

    fun clickSettingsButton(){
        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext())
    }


}