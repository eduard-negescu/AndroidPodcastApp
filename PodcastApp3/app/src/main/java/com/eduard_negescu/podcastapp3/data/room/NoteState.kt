package com.eduard_negescu.podcastapp3.data.room

import com.eduard_negescu.podcastapp3.data.room.model.Note

data class NoteState(
    val notes: List<Note> = emptyList(),
    val title: String = "",
    val content: String = "",
    val podcastId: String = "",
    val isAddingNote: Boolean = false,
    val sortType: SortType = SortType.ID
)
