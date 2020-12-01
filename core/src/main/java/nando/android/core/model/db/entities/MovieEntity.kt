package nando.android.core.model.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_favourites")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val imdbId: String,
    val title: String,
    val releaseDate: String,
    val director: String,
    val runtime: String,
    val genre: String,
    val actors: String,
    val plot: String,
    val metaScore: String,
    val imagePath: String
)