package com.eduard_negescu.podcastapp3.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eduard_negescu.podcastapp3.data.room.NoteDao
import com.eduard_negescu.podcastapp3.data.room.NoteEvent
import com.eduard_negescu.podcastapp3.data.room.NoteState
import com.eduard_negescu.podcastapp3.data.room.SortType
import com.eduard_negescu.podcastapp3.data.room.model.Note
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteViewModel(
    private val dao: NoteDao
) : ViewModel() {
    private val _sortType = MutableStateFlow(SortType.ID)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _notes = _sortType
        .flatMapLatest {
            when (it) {
                SortType.ID -> dao.getNotesOrderedById()
                SortType.TITLE -> dao.getNotesOrderedByTitle()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(NoteState())
    val state = combine(_state, _sortType, _notes) { state, sortType, notes ->
        state.copy(
            notes = notes,
            sortType = sortType,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteState())



    fun onEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    dao.deleteNote(event.note)
                }
            }
            NoteEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingNote = false
                ) }
            }
            NoteEvent.SaveNote -> {
                val title = state.value.title
                val content = state.value.content
                val podcastId = state.value.podcastId

                if (title.isBlank() || content.isBlank() || podcastId.isBlank()) {
                    return
                }
                val note = Note(
                    title = title,
                    content = content,
                    podcastId = state.value.podcastId
                )
                viewModelScope.launch {
                    dao.upsertNote(note)
                }
                _state.update { it.copy(
                    isAddingNote = false,
                    title = "",
                    content = "",
                    podcastId = ""
                ) }
            }
            is NoteEvent.SetContent -> {
                _state.update { it.copy(
                    content = event.content
                ) }
            }
            is NoteEvent.SetTitle -> {
                _state.update { it.copy(
                    title = event.title
                ) }
            }
            is NoteEvent.SetPodcastId -> {
                _state.update { it.copy(
                    podcastId = event.podcastId
                ) }
            }
            NoteEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingNote = true
                ) }
            }
            is NoteEvent.SortNotes -> {
                _sortType.value = event.sortType
            }
        }
    }
}