package com.example.parrottest.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.parrottest.R
import com.example.parrottest.ui.dashboard.DashboardActivity
import com.example.parrottest.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    companion object {

        private const val TIME_TO_OPEN: Long = 1000
    }

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()

        this.initializeObservers()
    }

    private fun initializeObservers() {

        this.viewModel.checkCurrentUser()

        this.viewModel.isAvailableGoToLogin.observe(this, Observer {

            if (it) {

                this.goToLogin()
            }
        })

        this.viewModel.isAvailableGoToDashboard.observe(this, Observer {

            if (it) {

                this.goToDashboard()
            }
        })
    }

    private fun goToLogin() {

        Handler(Looper.getMainLooper()).postDelayed({

            LoginActivity.startActivity(
                context = this
            )

            this.finish()
        }, TIME_TO_OPEN)
    }

    private fun goToDashboard() {

        Handler(Looper.getMainLooper()).postDelayed({

            DashboardActivity.startActivity(
                context = this
            )

            this.finish()
        }, TIME_TO_OPEN)
    }
}