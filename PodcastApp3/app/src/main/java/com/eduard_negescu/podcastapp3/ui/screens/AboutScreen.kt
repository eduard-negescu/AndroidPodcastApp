package com.eduard_negescu.podcastapp3.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eduard_negescu.podcastapp3.R
import com.eduard_negescu.podcastapp3.ui.BackButton

@Composable
fun AboutScreen(navController: NavController) {
    Surface(
        modifier = Modifier.padding(40.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BackButton(navController)
            Image(
                painter = painterResource(id = R.drawable.logotitle),
                contentDescription = "PodcastApp"
            )
            Text(
                text = "PodcastApp",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

        }
    }
}