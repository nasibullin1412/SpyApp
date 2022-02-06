package com.homework.myapplication.domain.repository

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        FAILURE
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, message)
        }

        fun <T> failed(message: String, data: T? = null): Resource<T> {
            return Resource(Status.FAILURE, data, message)
        }
    }
}
