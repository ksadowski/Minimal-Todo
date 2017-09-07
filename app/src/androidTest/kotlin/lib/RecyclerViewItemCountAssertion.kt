package lib

import android.support.v7.widget.RecyclerView
import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.ViewAssertion
import android.util.Log
import android.view.View
import org.junit.Assert.*


/**
 * Created by ksadowski on 06.09.2017.
 */
class RecyclerViewItemCountAssertion(val expectedCount: Int) : ViewAssertion {

    override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val recyclerView = view as RecyclerView
        val adapter = recyclerView.getAdapter()
        assertEquals(expectedCount,adapter.itemCount)
    }
}