package gresanu.emanuel.vasile.githubrepofetch.view.ui.activitys

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import gresanu.emanuel.vasile.githubrepofetch.R
import gresanu.emanuel.vasile.githubrepofetch.service.model.GitRepo
import gresanu.emanuel.vasile.githubrepofetch.service.repository.GitHubServiceV2
import gresanu.emanuel.vasile.githubrepofetch.utils.helpers.NetworkHelper
import gresanu.emanuel.vasile.githubrepofetch.utils.helpers.NetworkHelper.isNetworkAvailable
import gresanu.emanuel.vasile.githubrepofetch.view.ui.fragments.BatteryNotReaadyFragment
import gresanu.emanuel.vasile.githubrepofetch.view.ui.fragments.GitRepoDetailsFragment
import gresanu.emanuel.vasile.githubrepofetch.view.ui.fragments.GitReposFragment
import gresanu.emanuel.vasile.githubrepofetch.view.ui.fragments.RequestNetworkPermissionFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFragment(savedInstanceState)
    }

    private fun initFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val fragment = GitReposFragment()

            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment, GitReposFragment.TAG).commit()
        }
    }

    fun show(gitRepo: GitRepo) {
        val gitFragment = GitRepoDetailsFragment.forProject(gitRepo.name)

        supportFragmentManager
            .beginTransaction()
            .addToBackStack("gitRepo")
            .replace(
                R.id.fragment_container,
                gitFragment, null
            ).commit()
    }

    fun showDialog() {
        val projectFragment = RequestNetworkPermissionFragment.newInstance(getString(R.string.permission))
        if (!isNetworkAvailable(applicationContext)) {
            projectFragment.show(supportFragmentManager, RequestNetworkPermissionFragment.TAG)
        }
    }

    fun showBatteryNotReady() {
        val batteryLowFragment = BatteryNotReaadyFragment()
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(BatteryNotReaadyFragment.TAG)
            .replace(
                R.id.fragment_container,
                batteryLowFragment, null
            ).commit()
    }
}
