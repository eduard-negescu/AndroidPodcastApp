package com.eduard_negescu.podcastapp3.ui.screens

import android.widget.Space
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun WelcomeScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize()
            .padding(30.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Welcome to PodcastApp3!",
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = "Go to: ",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Best Podcasts",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.clickable {
                    navController.navigate("best_podcasts")
                }
            )

            Text(
                text = "Notes",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.clickable {
                    navController.navigate("notes")
                }
            )
            Text(
                text = "About App",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.clickable {
                    navController.navigate("about")
                }
            )
        }

    }
}