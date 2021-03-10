package com.example.parrottest.ui.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.parrottest.R
import com.example.parrottest.extensions.alert
import com.example.parrottest.extensions.alertCloseSession
import com.example.parrottest.interfaces.ActionDialogInterface
import com.example.parrottest.ui.category.CategoryFragment
import com.example.parrottest.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity(), ActionDialogInterface {

    companion object {

        fun startActivity(context: Context) {

            val intent = Intent(context, DashboardActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val viewModel: DashboardViewModel by viewModels()

    private lateinit var categoryFragment: CategoryFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setContentView(R.layout.activity_dashboard)
    }

    override fun onResume() {
        super.onResume()

        this.initializeObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_dashboard, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId) {

            R.id.action_close_session -> {

                this.showCloseSessionAlert()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {

        this.showCloseSessionAlert()
    }

    private fun initializeDashboard() {

        supportFragmentManager
            .beginTransaction()
            .add(R.id.activityDashboardFrameLayoutContainer, categoryFragment)
            .commit()
    }

    private fun initializeObservers() {

        this.viewModel.getStoresInformation()

        this.viewModel.titleBar.observe(this, Observer {

            supportActionBar?.title = it
        })

        this.viewModel.message.observe(this, Observer {

            this.alert(
                messageStringId = it,
                positiveStringId = R.string.button_accept
            )
        })

        this.viewModel.listStores.observe(this, Observer {

            if (it.isNotEmpty()) {

                categoryFragment = CategoryFragment.newInstance(it[0].uuid)

                this.initializeDashboard()
            }
        })
    }

    private fun showCloseSessionAlert() {

        this.alertCloseSession(
            callback = this
        )
    }

    override fun onAccept() {

        this.viewModel.closeSession()

        LoginActivity.startActivity(
            context = this
        )

        this.finish()
    }

    override fun onCancel() {
         // Nothing to do..
    }
}