package com.rx.network

import com.rx.model.Movie
import com.rx.model.Movies
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {
    @GET("trending/all/day")
    fun getTrendingMovie(): Single<Movies>

    @GET("movie/{movieId}")
    fun getMovie(@Path("movieId") movieId: Long): Single<Movie>
}