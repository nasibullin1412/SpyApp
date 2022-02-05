package com.homework.myapplication.data.repositoryimpl

import android.content.Context
import com.homework.myapplication.App
import com.homework.myapplication.data.frameworks.mappers.DexFileDtoMapper
import com.homework.myapplication.domain.entity.DexFile
import com.homework.myapplication.domain.repository.BaseDataSource
import com.homework.myapplication.domain.repository.DexRepository
import com.homework.myapplication.domain.repository.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.io.InputStream

class DexRepositoryImpl : BaseDataSource(), DexRepository {

    private val apiService = App.instance.apiService
    private val dexFileDtoMapper: DexFileDtoMapper = DexFileDtoMapper()

    override suspend fun downloadFile(dexFile: DexFile): Flow<Resource<InputStream>> = flow {
        val result = safeApiCall { apiService.downLoadDex(dexFile.name) }
        emit(dexFileDtoMapper(result))
    }.flowOn(Dispatchers.IO)

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun saveFile(dexFile: DexFile): Unit =
        withContext(Dispatchers.IO) {
            App.instance.applicationContext.openFileOutput(dexFile.name, Context.MODE_PRIVATE)
                .use { outputStream ->
                    outputStream.write(dexFile.content.readBytes())
                }
        }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun readFile(dexFile: DexFile): Flow<Resource<InputStream>> = flow {
        val content = App.instance.applicationContext.openFileInput(dexFile.name)
        val result = Resource.success(data = content)
        emit(result)
    }.flowOn(Dispatchers.IO)
}
