package com.example.universities.modules.universities.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.universities.data.repository.UniversitiesRepository
import com.example.universities.db.DatabaseBuilder
import com.example.universities.db.entity.LocalDomains
import com.example.universities.db.entity.LocalUniversity
import com.example.universities.db.entity.LocalWebPages
import com.example.universities.model.UniversityModel
import com.example.universities.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UniversitiesViewModel @Inject constructor(
    private val repository: UniversitiesRepository
) : ViewModel() {

    private val _universitiesResponse = MutableLiveData<Resource<List<UniversityModel>>>()

    val universitiesResponse: LiveData<Resource<List<UniversityModel>>> = _universitiesResponse

    private val _localUniversities = MutableLiveData<Resource<List<UniversityModel>>>()

    val localUniversities: LiveData<Resource<List<UniversityModel>>> = _localUniversities

    // get the data from the server
    fun getUniversities() = viewModelScope.launch {
        _universitiesResponse.value = Resource.loading()
        repository.getUniversities().collect {
            _universitiesResponse.value = it
        }
    }

    // save the university in local database
    fun insertUniversity(university: UniversityModel) = viewModelScope.launch {
        val universityDao = DatabaseBuilder.getInstance().universityDao()
        val allUniversities = universityDao.getAllUniversities()
        var isUniversityExists = false
        allUniversities.forEach {
            if (university.name == it.name) {
                isUniversityExists = true
            }
        }
        if (!isUniversityExists) {
            val localUniversity = LocalUniversity(
                id = 0,
                country = university.country,
                name = university.name,
                stateProvince = university.stateProvince,
                alphaTwoCode = university.alphaTwoCode
            )
            val id = universityDao.insertUniversity(localUniversity)
            if (!university.domains.isNullOrEmpty()) {
                university.domains.forEach {
                    universityDao.insertDomain(
                        LocalDomains(
                            id = 0,
                            universityId = id.toInt(),
                            domain = it
                        )
                    )
                }
            }
            if (!university.webPages.isNullOrEmpty()) {
                university.webPages.forEach {
                    universityDao.insertWebPage(
                        LocalWebPages(
                            id = 0,
                            universityId = id.toInt(),
                            webPage = it
                        )
                    )
                }
            }
        }
    }

    // get the universities from local database
    fun getLocalUniversities() = viewModelScope.launch {
        val universityDao = DatabaseBuilder.getInstance().universityDao()
        _localUniversities.value = Resource.loading()
        val allUniversities = universityDao.getAllUniversities()
        val universityList: MutableList<UniversityModel> = mutableListOf()

        allUniversities.forEach { university ->
            val webPages = universityDao.getAllWebPages(universityId = university.id)
            val domains = universityDao.getAllDomains(universityId = university.id)
            universityList.add(
                UniversityModel(
                    country = university.country,
                    webPages = webPages.map { it.webPage ?: "" },
                    name = university.name,
                    domains = domains.map { it.domain ?: "" },
                    stateProvince = university.stateProvince,
                    alphaTwoCode = university.alphaTwoCode
                )
            )
        }

        _localUniversities.value = Resource.success(universityList)
    }
}