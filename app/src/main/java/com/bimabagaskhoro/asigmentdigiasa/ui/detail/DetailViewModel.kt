package com.bimabagaskhoro.asigmentdigiasa.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bimabagaskhoro.asigmentdigiasa.data.repository.MovieRepository
import com.bimabagaskhoro.asigmentdigiasa.data.source.local.entity.MovieEntity
import com.bimabagaskhoro.asigmentdigiasa.vo.Resource

class DetailViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private lateinit var dataDetail: LiveData<Resource<MovieEntity>>

    fun setDetailMovie(movieId: Int)  : LiveData<Resource<MovieEntity>>{
        dataDetail = movieRepository.loadDetailMovie(movieId)
        return dataDetail
    }

    fun setMovieFavorite() {
        val dataMovie = dataDetail.value
        if (dataMovie?.data != null) {
            val newState = !dataMovie.data.addFav
            movieRepository.setMoviesFav(dataMovie.data, newState)
        }
    }
}