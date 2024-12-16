package com.eduard_negescu.podcastapp3.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.eduard_negescu.podcastapp3.RetrofitInstance
import com.eduard_negescu.podcastapp3.data.Result
import com.eduard_negescu.podcastapp3.data.model.PodcastDetails
import com.eduard_negescu.podcastapp3.data.repositories.PodcastRepository
import com.eduard_negescu.podcastapp3.data.repositories.PodcastRepositoryImpl
import com.eduard_negescu.podcastapp3.data.room.FavoritePodcastDao
import com.eduard_negescu.podcastapp3.data.room.model.FavoritePodcast
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PodcastDetailsViewModel(
    private val podcastRepository: PodcastRepository,
    val id: String
) : ViewModel() {
    private val _podcast = MutableStateFlow<PodcastDetails?>(null)
    val podcast = _podcast.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            podcastRepository.getPodcastDetails(id).collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        _showErrorToastChannel.send(true)
                    }
                    is Result.Success -> {
                        result.data?.let { podcast ->
                            _podcast.update { podcast }
                        }
                    }
                }

            }
        }
    }


}

class PodcastDetailsViewModelFactory(val id: String): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PodcastDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PodcastDetailsViewModel(PodcastRepositoryImpl(RetrofitInstance.api), id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}