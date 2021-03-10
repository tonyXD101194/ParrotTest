package com.example.parrottest.database.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.parrottest.database.ParrotDatabaseTestRule
import com.example.parrottest.database.model.ProductRoomModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductCRUDTest {

    @get:Rule
    val rule = ParrotDatabaseTestRule()

    @Test
    fun insertProductTest() = runBlocking {

        val product: ProductRoomModel = rule.product

        rule.productRepository.insertProduct(product)

        val productSaved = rule.productRepository.getProductById(product.uuid)

        assertThat(productSaved != null).isTrue()
        assertThat(product).isEqualTo(productSaved)
    }

    @Test
    fun getAllProductsByIdCategoryIdTest() = runBlocking {

        val product: ProductRoomModel = rule.product

        for (i in 1..3) {

            rule.productRepository.insertProduct(
                product.copy(
                    uuid = "test uuid $i"
                )
            )
        }

        val products = rule.productRepository.getProductsByCategory(product.idCategory)

        assertThat(products.isNotEmpty()).isTrue()
        assertThat(products.size == 3).isTrue()
    }

    @Test
    fun updateProductTest() = runBlocking {

        val product: ProductRoomModel = rule.product

        rule.productRepository.insertProduct(product)

        rule.productRepository.updateProduct(
            product.copy(
                name = "test name updated"
            )
        )

        val productSaved = rule.productRepository.getProductById(product.uuid)

        assertThat(productSaved != null).isTrue()
        assertThat(productSaved!!.name).isEqualTo("test name updated")
    }

    @Test
    fun deleteProductTest() = runBlocking {

        val product: ProductRoomModel = rule.product

        rule.productRepository.insertProduct(product)

        rule.productRepository.deleteProduct(product)

        val productSaved = rule.productRepository.getProductById(product.uuid)

        assertThat(productSaved == null).isTrue()
    }

    @Test
    fun deleteAllProductsTest() = runBlocking {

        val product: ProductRoomModel = rule.product

        for (i in 1..3) {

            rule.productRepository.insertProduct(
                product.copy(
                    uuid = "uuid test $i"
                )
            )
        }

        rule.productRepository.deleteAllProducts()

        val products = rule.productRepository.getProductsByCategory("uuid test 1")

        assertThat(products.isEmpty()).isTrue()
    }
}