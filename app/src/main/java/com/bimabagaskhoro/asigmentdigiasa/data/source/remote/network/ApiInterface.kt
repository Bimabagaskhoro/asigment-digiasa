package com.bimabagaskhoro.asigmentdigiasa.data.source.remote.network

import com.bimabagaskhoro.asigmentdigiasa.data.source.remote.response.MovieResponse
import com.bimabagaskhoro.asigmentdigiasa.data.source.remote.response.ResultsDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    companion object {
        const val API_KEY = "8e4d8c7c15bf8e9ea2ca67a08f863a31"
        const val SESSION_ID = "45f0cba2f22dc321d4acbe8a153e1e4f1b92aaee"
        const val KEY_HELPER = "language=en-US&sort_by=created_at.asc&page=1"
    }

    //Get Movie Popular
    @GET("movie/popular?api_key=$API_KEY")
    fun getMoviePopular(): Call<MovieResponse>

    //Get Movie Top Rated
    @GET("movie/top_rated?api_key=$API_KEY")
    fun getMovieTopRated(): Call<MovieResponse>

    @GET("tv/{movie_id}?api_key=$API_KEY")
    fun getDetailMovie(
        @Path("movie_id") movie_id: String
    ): Call<ResultsDetail>

}