package nando.android.movies.model.moviesearch.mapper

import android.util.Log
import kotlinx.coroutines.flow.collect
import nando.android.core.data.datasource.moviedetails.MovieDetailsDataSource
import nando.android.core.mapper.Mapper
import nando.android.core.model.Resource
import nando.android.core.model.db.entities.MovieEntity
import nando.android.core.model.movies.MovieModel
import nando.android.core.model.network.response.moviesearch.MovieSearchResult
import nando.android.movies.model.moviesearch.MovieThumbnailModel

/**
 * Mapper to map db model to feature model
 *
 * @property localDataSource
 */
class MovieThumnailMapper(
    private val localDataSource: MovieDetailsDataSource
): Mapper<MovieSearchResult, MovieThumbnailModel> {

    override suspend fun map(from: MovieSearchResult): MovieThumbnailModel {
        val id = from.imdbID
        var result: Resource<MovieModel> = Resource.Loading()
        //if movie is in db mark it as favourite
        localDataSource.getMovieById(id).collect {
            result = it
        }
        val isFavourite = result is Resource.Success<*>
        return MovieThumbnailModel(
            from.imdbID,
            from.year,
            from.poster,
            isFavourite
        )
    }
}