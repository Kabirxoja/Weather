package uz.kabir.weather.data.remote.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkChecker(private val context: Context) {
    fun isNetworkConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
}