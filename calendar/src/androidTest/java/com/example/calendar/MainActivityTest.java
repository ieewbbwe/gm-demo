package com.example.calendar;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import static android.provider.CalendarContract.Calendars.ACCOUNT_NAME;
import static android.provider.CalendarContract.Calendars.ACCOUNT_TYPE;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import static android.provider.CalendarContract.CALLER_IS_SYNCADAPTER;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Condition.matched;
import static org.hamcrest.Matchers.startsWith;

/**
 * Created by s2s8tb on 2019/7/15.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Rule
    public ActivityTestRule<MainActivity> mRule = new ActivityTestRule<MainActivity>(MainActivity.class, true, false);

    @Rule
    public ActivityTestRule<JumpActivity> mJumpRule = new ActivityTestRule<JumpActivity>(JumpActivity.class, true, true);

    @Test
    public void testQueryClick() {
        Intent intent = new Intent();
        intent.putExtra("input", "Test");
        mRule.launchActivity(intent);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.m_query_calendar_bt)).perform(click());
    }

    @Test
    public void testWithTest() {
        launchActivity();
        onView(withText(startsWith("QUERY"))).perform(click());
    }

    @Test
    public void testButtonShow() {
        launchActivity();
        onView(withText(startsWith("QUE"))).check(matches(isDisplayed()));
    }

    @Test
    public void launchActivity() {
        Intent intent = new Intent();
        intent.putExtra("input", "Test");
        mRule.launchActivity(intent);
    }

    @Test
    public void testScroll() {
        launchActivity();
        onView(withId(R.id.m_data_rl)).perform(click()).check(matches(isDisplayed()));
    }

    @Test
    public void testClickJump() {
        launchActivity();
        onView(withId(R.id.m_demo_bt)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //onView(withId(R.id.m_receiver_tv)).check(matches(withText("dynamic send intent")));
        //intended(hasComponent(JumpActivity.class.getName()));
        intended(toPackage("com.example.calendar"));
    }

    @Test
    public void testJumpActivity() {
        //intended(toPackage("com.android.phone"));
        //模拟要发送的activity
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, JumpActivity.class);
        intent.putExtra("TAG", "dynamic send intent");

        //启动目标activity
        mJumpRule.launchActivity(intent);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.m_receiver_tv)).check(matches(withText("dynamic send intent")));
    }

    @Test
    public void insertEvent() {
       // launchActivity();
        String accountName = "Phone";
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, 7, 26, 15, 47, 0);
        long startMills = calendar.getTimeInMillis();
        calendar.set(2019, 7, 26, 15, 50, 0);
        long endMills = calendar.getTimeInMillis();

        Uri eventUri = CalendarContract.Events.CONTENT_URI;
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", "代码插入的提醒事件");
        contentValues.put("dtstart", startMills);
        contentValues.put("dtend", endMills);
        contentValues.put("calendar_id", 1);
        contentValues.put("hasAlarm", true);
        contentValues.put("duration", true);

        eventUri = eventUri.buildUpon().
                appendQueryParameter(CALLER_IS_SYNCADAPTER, "true").
                appendQueryParameter(ACCOUNT_NAME, accountName).
                appendQueryParameter(ACCOUNT_TYPE, "com.android.huawei.phone").
                build();

        Uri uri = mRule.getActivity().getContentResolver().insert(eventUri, contentValues);
        if (uri != null) {
            Log.d("picher", "" + uri.getLastPathSegment());
        }

    }

}