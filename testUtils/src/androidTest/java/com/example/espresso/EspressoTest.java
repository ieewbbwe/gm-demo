package com.example.espresso;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

/**
 * Created by s2s8tb on 2019/8/13.
 */
@RunWith(AndroidJUnit4.class)
public class EspressoTest {

//    @Before
//    public void setUp() {
//
//    }
//
//    @After
//    public void tearDown() {
//
//    }

//    @Rule
//    public ActivityTestRule<MainActivity> mRule
//            = new ActivityTestRule<>(MainActivity.class, true, true);

    @Rule
    public ActivityTestRule<JumpActivity> mRule
            = new ActivityTestRule<>(JumpActivity.class, true, true);

    @Test
    public void setText() throws InterruptedException {
//        //Observable.timer(2, TimeUnit.MINUTES);
//        System.out.printf(Thread.currentThread().getName());
//        Log.d("picher", Thread.currentThread().getName());
//        Observable.create(e -> {
//            mRule.getActivity().setTest("cecece");
//            e.onNext(1);
//        })
////                .delay(1000, TimeUnit.MILLISECONDS)
////                .map(o -> {
////                    Log.d("picher","delay 2");
////                    return o;
////                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(AndroidSchedulers.mainThread())//.subscribe();
//                .subscribe();
//        //o -> onView(allOf(withText("cecece"),instanceOf(TextView.class), withId(R.id.m_content_tv))).check(matches(isDisplayed()))
//        Thread.sleep(2000);
//        onView(allOf(withText("cecece"),instanceOf(TextView.class),withId(R.id.m_content_tv))).check(matches(isDisplayed()));
        System.out.print("picher System out");
        Thread.sleep(5000);
    }
}
