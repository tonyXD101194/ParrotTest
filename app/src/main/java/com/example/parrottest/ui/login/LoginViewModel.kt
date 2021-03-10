package com.example.parrottest.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.parrottest.R
import com.example.parrottest.api.ApiServiceInterface
import com.example.parrottest.api.ServiceApi
import com.example.parrottest.database.model.UserRoomModel
import com.example.parrottest.extensions.isValidEmail
import com.example.parrottest.model.login.LoginModel
import com.example.parrottest.model.login.LoginRequest
import com.example.parrottest.repository.UserRepository
import com.example.parrottest.utilities.PreferencesParrot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val service: ApiServiceInterface by lazy {

        ServiceApi.getApiService().create(ApiServiceInterface::class.java)
    }

    private val userRepository: UserRepository by lazy {

        UserRepository(application)
    }

    private val sharedPreferences: PreferencesParrot by lazy {

        PreferencesParrot(application)
    }


    // region Message Info

    private val messageIntMutable: MutableLiveData<Int> = MutableLiveData()

    val message: LiveData<Int> = this.messageIntMutable

    // endregion


    // region login

    private val isAvailableGoToDashboardMutable: MutableLiveData<Boolean> by lazy {

        MutableLiveData(false)
    }

    val isAvailableGoToDashboard: LiveData<Boolean> = this.isAvailableGoToDashboardMutable

    fun login(username: String, password: String) {

        GlobalScope.launch(Dispatchers.IO) {

            if (username.isEmpty() || password.isEmpty()) {

                messageIntMutable.postValue(R.string.message_login_miss_fields)
            } else if(password.isValidEmail()) {

                messageIntMutable.postValue(R.string.message_login_wrong_email)
            } else {

                val call: Call<LoginModel> = service.login(
                    request = LoginRequest(username, password)
                )

                call.enqueue(object: Callback<LoginModel> {

                    override fun onResponse(
                        call: Call<LoginModel>?,
                        response: Response<LoginModel>?
                    ) {

                        val model = response?.body()

                        if (model != null) {

                            sharedPreferences.setCurrentUsername(username)
                            sharedPreferences.setCurrentKey(model.access)

                            saveUser(username, model)
                        }
                    }

                    override fun onFailure(call: Call<LoginModel>?, t: Throwable?) {

                        messageIntMutable.postValue(R.string.message_login_something_wrong)
                    }
                })
            }
        }
    }

    private fun saveUser(username: String, login: LoginModel) {

        GlobalScope.launch(Dispatchers.IO) {

            val user: UserRoomModel? = userRepository.getUserByUserName(username)

            if (user != null) {

                userRepository.updateUser(user.copy(
                    accessToken = login.access,
                    refreshToken = login.refresh
                ))
            } else {

                userRepository.insertUser(
                    user = UserRoomModel(
                        username = username,
                        accessToken = login.access,
                        refreshToken = login.refresh
                    )
                )
            }

            isAvailableGoToDashboardMutable.postValue(true)
        }
    }

    // endregion
}