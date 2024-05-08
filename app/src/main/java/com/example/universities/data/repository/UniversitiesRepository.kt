package com.example.universities.data.repository

import com.example.universities.data.remote.UniversitiesRemoteDataSource
import com.example.universities.model.UniversityModel
import com.example.universities.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UniversitiesRepository @Inject constructor(
    private val remoteDataSource: UniversitiesRemoteDataSource
) {
    suspend fun getUniversities(): Flow<Resource<List<UniversityModel>>> =
        flow {
            emit(remoteDataSource.getUniversities())
        }.flowOn(Dispatchers.IO)
}