package com.eduard_negescu.podcastapp3.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eduard_negescu.podcastapp3.data.model.Episode
import com.eduard_negescu.podcastapp3.data.model.PodcastDetails
import com.eduard_negescu.podcastapp3.ui.BackButton
import com.eduard_negescu.podcastapp3.ui.CoilImage
import com.eduard_negescu.podcastapp3.ui.MyCircularProgressIndicator
import com.eduard_negescu.podcastapp3.ui.view_models.PodcastDetailsViewModel

@Composable
fun PodcastDetailsScreen(viewModel: PodcastDetailsViewModel, navController: NavController) {
    val podcast = viewModel.podcast.collectAsState().value
    Surface(modifier = Modifier.padding(16.dp)) {
        if (podcast == null) {
            MyCircularProgressIndicator()
        } else {
            Column {
                BackButton(navController)
                Details(podcast)
                Text(text = "Episodes",
                    style = MaterialTheme.typography.headlineLarge
                )
                LazyColumn {
                    items(podcast.episodes) { episode ->
                        EpisodeView(episode)
                        Spacer(modifier = Modifier.height(30.dp))
                        Divider()
                    }
                }
            }
        }
    }
}

@Composable
fun Details(podcast: PodcastDetails) {
    Spacer(modifier = Modifier.height(16.dp))
    Row {
        CoilImage(
            imageUrl = podcast.image,
            description = podcast.title,
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
                .padding(16.dp)
        )
        Text(
            text = podcast.title,
            style = MaterialTheme.typography.headlineLarge
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = podcast.publisher,
        style = MaterialTheme.typography.titleMedium
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = podcast.description,
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
fun EpisodeView(episode: Episode) {
    Column {
        Text(episode.title)
    }
}