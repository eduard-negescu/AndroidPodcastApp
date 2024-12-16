package com.eduard_negescu.podcastapp3.data.room

import com.eduard_negescu.podcastapp3.data.room.model.Note

sealed interface NoteEvent {
    object SaveNote : NoteEvent
    data class SetTitle(val title: String) : NoteEvent
    data class SetContent(val content: String) : NoteEvent
    data class SetPodcastId(val podcastId: String) : NoteEvent
    object ShowDialog : NoteEvent
    object HideDialog : NoteEvent
    data class SortNotes(val sortType: SortType) : NoteEvent
    data class DeleteNote(val note: Note) : NoteEvent

}