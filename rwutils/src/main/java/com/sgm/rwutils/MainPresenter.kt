package com.sgm.rwutils

import android.os.Environment
import android.util.Log
import java.io.*


/**
 * Created by picher on 2019/9/4.
 * Describe：
 */

class MainPresenter(private val mView: MainContract.View<MainContract.Presenter>) : MainContract.Presenter {

    private var mFile: File

    init {
        mView.mPresenter = this
        mFile = File(Environment.getExternalStorageDirectory(), "Download")
    }

    override fun getTargetFile(): File {
        return mFile
    }

    override fun writeToFile(str: String, f: File) {
        try {
            if (!f.exists()) {
                f.mkdirs()
            }
            val target = File(f, "list.txt")
            if (!target.exists()) {
                target.createNewFile()
            }
            val accFile = RandomAccessFile(target, "rwd")
            accFile.seek(accFile.length())
            val barr = str.toByteArray(Charsets.UTF_8)
            accFile.write(barr)
            accFile.close()
            mView.showMsg("写入：$str\n字符大小：${barr.size} B")
            Log.d("picher", "字符大小：${barr.size} B")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun readFromFile(f: File) {
        var content = ""
        val target = File(f, "list.txt")
        if (target.exists()) {
            val instream = FileInputStream(target)
            val inputreader = InputStreamReader(instream, "UTF-8")
            val buffreader = BufferedReader(inputreader)
            //buffreader.readText()
            try {
                var line: String? = ""
                //分行读取
                while ({ line = buffreader.readLine();line }() != null) {
                    content += line + "\n"
                }
                mView.showFileContent(content)
                mView.showMsg("size:${content.toByteArray(Charsets.UTF_8).size} B")
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                inputreader.close()
                buffreader.close()
            }
        } else {
            mView.showMsg("未找到文件！")
        }

    }

}
