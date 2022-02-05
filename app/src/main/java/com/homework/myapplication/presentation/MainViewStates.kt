package com.homework.myapplication.presentation

import java.io.InputStream

sealed class MainViewState {
    class SuccessGetDexFile(val inputStream: InputStream) : MainViewState()
    class SuccessReadFile(val outputStream: InputStream)
    class ErrorGetDexFile(val message: String) : MainViewState()
}
