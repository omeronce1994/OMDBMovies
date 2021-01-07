package nando.android.core.data.datasource.moviedetails

import kotlinx.coroutines.flow.Flow
import nando.android.core.model.Resource
import nando.android.core.model.db.entities.MovieEntity
import nando.android.core.model.movies.MovieModel


interface MovieDetailsDataSource {
    /**
     * get Single Movie
     *
     * @param imdbId
     * @return
     */
    suspend fun getMovieById(imdbId: String): Flow<Resource<MovieModel>>

    /**
     * Save movie to data source
     *
     * @param movieModel
     * @return
     */
    suspend fun saveMovie(movieModel: MovieModel): Flow<Resource<Unit>>

    /**
     * delete movie from data source
     *
     * @param movieModel
     * @return
     */
    suspend fun deleteMovie(movieModel: MovieModel): Flow<Resource<Unit>>
}