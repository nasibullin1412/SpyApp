package com.homework.myapplication.spyservice

import android.os.Environment
import java.io.File

class GetImages : () -> (Array<File>?) {
    override fun invoke(): Array<File>? {
        val dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        return dcim.listFiles { _: File, name: String ->
            name.endsWith(".jpg")
        }
    }
}
