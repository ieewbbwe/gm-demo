package com.sgm.rwutils

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(), MainContract.View<MainContract.Presenter> {
    override lateinit var mPresenter: MainContract.Presenter

    override fun showMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showFileContent(content: String) {
        mContentTv.apply {
            text = content
        }
    }

    private lateinit var mWriteBt: Button
    private lateinit var mReadBt: Button
    private lateinit var mWriteContentEt: EditText
    private lateinit var mContentTv: TextView
    private val REQUEST_CODE_ASK_STORAGE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mWriteBt = findViewById(R.id.m_write_bt)
        mReadBt = findViewById(R.id.m_read_bt)
        mWriteContentEt = findViewById(R.id.m_write_content_et)
        mContentTv = findViewById(R.id.m_content_et)
        mPresenter = MainPresenter(this)

        mWriteBt.setOnClickListener({
            mPresenter.writeToFile(mWriteContentEt.text!!.toString(), mPresenter.getTargetFile())
        })

        mReadBt.setOnClickListener({
            mPresenter.readFromFile(mPresenter.getTargetFile())
        })

        reqPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    }


    private fun reqPermission(vararg pms: String) {
        for (premission in pms) {
            if (ContextCompat.checkSelfPermission(applicationContext, premission) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, premission)) {
                    AlertDialog.Builder(this)
                            .setTitle("提示")
                            .setMessage("需要开启存储的权限，是否继续？")
                            .setPositiveButton("确定") { _, _ ->
                                ActivityCompat.requestPermissions(this@MainActivity,
                                        arrayOf(premission), REQUEST_CODE_ASK_STORAGE)
                            }
                            .setNegativeButton("取消") { dialog, _ -> dialog.dismiss() }.show()
                } else {
                    //请求权限
                    ActivityCompat.requestPermissions(this,
                            arrayOf(premission), REQUEST_CODE_ASK_STORAGE)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_ASK_STORAGE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("picher", "授权同意：$grantResults[0]")
            } else {
                Toast.makeText(this@MainActivity, "拒绝授权！", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
