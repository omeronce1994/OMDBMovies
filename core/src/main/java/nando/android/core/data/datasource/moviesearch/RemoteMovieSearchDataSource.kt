package nando.android.core.data.datasource.moviesearch

import kotlinx.coroutines.flow.flow
import nando.android.core.model.Resource
import nando.android.core.model.network.response.moviesearch.MoviesSearchResponse
import nando.android.core.model.network.service.OMDBService
import nando.android.core.model.network.util.safeApiCall
import nando.android.core.util.CoreConstants
import nando.android.core.util.CoreConstants.OMBD_FAILED_RESPONSE
import java.lang.Exception

class RemoteMovieSearchDataSource(
    private val api: OMDBService
): MovieSearchDataSource {

    override suspend fun searchMovie(query: String, page: Int) = flow {
        val result = safeApiCall {
            val response = api.searchMovies(query, page.toString())
            if (response.isSuccessful == OMBD_FAILED_RESPONSE) {
                throw Exception(response.errorMessage)
            }
            response
        }
        emit(result)
    }
}