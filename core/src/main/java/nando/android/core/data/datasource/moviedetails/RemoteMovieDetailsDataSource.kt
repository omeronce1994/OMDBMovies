package nando.android.core.data.datasource.moviedetails

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nando.android.core.mapper.MovieEntityMapper
import nando.android.core.model.Resource
import nando.android.core.model.db.entities.MovieEntity
import nando.android.core.model.network.response.moviedetails.MovieDetailsResponse
import nando.android.core.model.network.service.OMDBService
import nando.android.core.model.network.util.safeApiCall
import nando.android.core.util.CoreConstants

class RemoteMovieDetailsDataSource(
    private val api: OMDBService,
    private val mapper: MovieEntityMapper
): MovieDetailsDataSource {

    override suspend fun getMovieById(imdbId: String): Flow<Resource<MovieEntity>>  = flow {
        val resource = safeApiCall {
            val response = api.getMovieById(imdbId)
            if (response.isSuccessful == CoreConstants.OMBD_FAILED_RESPONSE) {
                throw Exception(response.errorMessage)
            }
            response
        }
        val result = transformApiCall(resource)
        emit(result)
    }

    override suspend fun saveMovie(movieEntity: MovieEntity): Flow<Resource<Unit>> = flow {
        val resource = safeApiCall {
            throw Exception("Favourite Movie not implmented in server")
        }
    }

    override suspend fun deleteMovie(movieEntity: MovieEntity): Flow<Resource<Unit>> = flow {
        val resource = safeApiCall {
            throw Exception("Favourite Movie not implmented in server")
        }
    }

    private suspend fun transformApiCall(resource: Resource<MovieDetailsResponse>): Resource<MovieEntity>
        = when(resource) {
        is Resource.Success -> Resource.Success(mapper.map(resource.data))
        is Resource.Error -> Resource.Error(resource.errorHandler)
        else -> Resource.Loading()
    }
}