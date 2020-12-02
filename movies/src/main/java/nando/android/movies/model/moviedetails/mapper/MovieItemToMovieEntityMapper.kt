package nando.android.movies.model.moviedetails.mapper

import nando.android.core.mapper.Mapper
import nando.android.core.model.db.entities.MovieEntity
import nando.android.movies.model.moviedetails.MovieItemModel

class MovieItemToMovieEntityMapper: Mapper<MovieItemModel, MovieEntity> {

    override suspend fun map(from: MovieItemModel): MovieEntity
        = MovieEntity(
        from.imdbId,
        from.title,
        from.releaseDate,
        from.director,
        from.runtime,
        from.genre,
        from.actors,
        from.plot,
        from.metaScore,
        from.imagePath
    )
}