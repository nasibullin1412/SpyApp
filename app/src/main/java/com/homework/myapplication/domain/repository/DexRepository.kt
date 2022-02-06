package com.homework.myapplication.domain.repository

import com.homework.myapplication.domain.entity.DexFile
import kotlinx.coroutines.flow.Flow
import java.io.InputStream

interface DexRepository {
    suspend fun downloadFile(dexFile: DexFile): Flow<Resource<InputStream>>
    suspend fun saveFile(dexFile: DexFile): Flow<Resource<String>>
    suspend fun readFile(dexFile: DexFile): Flow<Resource<InputStream>>
}
