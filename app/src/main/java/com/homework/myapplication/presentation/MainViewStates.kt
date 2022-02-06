package com.homework.myapplication.presentation

import java.io.InputStream

sealed class MainViewState {
    class SuccessGetDexFile(val inputStream: InputStream) : MainViewState()
    class SuccessSaveFile(val internalPath: String) : MainViewState()
    class ErrorGetDexFile(val message: String) : MainViewState()
}
