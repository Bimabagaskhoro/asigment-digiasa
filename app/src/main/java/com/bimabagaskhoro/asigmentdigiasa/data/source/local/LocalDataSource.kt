package com.bimabagaskhoro.asigmentdigiasa.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.bimabagaskhoro.asigmentdigiasa.data.source.local.entity.MovieEntity
import com.bimabagaskhoro.asigmentdigiasa.data.source.local.room.MovieDao

class LocalDataSource private constructor(private val movieDao: MovieDao) {
    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieDao: MovieDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieDao).apply {
                INSTANCE = this
            }
    }

    fun getAllDataMovie(): DataSource.Factory<Int, MovieEntity> =
        movieDao.getMovies()

    fun getFavMovies(): DataSource.Factory<Int, MovieEntity> = movieDao.getFavMovies()

    fun getMovieById(id: Int): LiveData<MovieEntity> = movieDao.getMoviesById(id)

    fun insertMovie(movies: List<MovieEntity>) = movieDao.insertMovie(movies)

    fun updateFavMovie(movies: MovieEntity, newState: Boolean) {
        movies.addFav = newState
        movieDao.updateMovie(movies)
    }
}