package com.example.universities.data.remote

import javax.inject.Inject

// fetch the data from the apis
class UniversitiesRemoteDataSource @Inject constructor(
    private val service: UniversitiesService
): BaseDataSource() {

    suspend fun getUniversities() = getResult { service.getUniversities() }
}