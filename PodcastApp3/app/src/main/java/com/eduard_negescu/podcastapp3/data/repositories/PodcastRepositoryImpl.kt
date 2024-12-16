package com.eduard_negescu.podcastapp3.data.repositories

import com.eduard_negescu.podcastapp3.data.PodcastApi
import com.eduard_negescu.podcastapp3.data.model.BestPodcasts
import com.eduard_negescu.podcastapp3.data.Result
import com.eduard_negescu.podcastapp3.data.model.Podcast
import com.eduard_negescu.podcastapp3.data.model.PodcastDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class PodcastRepositoryImpl(
    private val api: PodcastApi
) : PodcastRepository {
    override suspend fun getBestPodcasts(): Flow<Result<List<Podcast>>> {
        return flow {
            val response = try {
                api.getBestPodcasts()
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "Something went wrong"))
                return@flow
            }
            emit(Result.Success(response.podcasts))
        }
    }

    override suspend fun getPodcastDetails(id: String): Flow<Result<PodcastDetails>> {
        return flow {
            val response = try {
                api.getPodcastDetails(id)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "Something went wrong"))
                return@flow
            }
            emit(Result.Success(response))
        }
    }

}