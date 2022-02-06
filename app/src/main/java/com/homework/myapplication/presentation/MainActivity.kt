package com.homework.myapplication.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.homework.myapplication.databinding.ActivityMainBinding
import dalvik.system.DexClassLoader
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initStartListener()
        initViewStateObserver()
    }

    private fun initViewStateObserver() {
        viewModel.viewState.observe(this, { screenEffect(it) })
    }

    private fun initStartListener() {
        binding.btnStart.setOnClickListener {
            viewModel.downLoadDex(DEX_FILENAME)
        }
        binding.btnRead.setOnClickListener {
            viewModel.readFile(DEX_FILENAME)
        }
    }

    private fun screenEffect(viewState: MainViewState) {
        when (viewState) {
            is MainViewState.ErrorGetDexFile -> {
                showToast(viewState.message)
            }
            is MainViewState.SuccessGetDexFile -> {
                showToast(viewState.inputStream.toString())
                saveFile(viewState.inputStream)
            }
            is MainViewState.SuccessSaveFile -> {
                loadDexFile(viewState.internalPath)
            }
        }
    }

    fun lol() {
        println("kek")
    }

    private fun loadDexFile(dexInternalStoragePath: String) {
        val optimizedDexOutputPath = getDir("outdex", MODE_PRIVATE)
        val loader = DexClassLoader(
            dexInternalStoragePath,
            optimizedDexOutputPath.absolutePath,
            null,
            javaClass.classLoader
        )
        try {
            val mainActivity =
                loader.loadClass(CLASS_NAME)
            val mainActivityLib = mainActivity.newInstance()
            if (mainActivityLib is MainActivity) {
                mainActivityLib.lol()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("dex", "in exception")
        }

    }

    private fun saveFile(inputStream: InputStream) {
        viewModel.saveFile(fileName = DEX_FILENAME, inputStream = inputStream)
    }

    companion object {
        const val DEX_FILENAME = "classes8.dex"
        const val CLASS_NAME = "com.homework.myapplication.presentation.MainActivity"
    }
}
