package com.homework.myapplication.domain.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class BaseDataSource {
    /**
     * generic for safe remote api call
     */
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T> {
        return try {
            val response = withContext(Dispatchers.IO) {
                apiCall()
            }
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Resource.success(body)
                } else {
                    Resource.failed("Null body fail.")
                }
            } else {
                Resource.error(response.errorBody().toString())
            }
        } catch (e: Exception) {
            Resource.error(e.message.toString())
        }
    }
}
