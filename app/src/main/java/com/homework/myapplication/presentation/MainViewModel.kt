package com.homework.myapplication.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homework.myapplication.domain.entity.DexFile
import com.homework.myapplication.domain.usecase.DownloadDexImpl
import com.homework.myapplication.domain.usecase.ReadFileUseCase
import com.homework.myapplication.domain.usecase.SaveFileImpl
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.io.InputStream

class MainViewModel : ViewModel() {
    val viewState: LiveData<MainViewState> get() = _viewState
    private val _viewState = MutableLiveData<MainViewState>()
    private val downloadDex: DownloadDexImpl = DownloadDexImpl()
    private val saveFileImpl: SaveFileImpl = SaveFileImpl()
    private val readFileImpl: ReadFileUseCase = ReadFileUseCase()

    fun downLoadDex(fileName: String) {
        viewModelScope.launch {
            downloadDex(dexFile = DexFile(fileName)).catch { e ->
                _viewState.value = MainViewState.ErrorGetDexFile(e.message ?: "Error")
            }.collect {
                if (it.data == null) {
                    _viewState.value = MainViewState.ErrorGetDexFile(it.message ?: "Error")
                    return@collect
                }
                _viewState.value = MainViewState.SuccessGetDexFile(it.data)
            }
        }
    }

    fun saveFile(fileName: String, inputStream: InputStream) {
        viewModelScope.launch {
            saveFileImpl(fileName = fileName, inputStream = inputStream)
        }
    }

    fun readFile(fileName: String) {
        viewModelScope.launch {
            readFileImpl(DexFile(name = fileName)).catch { e ->
                _viewState.value = MainViewState.ErrorGetDexFile(e.message ?: "Error")
            }.collect {
                if (it.data == null) {
                    _viewState.value = MainViewState.ErrorGetDexFile(it.message ?: "Error")
                    return@collect
                }
                _viewState.value = MainViewState.SuccessGetDexFile(it.data)
            }
        }
    }
}
