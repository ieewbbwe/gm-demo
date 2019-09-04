package com.example.calendar;

import android.util.Log;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void testGetDate(){
        int date = Calendar.getInstance().get(Calendar.DATE);
        assertEquals(date,26);
        //System.console().printf("date:"+date);
        //Log.d("picher",""+date);
    }

    @Test
    public void testUtil(){
        Assert.assertSame(DateUtil.add(1,2),3);
    }

    @Test
    public void logTest(){
        DateUtil.log();
    }

}