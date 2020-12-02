package nando.android.core.data.repository.moviedetails

import kotlinx.coroutines.flow.*
import nando.android.core.data.datasource.moviedetails.MovieDetailsDataSource
import nando.android.core.model.Resource
import nando.android.core.model.db.entities.MovieEntity

class MovieDetailsRepositoryImpl(
    private val localDataSource: MovieDetailsDataSource,
    private val remoteDataSource: MovieDetailsDataSource
): MovieDetailsRepository {

    override suspend fun getMovieById(imdbId: String): Flow<Resource<MovieEntity>> = flow {
        //first check if movie already saved locally. if so emit chached movie
        localDataSource.getMovieById(imdbId).collect {
            if (it is Resource.Success) {
                emit(it)
            }
        }
        //force update favourite movie from network to keep it up to date
        remoteDataSource.getMovieById(imdbId).collect {
            if (it is Resource.Success) {
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
    override suspend fun saveMovie(movieEntity: MovieEntity): Flow<Resource<Unit>>
        = localDataSource.saveMovie(movieEntity)

    /**
     * remove movie from local db
     *
     * @param movieEntity
     * @return
     */
    override suspend fun deleteMovie(movieEntity: MovieEntity): Flow<Resource<Unit>>
        = localDataSource.deleteMovie(movieEntity)
}