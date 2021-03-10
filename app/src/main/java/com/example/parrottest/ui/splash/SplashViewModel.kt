package com.example.parrottest.ui.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.parrottest.api.ApiServiceInterface
import com.example.parrottest.api.ServiceApi
import com.example.parrottest.model.token.ValidateModel
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
class SplashViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val service: ApiServiceInterface by lazy {

        ServiceApi.getApiService().create(ApiServiceInterface::class.java)
    }

    private val sharedPreferences: PreferencesParrot by lazy {

        PreferencesParrot(application)
    }


    // region checkOldUser

    private val isAvailableGoToLoginMutable: MutableLiveData<Boolean> by lazy {

        MutableLiveData(false)
    }

    val isAvailableGoToLogin: LiveData<Boolean> = this.isAvailableGoToLoginMutable

    private val isAvailableGoToDashboardMutable: MutableLiveData<Boolean> by lazy {

        MutableLiveData(false)
    }

    val isAvailableGoToDashboard: LiveData<Boolean> = this.isAvailableGoToDashboardMutable

    fun checkCurrentUser() {

        GlobalScope.launch(Dispatchers.IO) {

            val accessToken: String? = sharedPreferences.getCurrentKey()

            if (accessToken.isNullOrEmpty()) {

                isAvailableGoToLoginMutable.postValue(true)
            } else {

                val tokenBuilder = StringBuilder()

                tokenBuilder.append(ServiceApi.HEADER_TAG_AUTHORIZATION)
                tokenBuilder.append(accessToken)

                val call: Call<ValidateModel> = service.validateTokenActive(
                    token = tokenBuilder.toString()
                )

                call.enqueue(object: Callback<ValidateModel> {

                    override fun onResponse(
                        call: Call<ValidateModel>?,
                        response: Response<ValidateModel>?
                    ) {

                        val model = response?.body()

                        if (model != null && model.status == "ok") {

                            isAvailableGoToDashboardMutable.postValue(true)
                        } else {

                            isAvailableGoToLoginMutable.postValue(true)
                        }
                    }

                    override fun onFailure(call: Call<ValidateModel>?, t: Throwable?) {

                        isAvailableGoToLoginMutable.postValue(true)
                    }
                })
            }
        }
    }

    // endregion
}