package com.eduard_negescu.podcastapp3

import com.eduard_negescu.podcastapp3.data.PodcastApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: PodcastApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(PodcastApi.BASE_URL)
        .build()
        .create(PodcastApi::class.java)
}