package nando.android.core.data.datasource.moviesearch

import kotlinx.coroutines.flow.Flow
import nando.android.core.model.Resource
import nando.android.core.model.movies.MovieModel
import nando.android.core.model.network.response.moviesearch.MoviesSearchResponse

/**
 * For movie search data source we return network response object
 * as our single source of truth in this use case is the network(no local db or cache)
 *
 */
interface MovieSearchDataSource {
    suspend fun searchMovie(query: String, page: Int): Flow<Resource<List<MovieModel>>>
}