package com.example.espresso;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Calendar;

/**
 * Created by s2s8tb on 2019/8/13.
 */

@RunWith(JUnit4.class)
public class JunitTest {

    @Test
    public void testSub() {
        Assert.assertSame(StaticUtils.sub(1, 2), 3);
    }

    @Test
    public void testDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        Assert.assertEquals(calendar.get(Calendar.YEAR), 2019);
    }
}
