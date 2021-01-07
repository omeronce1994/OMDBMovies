package nando.android.core.data.repository.moviedetails

import kotlinx.coroutines.flow.*
import nando.android.core.data.datasource.moviedetails.MovieDetailsDataSource
import nando.android.core.model.Resource
import nando.android.core.model.db.entities.MovieEntity
import nando.android.core.model.movies.MovieModel

class MovieDetailsRepositoryImpl(
    private val localDataSource: MovieDetailsDataSource,
    private val remoteDataSource: MovieDetailsDataSource
): MovieDetailsRepository {

    override suspend fun getMovieById(imdbId: String): Flow<Resource<MovieModel>> = flow {
        //first check if movie already saved locally. if so emit chached movie
        localDataSource.getMovieById(imdbId).collect {
            if (it is Resource.Success) {
                emit(it)
            }
        }
        //force update favourite movie from network to keep it up to date
        remoteDataSource.getMovieById(imdbId).collect {
            if (it is Resource.Success && it.data.isFavourite) {
                localDataSource.saveMovie(it.data)
            }
            emit(it)
        }
    }

    /**
     * Save movie to local db
     *
     * @param movieEntity
     * @return
     */
    override suspend fun saveMovie(movieModel: MovieModel): Flow<Resource<Unit>>
        = localDataSource.saveMovie(movieModel)

    /**
     * remove movie from local db
     *
     * @param movieEntity
     * @return
     */
    override suspend fun deleteMovie(movieModel: MovieModel): Flow<Resource<Unit>>
        = localDataSource.deleteMovie(movieModel)
}