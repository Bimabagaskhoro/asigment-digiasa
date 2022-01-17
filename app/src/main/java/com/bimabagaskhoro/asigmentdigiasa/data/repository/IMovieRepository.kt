package com.bimabagaskhoro.asigmentdigiasa.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.bimabagaskhoro.asigmentdigiasa.data.source.local.entity.MovieEntity
import com.bimabagaskhoro.asigmentdigiasa.vo.Resource

interface IMovieRepository {

    fun loadPopularMovie(): LiveData<Resource<PagedList<MovieEntity>>>

    fun loadTopRatedMovie(): LiveData<Resource<PagedList<MovieEntity>>>

    fun loadDetailMovie(movieId: Int): LiveData<Resource<MovieEntity>>

   // fun loadFavoriteMovie(): LiveData<List<ResultsItem>>

    fun setMoviesFav(movie: MovieEntity, state: Boolean)

    fun getMoviesFav(): LiveData<PagedList<MovieEntity>>
}