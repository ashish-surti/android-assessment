package com.example.universities.modules.universities.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.universities.model.UniversityModel
import com.example.universities.ui.theme.UniversitiesTheme
import com.example.universities.utils.Resource
import com.example.universities.modules.universities.viewModel.UniversitiesViewModel
import com.example.universities.modules.universityDetail.UniversityDetails
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<UniversitiesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val data by viewModel.universitiesResponse.observeAsState()
            UniversitiesTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (data?.status) {
                        Resource.Status.SUCCESS -> {
                            if (data?.data != null && data?.data?.isNotEmpty()!!) {
                                saveLocalUniversity(data?.data!!)
                                UniversityList(
                                    universities = data?.data!!,
                                    infoMessage = "University List from the server."
                                )
                            } else {
                                UniversityList(
                                    universities = emptyList(),
                                    infoMessage = "We're currently unable to retrieve data from the server."
                                )
                            }
                        }

                        Resource.Status.ERROR -> {
                            viewModel.getLocalUniversities()

                            showLocalData()
                        }

                        Resource.Status.LOADING -> {
                            loadingState()
                        }

                        null -> {
                            Text(text = "No Data")
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        callApi()
    }

    @Composable
    private fun loadingState() {
        val strokeWidth = 3.dp

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(32.dp),
                color = Color.LightGray,
                strokeWidth = strokeWidth
            )
        }
    }

    // show local database data if API error occurs
    @Composable
    private fun showLocalData() {
        val localData by viewModel.localUniversities.observeAsState()

        when(localData?.status) {
            Resource.Status.SUCCESS -> {

                if (localData?.data != null && localData?.data?.isNotEmpty()!!) {
                    saveLocalUniversity(localData?.data!!)
                    UniversityList(
                        universities = localData?.data ?: emptyList(),
                        infoMessage =  "We're currently unable to retrieve data from the server. Displaying cached data for now."
                    )
                } else {
                    UniversityList(
                        universities = emptyList(),
                        infoMessage = "We're currently unable to retrieve data from the server."
                    )
                }


            }
            Resource.Status.ERROR -> {
                UniversityList(
                    universities = emptyList(),
                    infoMessage = "We're currently unable to retrieve data from the server."
                )
            }
            Resource.Status.LOADING -> {
                loadingState()
            }
            null ->  {
                Text(text = "No Data")
            }
        }


    }

    // make api call to server
    private fun callApi() {
        viewModel.getUniversities()
    }

    // save the universities from the server to the local database
    private fun saveLocalUniversity(universities: List<UniversityModel>) {
        universities.forEach { university ->
            viewModel.insertUniversity(university)
        }
    }
}

// a view to show all the universities either local or from server
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniversityList(infoMessage: String, universities: List<UniversityModel>) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "University List")
                },
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .verticalScroll(state = scrollState)
                    .padding(it)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    Text(text = infoMessage, modifier = Modifier
                        .padding(bottom = 16.dp))
                    Divider()
                }
                if (universities.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "No Data Found")
                    }
                } else {
                    universities.forEach { university ->
                        Row(
                            modifier = Modifier
                                .clickable {
                                    val intent = Intent(context, UniversityDetails::class.java)
                                    intent.putExtra("university", university)
                                    context.startActivity(intent)
                                }
                                .padding(16.dp)
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = CenterVertically
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.weight(weight = 1.0f)
                            ) {
                                Text(text = university.name ?: "")
                                if (!university.stateProvince.isNullOrEmpty()) {
                                    Text(text = university.stateProvince)
                                }
                            }
                            Icon(
                                Icons.Rounded.KeyboardArrowRight,
                                contentDescription = "Right Arrow"
                            )
                        }
                        Divider()
                    }
                }
            }
        }
    )

}