package com.eduard_negescu.podcastapp3.data.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.eduard_negescu.podcastapp3.data.room.model.FavoritePodcast

@Dao
interface FavoritePodcastDao {
    @Upsert
    fun upsertFavoritePodcast(favoritePodcast: FavoritePodcast)

    @Query("SELECT * FROM FavoritePodcast ORDER BY id")
    fun getFavoritePodcastsOrderedByDefault(): List<FavoritePodcast>

    @Query("SELECT * FROM FavoritePodcast ORDER BY title")
    fun getFavoritePodcastsOrderedByTitle(): List<FavoritePodcast>

}