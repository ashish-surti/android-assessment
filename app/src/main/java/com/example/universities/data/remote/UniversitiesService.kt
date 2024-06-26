package com.example.universities.data.remote

import com.example.universities.model.UniversityModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// all the http method will be stored here
interface UniversitiesService {

    @GET("search")
    suspend fun getUniversities(@Query("country") country: String = "United Arab Emirates"): Response<List<UniversityModel>>
}