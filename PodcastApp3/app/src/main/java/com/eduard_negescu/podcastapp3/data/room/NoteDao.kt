package com.eduard_negescu.podcastapp3.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.eduard_negescu.podcastapp3.data.room.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Upsert
    suspend fun upsertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM note ORDER BY id ASC")
    fun getNotesOrderedById(): Flow<List<Note>>

    @Query("SELECT * FROM note ORDER BY title ASC")
    fun getNotesOrderedByTitle(): Flow<List<Note>>

}