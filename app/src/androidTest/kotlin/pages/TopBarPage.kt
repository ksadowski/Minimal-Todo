package pages

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import com.example.avjindersinghsekhon.minimaltodo.R


/**
 * Created by ksadowski on 06.09.2017.
 */

class TopBarPage {

    val appNameStrRes = R.string.app_name

    fun clickSettingsButton() {
        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext())
    }

}