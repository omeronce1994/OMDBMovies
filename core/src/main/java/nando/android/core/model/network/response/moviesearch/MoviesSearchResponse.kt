package nando.android.core.model.network.response.moviesearch


import com.google.gson.annotations.SerializedName

data class MoviesSearchResponse(
    @SerializedName("Response")
    val isSuccessful: String = "",
    @SerializedName("Search")
    val search: List<Search> = listOf(),
    @SerializedName("totalResults")
    val totalResults: String = "0"
)