package com.homework.myapplication.spyservice

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

class GetInstalled : (Context) -> (List<String>) {
    @SuppressLint("QueryPermissionsNeeded")
    override fun invoke(context: Context): List<String> {
        return context.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
            .filter { a: ApplicationInfo -> a.name != null }
            .map { a: ApplicationInfo -> a.name }
    }
}
