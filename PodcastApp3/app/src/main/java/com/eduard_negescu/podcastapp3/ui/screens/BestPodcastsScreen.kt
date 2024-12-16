package com.eduard_negescu.podcastapp3.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eduard_negescu.podcastapp3.data.model.Podcast
import com.eduard_negescu.podcastapp3.ui.BackButton
import com.eduard_negescu.podcastapp3.ui.CoilImage
import com.eduard_negescu.podcastapp3.ui.MyCircularProgressIndicator
import com.eduard_negescu.podcastapp3.ui.view_models.BestPodcastsViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun BestPodcastsScreen(viewModel: BestPodcastsViewModel, navController: NavController) {
    val podcastList = viewModel.podcasts.collectAsState().value
    val localContext = LocalContext.current
    
    LaunchedEffect(key1 = viewModel.showErrorToastChannel) {
        viewModel.showErrorToastChannel.collectLatest { show ->
            if (show) {
                Toast.makeText(
                    localContext,
                    "Error loading podcasts",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    if (podcastList.isEmpty()) {
        MyCircularProgressIndicator()
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            contentPadding = PaddingValues(16.dp)
        ) {

            item {
                Spacer(modifier = Modifier.height(40.dp))
                BackButton(navController)
            }
            items(podcastList.size) { index ->
                val podcastEntry = podcastList[index]
                Podcast(podcastEntry, navController, "podcast_details/${podcastEntry.id}")
            }
        }
    }
}

@Composable
fun Podcast(podcast: Podcast, navController: NavController, route: String) {
    Column(
        Modifier
            .clip(RoundedCornerShape(10.dp))
            .height(200.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ){
        CoilImage(
            imageUrl = podcast.image,
            description = podcast.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = podcast.title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .clickable(
                    onClick = {
                        navController.navigate(route)
                    }
                )
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}
