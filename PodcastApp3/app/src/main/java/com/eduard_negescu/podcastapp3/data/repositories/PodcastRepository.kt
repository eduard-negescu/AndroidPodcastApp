package com.eduard_negescu.podcastapp3.data.repositories

import com.eduard_negescu.podcastapp3.data.model.BestPodcasts
import com.eduard_negescu.podcastapp3.data.Result
import com.eduard_negescu.podcastapp3.data.model.Podcast
import com.eduard_negescu.podcastapp3.data.model.PodcastDetails
import kotlinx.coroutines.flow.Flow

interface PodcastRepository {
    suspend fun getBestPodcasts(): Flow<Result<List<Podcast>>>
    suspend fun getPodcastDetails(id: String): Flow<Result<PodcastDetails>>

}