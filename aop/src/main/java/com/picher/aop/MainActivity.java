package com.picher.aop;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * AOP Demo
 * 以文件IO为例
 */
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ASK_STORAGE = 001;
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

    @Override
    protected void onResume() {
        super.onResume();
        int check = ContextCompat.checkSelfPermission(this, Manifest.permission_group.STORAGE);
        if (check != PackageManager.PERMISSION_GRANTED) {
            //requestPermission();
        }
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission_group.STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("需要开启存储的权限，是否继续？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission_group.STORAGE}, REQUEST_CODE_ASK_STORAGE);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else {
            //请求权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_ASK_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("picher", "strings:" + permissions + "->>results:" + grantResults);
        switch (requestCode) {
            case REQUEST_CODE_ASK_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("picher", "授权同意!");
                } else {
                    Toast.makeText(MainActivity.this, "拒绝授权！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
