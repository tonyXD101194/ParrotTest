package com.example.parrottest.api

import com.example.parrottest.model.category.CategoryModel
import com.example.parrottest.model.login.LoginModel
import com.example.parrottest.model.login.LoginRequest
import com.example.parrottest.model.product.ProductModel
import com.example.parrottest.model.product.ProductResponseModel
import com.example.parrottest.model.product.ProductStatusModel
import com.example.parrottest.model.product.ProductUpdateRequest
import com.example.parrottest.model.stores.ResultStoreModel
import com.example.parrottest.model.stores.StoreResponseModel
import com.example.parrottest.model.token.ValidateModel
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import retrofit2.Response
import java.util.*

class ApiServiceTest {

    private val token by lazy {

        val timestamp: Long = Date().time

        "token test $timestamp"
    }

    @Mock
    private lateinit var service: ApiServiceInterface

    @Before
    fun setUp() {

        service = Mockito.mock(ApiServiceInterface::class.java, Mockito.RETURNS_DEEP_STUBS)
    }

    // region get

    @Test
    fun getMyStoresTest() {

        Mockito.`when`(service.getMyStores(token).execute()).thenReturn(
            Response.success(
                StoreResponseModel(
                    status = "ok",
                    result = ResultStoreModel(
                        uuid = "uuid test",
                        email = "email test",
                        username = "username test",
                        stores = listOf()
                    )
                )
            ))

        val response = service.getMyStores(token).execute()
        val bodyResponse = response.body()

        assertThat(bodyResponse != null).isTrue()
        assertThat(bodyResponse?.status).isEqualTo("ok")
    }

    @Test
    fun getProductsByStoreIdTest() {

        Mockito.`when`(service.getProductsByStoreId(token, "id test").execute()).thenReturn(
            Response.success(
                ProductResponseModel(
                    status = "ok",
                    products = listOf()
                )
            ))

        val response = service.getProductsByStoreId(token, "id test").execute()
        val bodyResponse = response.body()

        assertThat(bodyResponse != null).isTrue()
        assertThat(bodyResponse?.status).isEqualTo("ok")
    }

    @Test
    fun validateTokenActiveTest() {

        Mockito.`when`(service.validateTokenActive(token).execute()).thenReturn(
            Response.success(
                ValidateModel(
                    status = "ok"
                )
            ))

        val response = service.validateTokenActive(token).execute()
        val bodyResponse = response.body()

        assertThat(bodyResponse != null).isTrue()
        assertThat(bodyResponse?.status).isEqualTo("ok")
    }

    // endregion

    // region post

    @Test
    fun loginTest() {

        Mockito.`when`(service.login(
            LoginRequest(
                userName = "username test",
                password = "password test"
            )
        ).execute()).thenReturn(
            Response.success(
                LoginModel(
                    refresh = "refresh token test",
                    access = "access token test"
                )
            ))

        val response = service.login(
            LoginRequest(
                userName = "username test",
                password = "password test"
            )).execute()

        val bodyResponse = response.body()

        assertThat(bodyResponse != null).isTrue()
    }

    // endregion

    // region put

    @Test
    fun changeStatusProductByIdTest() {

        Mockito.`when`(service.changeStatusProductById("id product", token,
            ProductUpdateRequest(
                availability = "availability test"
            )
        ).execute()).thenReturn(
            Response.success(
                ProductStatusModel(
                    status = "ok",
                    product = ProductModel(
                        uuid = "uuid test",
                        name = "name test",
                        description = "description test",
                        imageUrl = "url test",
                        legacyId = "legacy test",
                        price = "0.0",
                        alcoholCount = 0,
                        soldAlone = false,
                        availability = "ava test",
                        providerAvailability = null,
                        category = CategoryModel(
                            uuid = "uuid cat test",
                            name = "name test",
                            sortPosition = 1
                        )
                    )
                )
            ))

        val response = service.changeStatusProductById("id product", token,
            ProductUpdateRequest(
                availability = "availability test"
            )
        ).execute()

        val bodyResponse = response.body()

        assertThat(bodyResponse != null).isTrue()
    }

    // endregion
}