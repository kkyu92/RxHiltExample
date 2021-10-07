package com.rx.network

import com.rx.model.Movies
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface MovieService {
    @GET("trending/all/day")
    fun getTrendingMovie(): Single<Movies>
}