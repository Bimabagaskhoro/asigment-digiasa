package com.bimabagaskhoro.asigmentdigiasa.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bimabagaskhoro.asigmentdigiasa.data.source.remote.ApiResponse
import com.bimabagaskhoro.asigmentdigiasa.data.source.remote.network.ApiClient
import com.bimabagaskhoro.asigmentdigiasa.data.source.remote.response.MovieResponse
import com.bimabagaskhoro.asigmentdigiasa.data.source.remote.response.ResultsDetail
import com.bimabagaskhoro.asigmentdigiasa.data.source.remote.response.ResultsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {
    companion object {
        const val TAG = "Remote Data Resource"

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource = instance ?: synchronized(this) {
            RemoteDataSource().apply { instance = this }
        }
    }

    fun getPopularMovie(): LiveData<ApiResponse<List<ResultsItem>>> {
        val result = MutableLiveData<ApiResponse<List<ResultsItem>>>()
        ApiClient().apiInstance().getMoviePopular()
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if (response.isSuccessful) {
                        result.value =
                            ApiResponse.success(response.body()?.results as List<ResultsItem>)
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Log.e(TAG, "Failure to get Popular ${t.message}")
                }
            })
        return result
    }

    fun getTopRatedMovie(): LiveData<ApiResponse<List<ResultsItem>>> {
        val result = MutableLiveData<ApiResponse<List<ResultsItem>>>()
        ApiClient().apiInstance().getMovieTopRated()
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if (response.isSuccessful) {
                        result.value =
                            ApiResponse.success(response.body()?.results as List<ResultsItem>)
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Log.e(TAG, "Failure to get Top Rated ${t.message}")
                }
            })
        return result
    }


    fun getDetailMovie(movieId: String): LiveData<ApiResponse<ResultsDetail>> {
        val result = MutableLiveData<ApiResponse<ResultsDetail>>()
        ApiClient().apiInstance().getDetailMovie(movieId)
            .enqueue(object : Callback<ResultsDetail> {
                override fun onResponse(
                    call: Call<ResultsDetail>,
                    response: Response<ResultsDetail>
                ) {
                    if (response.isSuccessful) {
                        result.value = ApiResponse.success(response.body() as ResultsDetail)
                    }
                }

                override fun onFailure(call: Call<ResultsDetail>, t: Throwable) {
                    Log.e(TAG, "Failure to get Detail Movie ${t.message}")
                }
            })
        return result
    }
}