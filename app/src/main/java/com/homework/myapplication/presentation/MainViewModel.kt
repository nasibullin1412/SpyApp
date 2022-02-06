package com.homework.myapplication.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homework.myapplication.domain.entity.DexFile
import com.homework.myapplication.domain.usecase.DownloadDexUseCaseImpl
import com.homework.myapplication.domain.usecase.ReadFileUseCase
import com.homework.myapplication.domain.usecase.SaveFileUseCaseImpl
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.io.InputStream

class MainViewModel : ViewModel() {
    val viewState: LiveData<MainViewState> get() = _viewState
    private val _viewState = MutableLiveData<MainViewState>()
    private val downloadDex: DownloadDexUseCaseImpl = DownloadDexUseCaseImpl()
    private val saveFileUseCase: SaveFileUseCaseImpl = SaveFileUseCaseImpl()
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
            saveFileUseCase(fileName = fileName, inputStream = inputStream).catch { e ->
                _viewState.value = MainViewState.ErrorGetDexFile(e.message ?: "Error")
            }.collect {
                if (it.data == null) {
                    _viewState.value = MainViewState.ErrorGetDexFile(it.message ?: "Error")
                    return@collect
                }
                _viewState.value = MainViewState.SuccessSaveFile(it.data)
            }
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
