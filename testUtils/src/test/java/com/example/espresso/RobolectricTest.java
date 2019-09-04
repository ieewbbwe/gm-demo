package com.example.espresso;

import android.content.Intent;
import android.os.Build;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

/**
 * Created by s2s8tb on 2019/8/13.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.M,
        application = MyApplication.class,
        manifest = "../main/AndroidManifest.xml",
        shadows = {ShadowLog.class}
)

public class RobolectricTest {

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
    }

    public void testJumpActivity() {
//        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);
//        mainActivity.findViewById(R.id.m_click_bt).performClick();
//        Intent expertIntent = new Intent(mainActivity, JumpActivity.class);
//        Intent actualIntent = Shadows.shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
//        Assert.assertEquals(expertIntent.getComponent(), actualIntent.getComponent());
    }
}
