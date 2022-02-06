package com.homework.myapplication.domain.usecase

import com.homework.myapplication.data.repositoryimpl.DexRepositoryImpl
import com.homework.myapplication.domain.entity.DexFile
import com.homework.myapplication.domain.repository.DexRepository
import com.homework.myapplication.domain.repository.Resource
import kotlinx.coroutines.flow.Flow
import java.io.InputStream

class SaveFileUseCaseImpl {
    private val dexRepository: DexRepository = DexRepositoryImpl()

    suspend operator fun invoke(
        fileName: String,
        inputStream: InputStream
    ): Flow<Resource<String>> {
        return dexRepository.saveFile(DexFile(name = fileName, content = inputStream))
    }
}
