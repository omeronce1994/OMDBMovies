package nando.android.core.data.repository.moviesearch

import nando.android.core.data.datasource.moviesearch.MovieSearchDataSource

class MovieSearchRepositoryImpl(
    private val movieSearchDataSource: MovieSearchDataSource
): MovieSearchRepository {

    override suspend fun searchMovie(
        query: String,
        page: Int
    ) = movieSearchDataSource.searchMovie(query, page)
}