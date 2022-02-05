package com.homework.myapplication.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.homework.myapplication.databinding.ActivityMainBinding
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
        }
    }

    private fun saveFile(inputStream: InputStream) {
        viewModel.saveFile(fileName = DEX_FILENAME, inputStream = inputStream)
    }

    companion object {
        const val DEX_FILENAME = "lol.txt"
    }
}
