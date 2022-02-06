package com.homework.myapplication.domain.usecase

import com.homework.myapplication.data.repositoryimpl.DexRepositoryImpl
import com.homework.myapplication.domain.entity.DexFile
import com.homework.myapplication.domain.repository.DexRepository
import com.homework.myapplication.domain.repository.Resource
import kotlinx.coroutines.flow.Flow
import java.io.InputStream

class DownloadDexUseCaseImpl {
    private val dexRepository: DexRepository = DexRepositoryImpl()

    suspend operator fun invoke(dexFile: DexFile): Flow<Resource<InputStream>> {
        return dexRepository.downloadFile(dexFile)
    }
}
