package com.example.calendar.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.calendar.R;

/**
 * Created by sipzqv on 2019/7/19.
 */

public class otherActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
      setContentView(R.layout.layout1);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
