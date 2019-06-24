package gresanu.emanuel.vasile.githubrepofetch.view.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gresanu.emanuel.vasile.githubrepofetch.R

class BatteryNotReaadyFragment: Fragment() {

    companion object {
        val TAG: String = BatteryNotReaadyFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_battery_low, container, false)
    }
}