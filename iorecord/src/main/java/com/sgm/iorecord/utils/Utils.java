package com.sgm.iorecord.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by s2s8tb on 2019/10/8.
 */

public class Utils {

    private static final String TAG = "picher";


    public static void copyFileFromAssets(Context context, String assetsFilePath, String targetFileFullPath) {
        if (isExists(assetsFilePath, targetFileFullPath)) {
            Log.d(TAG, assetsFilePath + "文件已经存在！");
            return;
        }
        Log.d(TAG, "file: " + assetsFilePath);
        Log.d(TAG, "copyFileFrom:" + targetFileFullPath);
        InputStream assestsFileImputStream;
        try {
            assestsFileImputStream = context.getAssets().open(assetsFilePath);
            copyFile(assestsFileImputStream, targetFileFullPath);
        } catch (IOException e) {
            Log.e(TAG, "copyFileFromAssets " + "IOException-" + e.getMessage());
            e.printStackTrace();
        }

    }

    private static boolean isExists(String assetsFilePath, String targetFileFullPath) {
        String[] strs = assetsFilePath.split("/");
        String fileName = targetFileFullPath + File.separator + strs[strs.length - 1];
        File f = new File(fileName);
        return f.exists();
    }

    private static void copyFile(InputStream in, String targetPath) {
        try {
            File f = new File(targetPath);
            if (!f.exists()) {
                f.mkdirs();
            }

            FileOutputStream fos = new FileOutputStream(new File(targetPath, "iotop.sh"));
            byte[] buffer = new byte[1024];
            int byteCount = 0;
            while ((byteCount = in.read(buffer)) != -1) {// 循环从输入流读取
                // buffer字节
                fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
            }
            fos.flush();// 刷新缓冲区
            in.close();
            fos.close();
            Log.d("picher", "fileCopySucceed!!");
        } catch (Exception e) {
            Log.e(TAG, "copyFile " + "IOException-" + e.getMessage());
            e.printStackTrace();
        }
    }

    public static String executeShell(String shell){
        Runtime mRuntime = Runtime.getRuntime();
        try {
            //Process中封装了返回的结果和执行错误的结果
            Process mProcess = mRuntime.exec(shell);
            BufferedReader mReader = new BufferedReader(new InputStreamReader(mProcess.getInputStream()));
            StringBuffer mRespBuff = new StringBuffer();
            char[] buff = new char[1024];
            int ch = 0;
            while ((ch = mReader.read(buff)) != -1) {
                mRespBuff.append(buff, 0, ch);
            }
            mReader.close();
            System.out.print("result1" + mRespBuff.toString());
            return mRespBuff.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
