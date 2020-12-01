package nando.android.core.data.datasource.moviedetails

import kotlinx.coroutines.flow.Flow
import nando.android.core.model.Resource
import nando.android.core.model.db.entities.MovieEntity

/**
 * For movie details data source we return db entity object
 * as our single source of truth in this use case is local
 * (we save favourites movies in local db)
 *
 */
interface MovieDetailsDataSource {
    suspend fun getMovieById(imdbId: String): Flow<Resource<MovieEntity>>
    suspend fun saveMovie(movieEntity: MovieEntity): Flow<Resource<Unit>>
    suspend fun deleteMovie(movieEntity: MovieEntity): Flow<Resource<Unit>>
}