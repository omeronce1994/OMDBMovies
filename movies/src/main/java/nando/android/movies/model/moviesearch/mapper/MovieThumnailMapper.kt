package nando.android.movies.model.moviesearch.mapper

import android.util.Log
import kotlinx.coroutines.flow.collect
import nando.android.core.data.datasource.moviedetails.MovieDetailsDataSource
import nando.android.core.mapper.Mapper
import nando.android.core.model.Resource
import nando.android.core.model.db.entities.MovieEntity
import nando.android.core.model.network.response.moviesearch.MovieSearchResult
import nando.android.movies.model.moviesearch.MovieThumbnailModel

class MovieThumnailMapper(
    private val localDataSource: MovieDetailsDataSource
): Mapper<MovieSearchResult, MovieThumbnailModel> {

    private val TAG = "MovieThumnailMapper"

    override suspend fun map(from: MovieSearchResult): MovieThumbnailModel {
        val id = from.imdbID
        var result: Resource<MovieEntity> = Resource.Loading()
        Log.i(TAG, "map: call collect")
        localDataSource.getMovieById(id).collect {
            result = it
            Log.i(TAG, "map: clloected")
        }
        Log.i(TAG, "map: after collect called")
        val isFavourite = result is Resource.Success<*>
        return MovieThumbnailModel(
            from.year,
            from.poster,
            isFavourite
        )
    }
}