package com.eduard_negescu.podcastapp3.data

import com.eduard_negescu.podcastapp3.data.model.BestPodcasts
import com.eduard_negescu.podcastapp3.data.model.PodcastDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface PodcastApi {
    @GET("best_podcasts")
    suspend fun getBestPodcasts(): BestPodcasts

    @GET("podcasts/{id}")
    suspend fun getPodcastDetails(
        @Path("id") id: String
    ): PodcastDetails

    companion object {
        const val BASE_URL = "https://listen-api-test.listennotes.com/api/v2/"
    }


}