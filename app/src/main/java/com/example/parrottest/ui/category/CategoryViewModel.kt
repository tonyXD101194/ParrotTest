package com.example.parrottest.ui.category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.parrottest.R
import com.example.parrottest.api.ApiServiceInterface
import com.example.parrottest.api.ServiceApi
import com.example.parrottest.database.model.CategoryRoomModel
import com.example.parrottest.database.model.ProductRoomModel
import com.example.parrottest.enums.StatusProductEnum
import com.example.parrottest.extensions.isNetworkConnected
import com.example.parrottest.model.dashboard.MenuCategoryModel
import com.example.parrottest.model.product.ProductModel
import com.example.parrottest.model.product.ProductResponseModel
import com.example.parrottest.model.product.ProductStatusModel
import com.example.parrottest.model.product.ProductUpdateRequest
import com.example.parrottest.repository.CategoryRepository
import com.example.parrottest.repository.ProductRepository
import com.example.parrottest.utilities.PreferencesParrot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val service: ApiServiceInterface by lazy {

        ServiceApi.getApiService().create(ApiServiceInterface::class.java)
    }

    private val productRepository: ProductRepository by lazy {

        ProductRepository(application)
    }

    private val categoryRepository: CategoryRepository by lazy {

        CategoryRepository(application)
    }

    private val sharedPreferences: PreferencesParrot by lazy {

        PreferencesParrot(application)
    }


    // region Message Info

    private val messageIntMutable: MutableLiveData<Int> = MutableLiveData()

    val message: LiveData<Int> = this.messageIntMutable

    // endregion


    // region getProducts

    fun getProductsById(idStore: String) {

        GlobalScope.launch(Dispatchers.IO) {

            if (!getApplication<Application>().applicationContext.isNetworkConnected()) {

                messageIntMutable.postValue(R.string.no_internet)

                return@launch
            }

            val accessToken: String? = sharedPreferences.getCurrentKey()

            if (accessToken.isNullOrEmpty()) {

                messageIntMutable.postValue(R.string.no_token_saved)
            } else {

                val tokenBuilder = StringBuilder()

                tokenBuilder.append(ServiceApi.HEADER_TAG_AUTHORIZATION)
                tokenBuilder.append(accessToken)

                val call: Call<ProductResponseModel> = service.getProductsByStoreId(
                    token = tokenBuilder.toString(),
                    id = idStore
                )

                call.enqueue(object: Callback<ProductResponseModel> {

                    override fun onResponse(
                        call: Call<ProductResponseModel>?,
                        response: Response<ProductResponseModel>?
                    ) {

                        val model = response?.body()

                        if (model != null && model.status == "ok" && model.products.isNotEmpty()) {

                            saveProducts(model.products)
                        } else {

                            listMenuMutable.postValue(listOf())
                        }
                    }

                    override fun onFailure(call: Call<ProductResponseModel>?, t: Throwable?) {

                        messageIntMutable.postValue(R.string.message_dashboard_something_wrong)
                    }
                })
            }
        }
    }

    private val listMenuMutable: MutableLiveData<List<MenuCategoryModel>> = MutableLiveData()

    val listMenu: LiveData<List<MenuCategoryModel>> = this.listMenuMutable

    private fun saveProducts(list: List<ProductModel>) {

        GlobalScope.launch(Dispatchers.IO) {

            list.forEach { product ->

                val productSaved = productRepository.getProductById(product.uuid)

                if (productSaved != null) {

                    productRepository.updateProduct(
                        productSaved.copy(
                            idCategory = product.category.uuid,
                            description = product.description,
                            imageUrl = product.imageUrl,
                            price = product.price,
                            isSoldAlone = product.soldAlone,
                            availability = product.availability,
                            name = product.name
                        )
                    )

                } else {

                    productRepository.insertProduct(
                        ProductRoomModel(
                            idCategory = product.category.uuid,
                            description = product.description,
                            imageUrl = product.imageUrl,
                            price = product.price,
                            isSoldAlone = product.soldAlone,
                            availability = product.availability,
                            uuid = product.uuid,
                            name = product.name
                        )
                    )
                }

                categoryRepository.insertCategory(
                    CategoryRoomModel(
                        name = product.category.name,
                        uuid = product.category.uuid,
                        username = sharedPreferences.getCurrentUsername()!!,
                        sortPosition = product.category.sortPosition
                    )
                )
            }

            val listCategory = categoryRepository.getCategoriesByUsername(sharedPreferences.getCurrentUsername()!!)

            val listCategoryTemporal: MutableList<MenuCategoryModel> = mutableListOf()

            listCategory.forEach { category ->

                val productCategory = productRepository.getProductsByCategory(category.uuid)

                listCategoryTemporal.add(
                    MenuCategoryModel(
                        category = category,
                        listProduct = productCategory,
                        counter = productCategory.size
                    )
                )
            }

            listMenuMutable.postValue(listCategoryTemporal)
        }
    }

    // endregion


    // region update product

    fun updateStatusProduct(uuid: String, status: StatusProductEnum) {

        GlobalScope.launch(Dispatchers.IO) {

            if (!getApplication<Application>().applicationContext.isNetworkConnected()) {

                messageIntMutable.postValue(R.string.no_internet)

                return@launch
            }

            val accessToken: String? = sharedPreferences.getCurrentKey()

            if (accessToken.isNullOrEmpty()) {

                messageIntMutable.postValue(R.string.no_token_saved)
            } else {

                val tokenBuilder = StringBuilder()

                tokenBuilder.append(ServiceApi.HEADER_TAG_AUTHORIZATION)
                tokenBuilder.append(accessToken)

                val call: Call<ProductStatusModel> = service.changeStatusProductById(
                    token = tokenBuilder.toString(),
                    id = uuid,
                    request = ProductUpdateRequest(status.name)
                )

                call.enqueue(object: Callback<ProductStatusModel> {

                    override fun onResponse(
                        call: Call<ProductStatusModel>?,
                        response: Response<ProductStatusModel>?
                    ) {

                        val model = response?.body()

                        if (model != null && model.status == "ok" && model.product.uuid == uuid) {

                            updateProduct(model.product)
                        } else {

                            messageIntMutable.postValue(R.string.fragment_category_change_status_wrong)
                        }
                    }

                    override fun onFailure(call: Call<ProductStatusModel>?, t: Throwable?) {

                        messageIntMutable.postValue(R.string.message_dashboard_something_wrong)
                    }
                })
            }
        }
    }

    private fun updateProduct(product: ProductModel) {

        GlobalScope.launch(Dispatchers.IO) {

            val productSaved = productRepository.getProductById(product.uuid)

            if (productSaved != null) {

                productRepository.updateProduct(
                    productSaved.copy(
                        availability = product.availability
                    )
                )

                messageIntMutable.postValue(R.string.fragment_category_change_status_ok)
            }
        }
    }

    // endregion
}