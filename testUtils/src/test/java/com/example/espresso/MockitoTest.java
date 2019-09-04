package com.example.espresso;

import android.util.Log;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Calendar;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by s2s8tb on 2019/8/13.
 */

@RunWith(MockitoJUnitRunner.class)
public class MockitoTest {

    @Test
    public void testList() {
        List list = mock(List.class);
        when(list.get(1)).thenReturn(1);
        Assert.assertEquals(list.get(1),1);
        verify(list).get(1);
    }

    @Test
    public void calendar(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019,12,31);
        System.out.println(calendar.get(Calendar.MONTH));
        calendar.add(Calendar.MONTH, -1);
        System.out.println(calendar.get(Calendar.MONTH));
    }
}
