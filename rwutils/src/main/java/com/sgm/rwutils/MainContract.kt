package com.sgm.rwutils

import java.io.File

/**
 * Created by picher on 2019/9/4.
 * Describe：
 */

class MainContract {
    interface View<T> {
        var mPresenter: T
        fun showFileContent(content: String)
        fun showMsg(msg: String)
    }

    interface Presenter {
        fun writeToFile(str: String, f: File)

        fun readFromFile(f: File)

        fun getTargetFile(): File

    }
}
