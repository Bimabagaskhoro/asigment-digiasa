package com.bimabagaskhoro.asigmentdigiasa.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.bimabagaskhoro.asigmentdigiasa.data.repository.MovieRepository
import com.bimabagaskhoro.asigmentdigiasa.data.source.local.entity.MovieEntity
import com.bimabagaskhoro.asigmentdigiasa.data.source.remote.response.ResultsItem

class FavoriteViewModel (private val movieRepository: MovieRepository) : ViewModel() {

    fun getFavListMovie(): LiveData<PagedList<MovieEntity>> =
        movieRepository.getMoviesFav()

    fun setFavListMovie(movieEntity: MovieEntity) {
        val newState = !movieEntity.addFav
        movieRepository.setMoviesFav(movieEntity, newState)
    }
}