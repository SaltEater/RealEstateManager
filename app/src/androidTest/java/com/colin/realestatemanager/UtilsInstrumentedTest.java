package com.colin.realestatemanager;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.colin.realestatemanager.utils.Utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class UtilsInstrumentedTest {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.colin.realestatemanager", appContext.getPackageName());
    }
    @Test
    public void wifi_network_isCorrect() {
        assertTrue(Utils.isInternetAvailable2(InstrumentationRegistry.getInstrumentation().getTargetContext()));
    }
}
