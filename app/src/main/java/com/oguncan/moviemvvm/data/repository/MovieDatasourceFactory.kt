package com.oguncan.moviemvvm.data.repository

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.oguncan.moviemvvm.data.api.TheMovieDBInterface
import com.oguncan.moviemvvm.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable


class MovieDatasourceFactory(private val apiService : TheMovieDBInterface, private val compositeDisposable: CompositeDisposable) : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService, compositeDisposable)
        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }


}