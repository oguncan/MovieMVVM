package com.oguncan.moviemvvm.ui.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.oguncan.moviemvvm.data.api.POST_PER_PAGE
import com.oguncan.moviemvvm.data.api.TheMovieDBInterface
import com.oguncan.moviemvvm.data.repository.MovieDataSource
import com.oguncan.moviemvvm.data.repository.MovieDatasourceFactory
import com.oguncan.moviemvvm.data.repository.NetworkState
import com.oguncan.moviemvvm.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository(private val apiService : TheMovieDBInterface) {

    lateinit var moviePagedList : LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory : MovieDatasourceFactory

    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>>{
        moviesDataSourceFactory = MovieDatasourceFactory(apiService, compositeDisposable)

        val config : PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList
    }

    fun getNetworkState() : LiveData<NetworkState>{
        return Transformations.switchMap<MovieDataSource, NetworkState >(
            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState)
    }
}