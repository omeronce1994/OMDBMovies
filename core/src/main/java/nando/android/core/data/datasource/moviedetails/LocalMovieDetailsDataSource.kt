package nando.android.core.data.datasource.moviedetails

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nando.android.core.mapper.Mapper
import nando.android.core.mapper.MovieEntityToMovieModelMapper
import nando.android.core.mapper.MovieModelToMovieEntityMapper
import nando.android.core.model.Resource
import nando.android.core.model.db.dao.MoviesDao
import nando.android.core.model.db.entities.MovieEntity
import nando.android.core.model.db.util.safeDbCall
import nando.android.core.model.movies.MovieModel

class LocalMovieDetailsDataSource(
    private val moviesDao: MoviesDao,
    private val mapper: Mapper<MovieEntity, MovieModel> = MovieEntityToMovieModelMapper(),
    private val reversedMapper: Mapper<MovieModel, MovieEntity> = MovieModelToMovieEntityMapper()
): MovieDetailsDataSource {

    override suspend fun getMovieById(imdbId: String): Flow<Resource<MovieModel>> = flow {
        val result = safeDbCall {
            val movieEntity = moviesDao.getMovieById(imdbId) ?: throw Exception("Item Not Found")
            mapper.map(movieEntity)
        }
        emit(result)
    }

    override suspend fun saveMovie(movieModel: MovieModel): Flow<Resource<Unit>> = flow {
        val result = safeDbCall {
            moviesDao.insertMovie(reversedMapper.map(movieModel))
        }
        emit(result)
    }

    override suspend fun deleteMovie(movieModel: MovieModel): Flow<Resource<Unit>> = flow {
        val result = safeDbCall {
            moviesDao.deleteMovie(reversedMapper.map(movieModel))
        }
        emit(result)
    }
}