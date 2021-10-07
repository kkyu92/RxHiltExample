package com.rx.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Places (
    val id: Int,
    val name: String,
    val lat: String,
    val lon: String,
    @Json(name = "full_address")
    val fullAddress: String,
)