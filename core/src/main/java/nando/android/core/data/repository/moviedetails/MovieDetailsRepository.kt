package nando.android.core.data.repository.moviedetails

import kotlinx.coroutines.flow.Flow
import nando.android.core.model.Resource
import nando.android.core.model.db.entities.MovieEntity

/**
 * Use this repository to coordinate between data sources
 *
 */
interface MovieDetailsRepository {
    suspend fun getMovieById(imdbId: String): Flow<Resource<MovieEntity>>
    suspend fun saveMovie(movieEntity: MovieEntity): Flow<Resource<Unit>>
    suspend fun deleteMovie(movieEntity: MovieEntity): Flow<Resource<Unit>>
}