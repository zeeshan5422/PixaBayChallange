package com.images.api.util

/**
 * Created by ZEESHAN on 5/11/2023.
 */


const val ERROR_UNKNOWN = -1
const val ERROR_EMPTY_DATA = 49
const val UNAUTHORIZED = 50
const val NOT_FOUND = 51
const val BAD_RESPONSE = 52
const val NO_NETWORK = 53

sealed class Resource<out T>(
    val isSuccessful: Boolean,
    val data: T?,
    val apiError: ResourceError?
) {
    data class Success<out T>(val responseData: T) : Resource<T>(true, responseData, null)
    data class Failure<out T>(val errorData: ResourceError) : Resource<T>(false, null, errorData)
}


data class ResourceError(
    val errorCode: Int = ERROR_UNKNOWN, // this can also be our custom object of error codes
    override val message: String = "",
) : Throwable(message = message) {
    override fun toString(): String {
        return "$errorCode : $message"
    }
}

fun emptyException(): ResourceError {
    return ResourceError(errorCode = ERROR_UNKNOWN, message = "unhandled exception occurred")
}
