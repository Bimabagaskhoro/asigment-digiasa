package com.bimabagaskhoro.asigmentdigiasa.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.bimabagaskhoro.asigmentdigiasa.data.source.local.entity.MovieEntity


@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_entity")
    fun getMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movie_entity WHERE addFav = 1")
    fun getFavMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movie_entity WHERE id = :id")
    fun getMoviesById(id: Int): LiveData<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: List<MovieEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)
}