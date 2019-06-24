package gresanu.emanuel.vasile.githubrepofetch.view.ui.fragments

import android.Manifest
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gresanu.emanuel.vasile.githubrepofetch.R
import gresanu.emanuel.vasile.githubrepofetch.databinding.FragmentGitListBinding
import gresanu.emanuel.vasile.githubrepofetch.service.model.GitRepo
import gresanu.emanuel.vasile.githubrepofetch.utils.exceptions.PermissionDenied
import gresanu.emanuel.vasile.githubrepofetch.utils.helpers.BatteryHelper.getBatteryPreparedForTheNetworkRequest
import gresanu.emanuel.vasile.githubrepofetch.utils.helpers.NetworkHelper.isNetworkAvailable
import gresanu.emanuel.vasile.githubrepofetch.utils.helpers.VerionControl.isCurrentVersionAboveM
import gresanu.emanuel.vasile.githubrepofetch.view.adapter.recyclerview.GitReposAdapter
import gresanu.emanuel.vasile.githubrepofetch.view.callback.ProjectClickCallback
import gresanu.emanuel.vasile.githubrepofetch.view.ui.activitys.MainActivity
import gresanu.emanuel.vasile.githubrepofetch.viewmodel.GitListViewModel
import kotlinx.android.synthetic.main.fragment_git_list.*

class GitReposFragment : Fragment() {
    private var gitReposAdapter: GitReposAdapter? = null
    private var binding: FragmentGitListBinding? = null

    private val viewModel: GitListViewModel by lazy {
        ViewModelProviders.of(this).get(GitListViewModel::class.java)
    }

    private val projectClickCallback = object : ProjectClickCallback {

        override fun onClick(gitRepo: GitRepo) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                (activity as MainActivity).show(gitRepo)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_git_list, container, false)
        binding!!.lifecycleOwner = this
        gitReposAdapter = GitReposAdapter(projectClickCallback)
        binding!!.projectList.adapter = gitReposAdapter
        binding!!.isLoading = true

        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.title = getString(R.string.all_github_projects)
    }

    override fun onResume() {
        super.onResume()

        try {
            val batteryPreparedForTheNetworkRequest =
                getBatteryPreparedForTheNetworkRequest(activity!!.applicationContext)
            if (isNetworkAvailable(activity!!.applicationContext) && batteryPreparedForTheNetworkRequest) {
                observeViewModel(viewModel)
            } else {
                if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                    if(batteryPreparedForTheNetworkRequest) {
                        (activity as MainActivity).showDialog()
                    } else {
                        (activity as MainActivity).showBatteryNotReady()
                    }
                }
            }
        } catch (e: PermissionDenied) {
            if(isCurrentVersionAboveM()) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_NETWORK_STATE), PERMISSION_KEY)
            }
        }
    }

    private fun observeViewModel(viewModel: GitListViewModel) {
        // Update the list when the data changes
        viewModel.gitRepoListObservable.observe(this,
            Observer<List<GitRepo>> { gitRepos ->
                if (gitRepos != null) {
                    binding!!.isLoading = false
                    gitReposAdapter!!.setProjectList(gitRepos)
                }
            })
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(PERMISSION_KEY == requestCode) {
            if(grantResults.any { PackageManager.PERMISSION_DENIED == it }) {
                //Request permission until I have it
                if(isCurrentVersionAboveM()) {
                    requestPermissions(arrayOf(Manifest.permission.ACCESS_NETWORK_STATE), PERMISSION_KEY)
                }
            }
        }
    }

    companion object {
        const val PERMISSION_KEY=341
        val TAG: String = GitReposFragment::class.java.simpleName
    }
}
