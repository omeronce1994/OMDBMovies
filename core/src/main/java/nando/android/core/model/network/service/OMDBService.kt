package nando.android.core.model.network.service

import nando.android.core.model.network.response.moviedetails.MovieDetailsResponse
import nando.android.core.model.network.response.moviesearch.MoviesSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OMDBService {

    @GET(".")
    suspend fun searchMovies(
        @Query("s") query: String,
        @Query("page") page: String,
        @Query("apikey") apiKey: String
    ): MoviesSearchResponse

    @GET(".")
    suspend fun getMovieById(
        @Query("i") movieId: String
    ): MovieDetailsResponse
}