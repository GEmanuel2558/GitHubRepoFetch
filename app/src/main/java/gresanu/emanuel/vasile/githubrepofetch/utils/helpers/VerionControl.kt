package gresanu.emanuel.vasile.githubrepofetch.utils.helpers

import android.os.Build

object VerionControl {

    fun isCurrentVersionAboveM() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

}