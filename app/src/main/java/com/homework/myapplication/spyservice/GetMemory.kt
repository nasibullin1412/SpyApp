package com.homework.myapplication.spyservice

import android.app.ActivityManager
import android.content.Context

class GetMemory : (Context) -> (Long) {
    override fun invoke(context: Context): Long {
        val memoryInfo = ActivityManager.MemoryInfo()
        return (context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).run {
            getMemoryInfo(memoryInfo)
            memoryInfo.availMem
        }
    }
}
