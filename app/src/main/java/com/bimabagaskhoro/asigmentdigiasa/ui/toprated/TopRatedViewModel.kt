package com.bimabagaskhoro.asigmentdigiasa.ui.toprated

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.bimabagaskhoro.asigmentdigiasa.data.repository.MovieRepository
import com.bimabagaskhoro.asigmentdigiasa.data.source.local.entity.MovieEntity
import com.bimabagaskhoro.asigmentdigiasa.data.source.remote.response.ResultsItem
import com.bimabagaskhoro.asigmentdigiasa.vo.Resource

class TopRatedViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getTopRatedMovie(): LiveData<Resource<PagedList<MovieEntity>>> =
        movieRepository.loadTopRatedMovie()
}