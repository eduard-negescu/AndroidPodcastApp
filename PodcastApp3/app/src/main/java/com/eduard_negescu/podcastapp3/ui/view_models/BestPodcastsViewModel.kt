package com.eduard_negescu.podcastapp3.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.eduard_negescu.podcastapp3.RetrofitInstance
import com.eduard_negescu.podcastapp3.data.model.Podcast
import com.eduard_negescu.podcastapp3.data.repositories.PodcastRepository
import com.eduard_negescu.podcastapp3.data.Result
import com.eduard_negescu.podcastapp3.data.repositories.PodcastRepositoryImpl
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BestPodcastsViewModel(
    private val podcastRepository: PodcastRepository
): ViewModel() {
    private val _podcasts = MutableStateFlow<List<Podcast>>(emptyList())
    val podcasts = _podcasts.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            podcastRepository.getBestPodcasts().collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        _showErrorToastChannel.send(true)
                    }
                    is Result.Success -> {
                        result.data?.let { podcasts ->
                            _podcasts.update { podcasts }
                        }
                    }
                }

            }
        }
    }
}

class BestPodcastsViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BestPodcastsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BestPodcastsViewModel(PodcastRepositoryImpl(RetrofitInstance.api)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}