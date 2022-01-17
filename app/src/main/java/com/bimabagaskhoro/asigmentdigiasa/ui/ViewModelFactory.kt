package com.bimabagaskhoro.asigmentdigiasa.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bimabagaskhoro.asigmentdigiasa.data.repository.MovieRepository
import com.bimabagaskhoro.asigmentdigiasa.di.Injection
import com.bimabagaskhoro.asigmentdigiasa.ui.detail.DetailViewModel
import com.bimabagaskhoro.asigmentdigiasa.ui.favorite.FavoriteViewModel
import com.bimabagaskhoro.asigmentdigiasa.ui.popular.PopularViewModel
import com.bimabagaskhoro.asigmentdigiasa.ui.toprated.TopRatedViewModel

class ViewModelFactory (private val movieRepository: MovieRepository) :
ViewModelProvider.NewInstanceFactory(){

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context)).apply {
                    instance = this
                }
            }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PopularViewModel::class.java) -> {
                PopularViewModel(movieRepository) as T
            }
            modelClass.isAssignableFrom(TopRatedViewModel::class.java) -> {
                TopRatedViewModel(movieRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(movieRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(movieRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    }
}