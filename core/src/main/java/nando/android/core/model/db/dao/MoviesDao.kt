package nando.android.core.model.db.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import nando.android.core.model.db.entities.MovieEntity

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies_favourites")
    fun getMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies_favourites WHERE id = :imdbId")
    suspend fun getMovieById(imdbId: String): MovieEntity?

    @Delete
    suspend fun deleteMovie(movieEntity: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieEntity: MovieEntity)
}