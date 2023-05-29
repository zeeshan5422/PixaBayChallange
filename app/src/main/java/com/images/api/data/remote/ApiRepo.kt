package com.images.api.data.remote

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.MalformedJsonException
import com.images.api.util.*
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.reflect.Type
import java.net.ConnectException
import java.net.UnknownHostException

/**
 * Created by ZEESHAN on 5/12/2023.
 */

inline fun <reified T> getTypeToken(): Type = object : TypeToken<T>() {}.type


interface ApiRepo {

    suspend fun <T> Response<T>.handle(): Resource<T> {
        return try {
            val body = this.body()
            if (this.isSuccessful && body != null) {
                Resource.Success(body)
            } else {
                handleFailureResponse(
                    this.code(), this.message(), this.errorBody()
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            handleException(e)
        }
    }

    private fun <T> handleFailureResponse(
        code: Int, message: String, errorBody: ResponseBody?
    ): Resource<T> {

        return try {
            val errorCode: Int = when (code) {
                in (401..403), 440 -> UNAUTHORIZED
                404 -> NOT_FOUND
                500 -> BAD_RESPONSE
                405 -> NO_NETWORK
                else -> code
            }
            Resource.Failure(ResourceError(errorCode, message))
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(
                "BaseRepository", e.message ?: "Unknown error while handling failure response"
            )
            Resource.Failure(ResourceError(-1/*InvocationKind.UNKNOWN()*/))
        }
    }

    private fun <T> handleException(exception: Exception?): Resource<T> {
        exception?.let {
            val apiError = when (exception) {
                is ConnectException -> {
                    ResourceError(NO_NETWORK, message = "No internet Connection") //
                }
                is MalformedJsonException -> {
                    ResourceError(
                        BAD_RESPONSE, message = exception.message.toString()
                    )
                }
                is UnknownHostException -> {
                    ResourceError(NO_NETWORK, message = exception.message.toString())
                }
                else -> {
                    ResourceError(
                        BAD_RESPONSE, message = exception.message.toString()
                    )
                }
            }
            return Resource.Failure(apiError)
        }
        return Resource.Failure(
            ResourceError(ERROR_UNKNOWN, message = exception?.message.toString())
        )
    }


    // Assets json response loading
    fun <T> getDataFromPath(context: Context, path: String, typ: Type): Resource<T> {
        val loadedData = FileUtils.loadJSONStringFromAsset(context, path)
        loadedData?.let {
            val data: T = Gson().fromJson(loadedData, typ)
            Log.d("response", data.toString())
            return Resource.Success(data)
        } ?: kotlin.run {
            return Resource.Failure(
                ResourceError(
                    errorCode = NOT_FOUND, message = "file not exist on path: $path"
                )
            )
        }
    }

}