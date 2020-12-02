package nando.android.movies.model.moviedetails.mapper

import kotlinx.coroutines.flow.collect
import nando.android.core.data.datasource.moviedetails.MovieDetailsDataSource
import nando.android.core.mapper.Mapper
import nando.android.core.model.Resource
import nando.android.core.model.db.entities.MovieEntity
import nando.android.movies.model.moviedetails.MovieItemModel

class MovieItemMapper(
    private val localDataSource: MovieDetailsDataSource
): Mapper<MovieEntity, MovieItemModel> {

    override suspend fun map(from: MovieEntity): MovieItemModel {
        val id = from.imdbId
        var result: Resource<MovieEntity> = Resource.Loading()
        localDataSource.getMovieById(id).collect {
            result = it
        }
        val isFavourite = result is Resource.Success<*>
        return MovieItemModel(
            from.imdbId,
            from.title,
            from.releaseDate,
            from.director,
            from.runtime,
            from.genre,
            from.actors,
            from.plot,
            from.metaScore,
            from.imagePath,
            isFavourite
        )
    }
}