package com.homework.myapplication.spyservice

import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE

class GetRunning : (Context) -> (List<String>) {
    override fun invoke(context: Context): List<String> {
        val activityManager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        return activityManager.runningAppProcesses.filter { it.processName != null }
            .map { it.processName }
    }
}
