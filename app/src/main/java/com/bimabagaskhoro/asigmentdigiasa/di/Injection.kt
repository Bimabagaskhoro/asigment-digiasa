package com.bimabagaskhoro.asigmentdigiasa.di

import android.content.Context
import com.bimabagaskhoro.asigmentdigiasa.data.repository.MovieRepository
import com.bimabagaskhoro.asigmentdigiasa.data.source.RemoteDataSource
import com.bimabagaskhoro.asigmentdigiasa.data.source.local.LocalDataSource
import com.bimabagaskhoro.asigmentdigiasa.data.source.local.room.ShowDatabase
import com.bimabagaskhoro.asigmentdigiasa.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): MovieRepository {
        val db = ShowDatabase.getInstance(context)
        val localDataSource = LocalDataSource.getInstance(db.movieDao())
        val appExecutors = AppExecutors()
        val remoteDataSource = RemoteDataSource.getInstance()

        return MovieRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}