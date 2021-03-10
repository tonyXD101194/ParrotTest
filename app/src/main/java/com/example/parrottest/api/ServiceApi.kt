package com.example.parrottest.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceApi {

    companion object {

        private const val ENDPOINT = "http://api-staging.parrot.rest"

        const val HEADER_TAG_AUTHORIZATION = "Bearer "

        private lateinit var retrofit: Retrofit

        fun getApiService(): Retrofit {

            if (!this::retrofit.isInitialized) {

                retrofit = Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return retrofit
        }
    }
}