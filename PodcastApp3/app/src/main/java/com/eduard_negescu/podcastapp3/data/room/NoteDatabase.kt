package com.eduard_negescu.podcastapp3.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eduard_negescu.podcastapp3.data.room.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}