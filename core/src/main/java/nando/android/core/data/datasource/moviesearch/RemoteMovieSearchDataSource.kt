package nando.android.core.data.datasource.moviesearch

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import nando.android.core.data.datasource.moviedetails.MovieDetailsDataSource
import nando.android.core.mapper.MovieSearchResultToMovieModelMapper
import nando.android.core.model.Resource
import nando.android.core.model.movies.MovieModel
import nando.android.core.model.network.service.OMDBService
import nando.android.core.model.network.util.safeApiCall
import nando.android.core.util.CoreConstants.OMBD_FAILED_RESPONSE
import java.lang.Exception

class RemoteMovieSearchDataSource(
    private val api: OMDBService,
    private val localDataSource: MovieDetailsDataSource
): MovieSearchDataSource {

    override suspend fun searchMovie(query: String, page: Int) = flow {
        val result = safeApiCall {
            val response = api.searchMovies(query, page.toString())
            if (response.isSuccessful == OMBD_FAILED_RESPONSE) {
                throw Exception(response.errorMessage)
            }
            //map network response to required model
            var dbResult: Resource<MovieModel> = Resource.Loading()
            response.searchResults.map {
                localDataSource.getMovieById(it.imdbID).collect { dbResult = it }
                val isFavourite = dbResult is Resource.Success<*>
                MovieSearchResultToMovieModelMapper(isFavourite).map(it)
            }
        }
        emit(result)
    }
}