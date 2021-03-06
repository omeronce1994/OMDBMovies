package nando.android.core.model.movies

data class MovieModel (
    val imdbId: String = "",
    val title: String = "",
    val releaseDate: String = "",
    val director: String = "",
    val runtime: String = "",
    val genre: String = "",
    val actors: String = "",
    val plot: String = "",
    val metaScore: String = "",
    val imagePath: String = "",
    val isFavourite: Boolean = false,
    val type: String = "",
    val year: String = ""
)