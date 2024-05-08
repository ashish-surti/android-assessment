package com.example.universities.data.remote

import android.util.Log
import com.example.universities.utils.Resource
import retrofit2.Response

// universal api call for all the apis in the project
abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return error(" ${response.code()} ${response.errorBody()?.string()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Log.d("error_msg", message)
        return Resource.error("Network call has failed for a following reason: $message")
    }

}