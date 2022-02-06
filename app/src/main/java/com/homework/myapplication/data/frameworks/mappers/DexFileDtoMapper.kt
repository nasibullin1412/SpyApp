package com.homework.myapplication.data.frameworks.mappers

import com.homework.myapplication.domain.repository.Resource
import okhttp3.ResponseBody
import java.io.InputStream

class DexFileDtoMapper : (Resource<ResponseBody>) -> (Resource<InputStream>) {
    override fun invoke(responseBody: Resource<ResponseBody>): Resource<InputStream> {
        return responseBody.data?.let {
            Resource.success(it.byteStream())
        } ?: Resource.failed(responseBody.message ?: "")
    }
}
