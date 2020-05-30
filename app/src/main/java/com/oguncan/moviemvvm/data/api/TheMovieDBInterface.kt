package com.oguncan.moviemvvm.data.api

import com.oguncan.moviemvvm.data.vo.MovieDetails
import com.oguncan.moviemvvm.data.vo.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface TheMovieDBInterface {
    //https://api.themoviedb.org/3/movie/popular?api_key=a580dfd3c27aa63174fc60a885194c50&language=tr-TR&page=1
    //http://api.themoviedb.org/3/movie/popular?api_key=a580dfd3c27aa63174fc60a885194c50&language=tr-TR&page=1
    //https://api.themoviedb.org/3/movie/419704?api_key=a580dfd3c27aa63174fc60a885194c50&language=tr-TR
    //https://api.themoviedb.org/3/

    @GET("movie/popular")
    fun getPopulerMovie(@Query("page") page: Int) : Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int) : Single<MovieDetails>

}