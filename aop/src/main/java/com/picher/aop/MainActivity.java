package com.picher.aop;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private TextView mContenttv;
    private Button mContentBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContenttv = findViewById(R.id.m_content_tv);
        mContentBt = findViewById(R.id.m_content_bt);

        mContentBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentText("changed");
                //testAnnotation();
                writeFile();
            }
        });
    }

    private void writeFile() {
        if (isSdCardExist()) {
            //File target = new File(path, "list.txt");
            File target = new File(Environment.getExternalStorageDirectory(), "iotest");
            Log.d("picher", target.getPath());
            if (!target.exists()) {
                target.mkdirs();
            }
            File saveFile = new File(target, "list.txt");
            Log.d("picher", saveFile.getPath());
            if (!saveFile.exists()) {
                try {
                    if (saveFile.createNewFile()) {
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveFile), "UTF8"));
                        writer.write("Picher");
                        writer.flush();
                        writer.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //SD是否存在
    private boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public void setContentText(String str) {
        mContenttv.setText(str);
        try {
            throw new IllegalArgumentException("exception test!!");
        } catch (Exception e) {

        }
    }


    @TestAnnotation(type = 2, value = "picher")
    public void testAnnotation() {
        Log.d("picher", "testAnnotation");
    }
}
