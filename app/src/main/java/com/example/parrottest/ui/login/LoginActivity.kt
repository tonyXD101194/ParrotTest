package com.example.parrottest.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.parrottest.R
import com.example.parrottest.extensions.alert
import com.example.parrottest.ui.dashboard.DashboardActivity
import com.example.parrottest.ui.dialogs.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    companion object {

        fun startActivity(context: Context) {

            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }

        private const val DUMMY_USERNAME = "android-challenge@parrotsoftware.io"
        private const val DUMMY_PASSWORD = "8mngDhoPcB3ckV7X"
    }

    private val viewModel: LoginViewModel by viewModels()

    private val loadingDialog: LoadingDialog by lazy {

        LoadingDialog.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setContentView(R.layout.activity_login)
    }

    override fun onResume() {
        super.onResume()

        this.initializeListeners()
        this.initializeObservers()

        //TODO remove this later

        this.activityLoginUserTextInputEditText.setText(DUMMY_USERNAME)
        this.activityLoginPasswordTextInputEditText.setText(DUMMY_PASSWORD)
    }

    private fun initializeListeners() {

        this.activityLoginButton.setOnClickListener {

            this.loadingDialog.show(supportFragmentManager, LoadingDialog.DIALOG_LOADING_TAG)

            this.viewModel.login(
                username = activityLoginUserTextInputEditText.text.toString(),
                password = activityLoginPasswordTextInputEditText.text.toString()
            )
        }
    }

    private fun initializeObservers() {

        this.viewModel.message.observe(this, Observer {

            this.alert(
                messageStringId = it,
                positiveStringId = R.string.button_accept
            )
        })

        this.viewModel.isAvailableGoToDashboard.observe(this, Observer {

            if (loadingDialog.isAdded) {

                this.loadingDialog.dismiss()
            }

            if (it) {

                DashboardActivity.startActivity(
                    context = this
                )

                this.finish()
            }
        })
    }
}