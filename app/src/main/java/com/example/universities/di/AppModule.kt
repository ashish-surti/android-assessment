package com.example.universities.di

import com.example.universities.data.remote.UniversitiesRemoteDataSource
import com.example.universities.data.remote.UniversitiesService
import com.example.universities.data.repository.UniversitiesRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// dependency injection for the api call
@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("http://universities.hipolabs.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Singleton
    @Provides
    fun provideUniversitiesService(retrofit: Retrofit): UniversitiesService =
        retrofit.create(UniversitiesService::class.java)

    @Singleton
    @Provides
    fun provideUniversitiesRemoteDataSource(universitiesService: UniversitiesService) =
        UniversitiesRemoteDataSource(universitiesService)

    @Singleton
    @Provides
    fun provideUniversitiesRepository(universitiesRemoteDataSource: UniversitiesRemoteDataSource) =
        UniversitiesRepository(universitiesRemoteDataSource)
}