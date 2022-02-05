package com.homework.myapplication.domain.usecase

import com.homework.myapplication.data.repositoryimpl.DexRepositoryImpl
import com.homework.myapplication.domain.entity.DexFile
import com.homework.myapplication.domain.repository.DexRepository
import java.io.InputStream

class SaveFileImpl {
    private val dexRepository: DexRepository = DexRepositoryImpl()

    suspend operator fun invoke(fileName: String, inputStream: InputStream) {
        dexRepository.saveFile(DexFile(name = fileName, content = inputStream))
    }
}
