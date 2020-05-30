package com.oguncan.moviemvvm.ui.single_movie_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.oguncan.moviemvvm.R
import com.oguncan.moviemvvm.data.api.POSTER_BASE_URL
import com.oguncan.moviemvvm.data.api.TheMovieDBClient
import com.oguncan.moviemvvm.data.api.TheMovieDBInterface
import com.oguncan.moviemvvm.data.repository.NetworkState
import com.oguncan.moviemvvm.data.vo.MovieDetails
import kotlinx.android.synthetic.main.activity_single_movie.*
import java.text.NumberFormat
import java.util.*

class SingleMovie : AppCompatActivity() {
    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository : MovieDetailsRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)
        val movieId : Int = intent.getIntExtra("id",1)

        val apiService : TheMovieDBInterface = TheMovieDBClient.getClient()
        movieRepository = MovieDetailsRepository(apiService)
        viewModel = getViewModel(movieId)
        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })
        viewModel.networkState.observe(this, Observer {
            progressBar.visibility = if(it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txtError.visibility = if(it == NetworkState.LOADING) View.VISIBLE else View.GONE
        })
    }

    fun bindUI(movieDetails : MovieDetails){
        txtMovieTitle.text = movieDetails.title
        txtTagLine.text = movieDetails.tagline
        movie_release_date.text = movieDetails.releaseDate
        movie_rating.text = movieDetails.rating.toString()
        movie_runtime.text = movieDetails.runtime.toString() + "minutes"
        movie_overview.text = movieDetails.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.getDefault())
        movie_revenue.text = formatCurrency.format(movieDetails.revenue)
        movie_budget.text = formatCurrency.format(movieDetails.budget)

        val moviePosterURL = POSTER_BASE_URL + movieDetails.posterPath
        println(movieDetails.posterPath)
        Glide.with(this)
            .load(moviePosterURL)
            .into(imgMoviePoster)

    }




    private fun getViewModel(movieId : Int) : SingleMovieViewModel{
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(movieRepository,movieId) as T
            }
        })[SingleMovieViewModel::class.java]

    }
}
