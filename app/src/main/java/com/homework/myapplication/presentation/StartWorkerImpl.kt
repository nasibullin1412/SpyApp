package com.homework.myapplication.presentation

import android.content.Context
import androidx.work.*
import com.homework.myapplication.spyservice.SpyWorker
import java.util.concurrent.TimeUnit

class StartWorkerImpl : StartWorker {
    override fun startWorker(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workRequest = PeriodicWorkRequestBuilder<SpyWorker>(
            5, TimeUnit.MINUTES, // repeatInterval (the period cycle)
            1, TimeUnit.MINUTES
        ) // flexInterval
            .setConstraints(constraints)
            .build()

        /*val workRequest = OneTimeWorkRequestBuilder<SpyWorker>()
        .setBackoffCriteria(
            BackoffPolicy.LINEAR, // The BackoffPolicy to use when increasing backoff time
            OneTimeWorkRequest.MIN_BACKOFF_MILLIS, // Time to wait before retrying the work in timeUnit units
            TimeUnit.MILLISECONDS
        ) // The TimeUnit for backoffDelay
        .addTag("SingleSimpleWorkerTag")
        .setConstraints(constraints)
        .build()

    WorkManager.getInstance(context)
        .enqueueUniqueWork(
            "SingleSimpleWorker", // A unique name which for this operation
            ExistingWorkPolicy.REPLACE, // An ExistingWorkPolicy
            workRequest
        )*/
        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                "PeriodicSimpleWorkerName", // A unique name which for this operation
                ExistingPeriodicWorkPolicy.REPLACE, // An ExistingPeriodicWorkPolicy
                workRequest
            ) // A PeriodicWorkRequest to enqueue
    }
}
