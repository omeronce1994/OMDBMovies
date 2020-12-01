package nando.android.core.model.network.response.moviesearch


import com.google.gson.annotations.SerializedName

data class MoviesSearchResponse(
    @SerializedName("Error")
    val errorMessage: String = "",
    @SerializedName("Response")
    val isSuccessful: String = "",
    @SerializedName("Search")
    val searchResults: List<MovieSearchResult> = listOf(),
    @SerializedName("totalResults")
    val totalResults: String = "0"
)