package com.example.parrottest.extensions

import android.content.Context
import android.net.ConnectivityManager


fun Context.isNetworkConnected(): Boolean {

    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

    return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
}