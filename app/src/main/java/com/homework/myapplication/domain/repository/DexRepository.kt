package com.homework.myapplication.domain.repository

import android.net.Uri
import com.homework.myapplication.domain.entity.DexFile
import kotlinx.coroutines.flow.Flow
import java.io.InputStream

interface DexRepository {
    suspend fun downloadFile(dexFile: DexFile): Flow<Resource<InputStream>>
    suspend fun saveFile(dexFile: DexFile)
    suspend fun readFile(dexFile: DexFile): Flow<Resource<InputStream>>
}
