package com.eduard_negescu.podcastapp3.data.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoritePodcast(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val publisher: String,
    val description: String,
    val image: String
)
