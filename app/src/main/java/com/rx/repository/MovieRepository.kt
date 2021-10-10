package com.rx.repository

import com.rx.model.Movie
import com.rx.model.Movies
import com.rx.network.MovieService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieService: MovieService
) {
    fun getTrendingMovie(): Single<Movies> {
        return movieService
            .getTrendingMovie()
            .subscribeOn(Schedulers.io())
    }

    fun getMovieDetails(movieId: Long): Single<Movie> {
        return movieService
            .getMovie(movieId)
            .subscribeOn(Schedulers.io())
    }
}