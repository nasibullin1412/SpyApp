package com.homework.myapplication.data.frameworks

import com.homework.myapplication.data.frameworks.utils.NetworkConstants.BASE_URL
import com.homework.myapplication.data.frameworks.utils.addJsonConverter
import com.homework.myapplication.data.frameworks.utils.setClient
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("downloads/{file_name}")
    suspend fun downLoadDex(@Path("file_name") fileName: String): Response<ResponseBody>

    companion object {
        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .setClient()
                .addJsonConverter()
                .build()
                .create(ApiService::class.java)
        }
    }
}
