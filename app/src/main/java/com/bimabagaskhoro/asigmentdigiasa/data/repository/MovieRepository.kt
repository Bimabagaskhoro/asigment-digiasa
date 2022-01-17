package com.bimabagaskhoro.asigmentdigiasa.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.bimabagaskhoro.asigmentdigiasa.data.NetworkBoundResource
import com.bimabagaskhoro.asigmentdigiasa.data.source.RemoteDataSource
import com.bimabagaskhoro.asigmentdigiasa.data.source.local.LocalDataSource
import com.bimabagaskhoro.asigmentdigiasa.data.source.local.entity.MovieEntity
import com.bimabagaskhoro.asigmentdigiasa.data.source.remote.ApiResponse
import com.bimabagaskhoro.asigmentdigiasa.data.source.remote.response.ResultsDetail
import com.bimabagaskhoro.asigmentdigiasa.data.source.remote.response.ResultsItem
import com.bimabagaskhoro.asigmentdigiasa.utils.AppExecutors
import com.bimabagaskhoro.asigmentdigiasa.vo.Resource

class MovieRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
) : IMovieRepository {

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(
            remote: RemoteDataSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors,
        ): MovieRepository =
            instance ?: synchronized(this) {
                MovieRepository(remote, localDataSource, appExecutors).apply {
                    instance = this
                }
            }
    }

    override fun loadPopularMovie(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MovieEntity>, List<ResultsItem>>(appExecutors) {
            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun loadFromDb(): LiveData<PagedList<MovieEntity>> {
                val conf = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllDataMovie(), conf).build()
            }

            override fun createCall(): LiveData<ApiResponse<List<ResultsItem>>> =
                remoteDataSource.getPopularMovie()

            override fun saveCallResult(data: List<ResultsItem>) {
                val listMovie = ArrayList<MovieEntity>()
                for (dataMovie in data) {
                    dataMovie.apply {
                        val movie = MovieEntity(
                            id,
                            title,
                            voteCount.toString(),
                            releaseDate,
                            overview,
                            originalLanguage,
                            posterPath,
                            backdropPath,
                            false
                        )
                        listMovie.add(movie)
                    }
                }
                localDataSource.insertMovie(listMovie)
            }

        }.asLiveData()
    }

    override fun loadTopRatedMovie(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MovieEntity>, List<ResultsItem>>(appExecutors) {
            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun loadFromDb(): LiveData<PagedList<MovieEntity>> {
                val conf = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllDataMovie(), conf).build()
            }

            override fun createCall(): LiveData<ApiResponse<List<ResultsItem>>> =
                remoteDataSource.getTopRatedMovie()

            override fun saveCallResult(data: List<ResultsItem>) {
                val listMovie = ArrayList<MovieEntity>()
                for (dataMovie in data) {
                    dataMovie.apply {
                        val movie = MovieEntity(
                            id,
                            title,
                            voteCount.toString(),
                            releaseDate,
                            overview,
                            originalLanguage,
                            posterPath,
                            backdropPath,
                            false
                        )
                        listMovie.add(movie)
                    }
                }
                localDataSource.insertMovie(listMovie)
            }

        }.asLiveData()
    }

    override fun loadDetailMovie(movieId: Int): LiveData<Resource<MovieEntity>> {
        return object :
            NetworkBoundResource<MovieEntity, ResultsDetail>(appExecutors) {

            override fun shouldFetch(data: MovieEntity?): Boolean = data == null
            override fun loadFromDb(): LiveData<MovieEntity> =
                localDataSource.getMovieById(movieId)

            override fun createCall(): LiveData<ApiResponse<ResultsDetail>> =
                remoteDataSource.getDetailMovie(movieId.toString())

            override fun saveCallResult(data: ResultsDetail) {
                data.apply {
                    val detailMovie = MovieEntity(
                        id,
                        title,
                        voteCount.toString(),
                        releaseDate,
                        overview,
                        originalLanguage,
                        posterPath,
                        backdropPath,
                        false
                    )
                    localDataSource.updateFavMovie(detailMovie, false)
                }
            }
        }.asLiveData()
    }

    override fun setMoviesFav(movie: MovieEntity, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.updateFavMovie(movie, state)
        }
    }

    override fun getMoviesFav(): LiveData<PagedList<MovieEntity>> {
        val conf = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return LivePagedListBuilder(localDataSource.getFavMovies(), conf).build()
    }
}