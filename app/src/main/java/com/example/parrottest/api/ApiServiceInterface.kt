package com.example.parrottest.api

import com.example.parrottest.model.login.LoginModel
import com.example.parrottest.model.login.LoginRequest
import com.example.parrottest.model.product.ProductResponseModel
import com.example.parrottest.model.product.ProductStatusModel
import com.example.parrottest.model.product.ProductUpdateRequest
import com.example.parrottest.model.stores.StoreResponseModel
import com.example.parrottest.model.token.ValidateModel
import retrofit2.Call
import retrofit2.http.*

interface ApiServiceInterface {

    // region GET

    @GET("/api/v1/users/me")
    fun getMyStores(@Header("Authorization") token: String): Call<StoreResponseModel>

    @GET("/api/v1/products/")
    fun getProductsByStoreId(@Header("Authorization") token: String, @Query("store") id: String): Call<ProductResponseModel>

    @GET("/api/auth/token/test")
    fun validateTokenActive(@Header("Authorization") token: String): Call<ValidateModel>

    // endregion


    // region POST

    @POST("/api/auth/token")
    fun login(@Body request: LoginRequest): Call<LoginModel>

    // endregion


    // region PUT

    @PUT("/api/v1/products/{product_id}/availability")
    fun changeStatusProductById(
        @Path("product_id") id: String,
        @Header("Authorization") token: String,
        @Body request: ProductUpdateRequest): Call<ProductStatusModel>

    // endregion
}