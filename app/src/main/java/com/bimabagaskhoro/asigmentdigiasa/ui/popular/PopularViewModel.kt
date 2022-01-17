package com.bimabagaskhoro.asigmentdigiasa.ui.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.bimabagaskhoro.asigmentdigiasa.data.repository.MovieRepository
import com.bimabagaskhoro.asigmentdigiasa.data.source.local.entity.MovieEntity
import com.bimabagaskhoro.asigmentdigiasa.data.source.remote.response.ResultsItem
import com.bimabagaskhoro.asigmentdigiasa.vo.Resource

class PopularViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getPopularMovie(): LiveData<Resource<PagedList<MovieEntity>>> =
        movieRepository.loadPopularMovie()
}