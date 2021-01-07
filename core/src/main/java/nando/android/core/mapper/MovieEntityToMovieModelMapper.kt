package nando.android.core.mapper

import nando.android.core.model.db.entities.MovieEntity
import nando.android.core.model.movies.MovieModel

class MovieEntityToMovieModelMapper: Mapper<MovieEntity, MovieModel> {

    override suspend fun map(from: MovieEntity): MovieModel = MovieModel(
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
        true //if movie is in our db it means a favourite movie
    )

}