package com.example.parrottest.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.parrottest.R
import com.example.parrottest.api.ApiServiceInterface
import com.example.parrottest.api.ServiceApi
import com.example.parrottest.database.model.StoreRoomModel
import com.example.parrottest.model.stores.StoreModel
import com.example.parrottest.model.stores.StoreResponseModel
import com.example.parrottest.repository.StoreRepository
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
class DashboardViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val service: ApiServiceInterface by lazy {

        ServiceApi.getApiService().create(ApiServiceInterface::class.java)
    }

    private val storeRepository: StoreRepository by lazy {

        StoreRepository(application)
    }

    private val sharedPreferences: PreferencesParrot by lazy {

        PreferencesParrot(application)
    }


    // region Message Info

    private val messageIntMutable: MutableLiveData<Int> = MutableLiveData()

    val message: LiveData<Int> = this.messageIntMutable

    // endregion


    // region getStores

    private val titleBarMutable: MutableLiveData<String> = MutableLiveData()

    val titleBar: LiveData<String> = this.titleBarMutable

    fun getStoresInformation() {

        GlobalScope.launch(Dispatchers.IO) {

            val accessToken: String? = sharedPreferences.getCurrentKey()

            if (accessToken.isNullOrEmpty()) {

                messageIntMutable.postValue(R.string.no_token_saved)
            } else {

                val tokenBuilder = StringBuilder()

                tokenBuilder.append(ServiceApi.HEADER_TAG_AUTHORIZATION)
                tokenBuilder.append(accessToken)

                val call: Call<StoreResponseModel> = service.getMyStores(
                    token = tokenBuilder.toString()
                )

                call.enqueue(object: Callback<StoreResponseModel> {

                    override fun onResponse(
                        call: Call<StoreResponseModel>?,
                        response: Response<StoreResponseModel>?
                    ) {

                        val model = response?.body()

                        if (model != null && model.status == "ok" && model.result.stores.isNotEmpty()) {

                            titleBarMutable.postValue(model.result.stores[0].name)

                            saveStores(model.result.email, model.result.stores)
                        } else {

                            messageIntMutable.postValue(R.string.message_dashboard_something_wrong)
                        }
                    }

                    override fun onFailure(call: Call<StoreResponseModel>?, t: Throwable?) {

                        messageIntMutable.postValue(R.string.message_dashboard_something_wrong)
                    }
                })
            }
        }
    }

    private val listStoresMutable: MutableLiveData<List<StoreRoomModel>> = MutableLiveData()

    val listStores: LiveData<List<StoreRoomModel>> = this.listStoresMutable

    private fun saveStores(email: String, list: List<StoreModel>) {

        GlobalScope.launch(Dispatchers.IO) {

            list.forEach { store ->

                storeRepository.insertStore(
                    StoreRoomModel(
                        name = store.name,
                        availabilityState = store.availabilityState,
                        username = sharedPreferences.getCurrentUsername()!!,
                        email = email,
                        uuid = store.uuid
                    )
                )
            }

            listStoresMutable.postValue(
                storeRepository.getStoresByUsername(sharedPreferences.getCurrentUsername()!!)
            )
        }
    }

    // endregion


    // region close session

    fun closeSession() {

        sharedPreferences.setCurrentKey("")
        sharedPreferences.setCurrentUsername("")
    }

    // endregion
}