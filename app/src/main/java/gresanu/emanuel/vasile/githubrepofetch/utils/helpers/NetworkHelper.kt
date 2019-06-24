package gresanu.emanuel.vasile.githubrepofetch.utils.helpers

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import gresanu.emanuel.vasile.githubrepofetch.utils.exceptions.PermissionDenied
import gresanu.emanuel.vasile.githubrepofetch.utils.helpers.VerionControl.isCurrentVersionAboveM


object NetworkHelper {

    @Throws(PermissionDenied::class)
    fun isNetworkAvailable(context: Context): Boolean {

        if(isCurrentVersionAboveM() && PackageManager.PERMISSION_DENIED == context.checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE)) {
            throw PermissionDenied("Permission denied")
        }

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}