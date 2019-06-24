package gresanu.emanuel.vasile.githubrepofetch.view.ui.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gresanu.emanuel.vasile.githubrepofetch.R
import gresanu.emanuel.vasile.githubrepofetch.databinding.FragmentGitDetailsBinding
import gresanu.emanuel.vasile.githubrepofetch.service.model.GitRepo
import gresanu.emanuel.vasile.githubrepofetch.viewmodel.GitViewModel

class GitRepoDetailsFragment : Fragment() {

    private var binding: FragmentGitDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_git_details, container, false)

        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = GitViewModel.Factory(
            activity!!.application, arguments!!.getString(KEY_REPO_ID)
        )
        binding!!.lifecycleOwner = this
        val viewModel = ViewModelProviders.of(this, factory)
            .get(GitViewModel::class.java)

        binding!!.gitViewModel = viewModel
        binding!!.isLoading = true

        observeViewModel(viewModel)
    }

    private fun observeViewModel(viewModel: GitViewModel) {
        // Observe gitRepo data
        viewModel.observableGitRepo.observe(this, Observer<GitRepo> { gitRepo ->
            if (gitRepo != null) {
                binding!!.isLoading = false
                viewModel.setContributors(gitRepo).observe(this, Observer<GitRepo> { completeUpdate ->
                    viewModel.setProject(completeUpdate!!)
                })
            }
        })
    }

    companion object {
        private val KEY_REPO_ID = "repo_id"

        /** Creates gitRepo fragment for specific gitRepo ID  */
        fun forProject(projectID: String): GitRepoDetailsFragment {
            val fragment = GitRepoDetailsFragment()
            val args = Bundle()

            args.putString(KEY_REPO_ID, projectID)
            fragment.arguments = args

            return fragment
        }
    }
}
