package nando.android.core.data.datasource.moviedetails

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import nando.android.core.mapper.Mapper
import nando.android.core.mapper.MovieResponseToMovieModelMapper
import nando.android.core.model.Resource
import nando.android.core.model.db.entities.MovieEntity
import nando.android.core.model.movies.MovieModel
import nando.android.core.model.network.response.moviedetails.MovieDetailsResponse
import nando.android.core.model.network.service.OMDBService
import nando.android.core.model.network.util.safeApiCall
import nando.android.core.util.CoreConstants

class RemoteMovieDetailsDataSource(
    private val api: OMDBService,
    private val localDataSource: MovieDetailsDataSource
) : MovieDetailsDataSource {

    override suspend fun getMovieById(imdbId: String): Flow<Resource<MovieModel>> = flow {
        val resource = safeApiCall {
            val response = api.getMovieById(imdbId)
            if (response.isSuccessful == CoreConstants.OMBD_FAILED_RESPONSE) {
                throw Exception(response.errorMessage)
            }
            response
        }
        val isFavourite = isMovieInFavourites(imdbId)
        val result = transformApiCall(resource, isFavourite)
        emit(result)
    }

    override suspend fun saveMovie(movieModel: MovieModel): Flow<Resource<Unit>> = flow {
        val resource = safeApiCall {
            throw Exception("Favourite Movie not implmented in server")
        }
    }

    override suspend fun deleteMovie(movieModel: MovieModel): Flow<Resource<Unit>> = flow {
        val resource = safeApiCall {
            throw Exception("Favourite Movie not implmented in server")
        }
    }

    private suspend fun isMovieInFavourites(imdbId: String): Boolean {
        var movieEntity: Resource<MovieModel> = Resource.Loading()
        localDataSource.getMovieById(imdbId).collect {
            movieEntity = it
        }
        return movieEntity is Resource.Success<*>
    }

    private suspend fun transformApiCall(
        resource: Resource<MovieDetailsResponse>,
        isFavourite: Boolean
    ): Resource<MovieModel> = when (resource) {
        is Resource.Success -> {
            val mapper = MovieResponseToMovieModelMapper(isFavourite)
            Resource.Success(mapper.map(resource.data))
        }
        is Resource.Error -> Resource.Error(resource.errorHandler)
        else -> Resource.Loading()
    }
}