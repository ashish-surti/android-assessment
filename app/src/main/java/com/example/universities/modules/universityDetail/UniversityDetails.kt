package com.example.universities.modules.universityDetail

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.universities.model.UniversityModel
import com.example.universities.modules.universities.viewModel.UniversitiesViewModel
import com.example.universities.modules.universityDetail.ui.theme.UniversitiesTheme

class UniversityDetails : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("university", UniversityModel::class.java)
        } else {
            intent.getSerializableExtra("university") as UniversityModel
        }
        setContent {
            UniversitiesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    if (data != null) {
                        UniversityDetail(university = data)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniversityDetail(university: UniversityModel) {
    val context = (LocalContext.current as? Activity)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "University Detail")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        context?.finish()
                    }) {
                        Icon(
                            Icons.Rounded.ArrowBack,
                            contentDescription = "Back Button"
                        )
                    }

                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(10.dp)
                    .fillMaxSize()
            ) {
                IconButton(onClick = {
                    context?.finish()
                }) {
                    Icon(
                        Icons.Rounded.Refresh,
                        contentDescription = "Refresh Button"
                    )
                }
                Text(text = university.name ?: "")
                if (!university.stateProvince.isNullOrEmpty()) {
                    Text(text = university.stateProvince)
                }
                Row {
                    if (!university.country.isNullOrEmpty()) {
                        Text(text = university.country)
                    }
                    if (!university.alphaTwoCode.isNullOrEmpty()) {
                        Text(text = university.alphaTwoCode)
                    }
                }
                if (!university.webPages.isNullOrEmpty()) {
                    Text(text = university.webPages.joinToString("\n"))
                }
            }
        }
    )

}