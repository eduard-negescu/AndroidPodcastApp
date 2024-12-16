package com.eduard_negescu.podcastapp3.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eduard_negescu.podcastapp3.data.room.NoteEvent
import com.eduard_negescu.podcastapp3.data.room.NoteState
import com.eduard_negescu.podcastapp3.data.room.SortType
import com.eduard_negescu.podcastapp3.ui.BackButton

@Composable
fun NoteScreen(
    state: NoteState,
    onEvent: (NoteEvent) -> Unit,
    navController: NavController
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(NoteEvent.ShowDialog)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Note"
                )
            }
        },
        modifier = Modifier.padding(30.dp)
    ) { paddingValues ->
        if (state.isAddingNote) {
            AddContentDialog(state = state, onEvent = onEvent)
        }
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    BackButton(navController)
                    SortType.entries.forEach { sortType ->
                        Row(
                            modifier = Modifier.clickable(
                                onClick = {
                                    onEvent(NoteEvent.SortNotes(sortType))
                                }
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = state.sortType == sortType, onClick = {
                                    onEvent(NoteEvent.SortNotes(sortType))
                                }
                            )
                            Text(text = sortType.name)
                        }
                    }
                }
            }

            items(state.notes) {note ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = note.title, style = MaterialTheme.typography.headlineSmall)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = note.content, style = MaterialTheme.typography.bodyLarge)
                    }
                    IconButton(onClick = {
                        onEvent(NoteEvent.DeleteNote(note))
                    } ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Note"
                        )
                    }
                }

            }

        }
    }
}

@Composable
fun AddContentDialog(
    state: NoteState,
    onEvent: (NoteEvent) -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            onEvent(NoteEvent.HideDialog)
        },
        title = { Text(text = "Add Note") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.title,
                    onValueChange = {
                        onEvent(NoteEvent.SetTitle(it))
                    },
                    placeholder = {
                        Text(text = "Title")
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = state.content,
                    onValueChange = {
                        onEvent(NoteEvent.SetContent(it))
                    },
                    placeholder = {
                        Text(text = "Content")
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = state.podcastId,
                    onValueChange = {
                        onEvent(NoteEvent.SetPodcastId(it))
                    },
                    placeholder = {
                        Text(text = "Podcast Id")
                    }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onEvent(NoteEvent.SaveNote)
                }
            ) {
                Text(text = "Save")
            }
        }
    )
}