package com.rx.network

import com.rx.model.Movies
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface MovieService {
    @GET("trending/all/day?api_key=25e18c8c0289572a63392742ea79c33f")
    fun getTrendingMovie(): Single<Movies>
}