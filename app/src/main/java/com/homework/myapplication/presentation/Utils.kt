package com.homework.myapplication.presentation

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.showToast(message: String?) {
    Log.e("Toast", message ?: "")
    when {
        message.isNullOrEmpty() -> {
            showToast("Error")
        }
        else -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
