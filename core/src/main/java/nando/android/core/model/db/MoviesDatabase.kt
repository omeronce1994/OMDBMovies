package nando.android.core.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import nando.android.core.model.db.dao.MoviesDao
import nando.android.core.model.db.entities.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1
)
abstract class MoviesDatabase: RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
}