package com.example.spoetz

import com.example.spoetz.model.Item
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("e4049e95-62fc-4fee-bd82-03ac833af40a") // Endpoint for the movies JSON
    fun getMovies(): Call<List<Item>>

    companion object {
        private const val BASE_URL = "https://run.mocky.io/v3/"

        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}