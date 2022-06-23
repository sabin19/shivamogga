package com.sabin.shivamogga.trip.planner.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.sabin.shivamogga.ui.theme.ShivamoggaTheme

@Composable
fun HomeScreen(){
    ShivamoggaTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

        }
    }
}

@Composable
fun MapScreen() {
    var mapProperties by remember {
        mutableStateOf(
            MapProperties(maxZoomPreference = 10f, minZoomPreference = 5f)
        )
    }
    var mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(mapToolbarEnabled = false)
        )
    }

    Box(Modifier.fillMaxSize()) {
        GoogleMap(properties = mapProperties, uiSettings = mapUiSettings)
        Column {
            Button(onClick = {
                mapProperties = mapProperties.copy(
                    isBuildingEnabled = !mapProperties.isBuildingEnabled
                )
            }) {
                Text(text = "Toggle isBuildingEnabled")
            }
            Button(onClick = {
                mapUiSettings = mapUiSettings.copy(
                    mapToolbarEnabled = !mapUiSettings.mapToolbarEnabled
                )
            }) {
                Text(text = "Toggle mapToolbarEnabled")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ShivamoggaTheme {
        MapScreen()
    }
}