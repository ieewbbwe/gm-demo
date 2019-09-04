package com.example.espresso;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mClickBt;
    private TextView mContentTv;
    private EditText mInputEt;
    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mClickBt = (Button) findViewById(R.id.m_click_bt);
        mContentTv = (TextView) findViewById(R.id.m_content_tv);
        mInputEt = (EditText) findViewById(R.id.m_input_et);

        mClickBt.setOnClickListener(this);

        Log.d("picher", "mainCreate");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.m_click_bt:
                startActivity(new Intent(MainActivity.this, JumpActivity.class));
                break;
        }
    }

    public void setTest(String str) {
        mContentTv.setText(str);
    }
}
