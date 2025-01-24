package com.example.myapplication

import retrofit2.Response
import retrofit2.http.GET

interface ApiTest {
    @GET("/v2/list")
    suspend fun getRandomImage(): Response<List<ImageInfo>>
}