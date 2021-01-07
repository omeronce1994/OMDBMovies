package nando.android.core.data.repository.moviedetails

import kotlinx.coroutines.flow.Flow
import nando.android.core.model.Resource
import nando.android.core.model.movies.MovieModel

/**
 * Use this repository to coordinate between data sources
 *
 */
interface MovieDetailsRepository {
    suspend fun getMovieById(imdbId: String): Flow<Resource<MovieModel>>
    suspend fun saveMovie(movieModel: MovieModel): Flow<Resource<Unit>>
    suspend fun deleteMovie(movieModel: MovieModel): Flow<Resource<Unit>>
}