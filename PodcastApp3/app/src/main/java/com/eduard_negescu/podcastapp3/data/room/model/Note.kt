package com.eduard_negescu.podcastapp3.data.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val title: String,
    val content: String,
    val podcastId: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
