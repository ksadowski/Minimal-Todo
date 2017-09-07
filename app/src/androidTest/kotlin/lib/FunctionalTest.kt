package lib

import android.app.Activity
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.runner.RunWith

/**
 * Created by ksadowski on 27.08.2017.
 */
@RunWith(AndroidJUnit4::class)
abstract class FunctionalTest < T: Activity> (clazz: Class <T> ) {

    @Rule @JvmField
    val testRule: ActivityTestRule<T> = IntentsTestRule(clazz)

    val check: Matchers = Matchers()
    val events: Events = Events()

}


