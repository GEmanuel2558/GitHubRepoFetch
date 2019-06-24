package gresanu.emanuel.vasile.githubrepofetch.view.ui.fragments

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import gresanu.emanuel.vasile.githubrepofetch.R


class RequestNetworkPermissionFragment: DialogFragment() {

    companion object {
        @JvmStatic
        fun newInstance(title: String): RequestNetworkPermissionFragment {
            val frag = RequestNetworkPermissionFragment()
            val args = Bundle()
            args.putString("title", title)
            frag.arguments = args
            return frag

        }

        val TAG: String = RequestNetworkPermissionFragment::class.java.simpleName
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = arguments!!.getString("title")

        val alertDialogBuilder = AlertDialog.Builder(context!!)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(getString(R.string.internet_connection))
        alertDialogBuilder.setCancelable(false)

        alertDialogBuilder.setPositiveButton("Wifi") { dialog, which ->
            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
            dialog.dismiss()
        }

        alertDialogBuilder.setNegativeButton("Mobile Data") { dialog, which ->
            val intent = Intent(Intent.ACTION_MAIN)
            intent.setClassName("com.android.phone", "com.android.phone.NetworkSetting")
            startActivity(intent)
            dialog.dismiss()
        }

        return alertDialogBuilder.create()
    }

}