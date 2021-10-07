package com.rx.network

import com.rx.model.Movies
import com.rx.model.Places
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface SearchService {
    // bearer token = iGR3hRvQ.K3wPNmJgqSMeY6CehCZmuq7Kg5Hnw3o7
    @GET("delivery_server/api/restaurants/query_search/")
    fun getSearchPlace(): Single<Places>
}