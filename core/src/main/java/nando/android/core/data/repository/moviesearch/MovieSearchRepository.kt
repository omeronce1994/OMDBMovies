package nando.android.core.data.repository.moviesearch

import kotlinx.coroutines.flow.Flow
import nando.android.core.model.Resource
import nando.android.core.model.movies.MovieModel
import nando.android.core.model.network.response.moviesearch.MoviesSearchResponse

interface MovieSearchRepository {
    suspend fun searchMovie(query: String, page: Int): Flow<Resource<List<MovieModel>>>
}