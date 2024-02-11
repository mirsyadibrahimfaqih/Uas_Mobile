package com.irsyad.filmku.data.network

import com.irsyad.filmku.data.response.PopulerResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response

interface ApiService {
    @GET("discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc")
    suspend fun getAllPopuler(@Query("api_key") apiKey: String): Response<PopulerResponse>
}
