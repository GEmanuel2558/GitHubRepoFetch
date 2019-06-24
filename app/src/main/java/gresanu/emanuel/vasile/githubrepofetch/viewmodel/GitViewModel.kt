package gresanu.emanuel.vasile.githubrepofetch.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.databinding.ObservableField
import gresanu.emanuel.vasile.githubrepofetch.service.model.GitRepo
import gresanu.emanuel.vasile.githubrepofetch.service.repository.GitRepository

class GitViewModel(
    application: Application,
    gitID: String
) : AndroidViewModel(application) {

    val observableGitRepo: LiveData<GitRepo> = GitRepository.getProjectDetails(gitID)

    var gitRepo = ObservableField<GitRepo>()

    fun setContributors(gitRepo: GitRepo) = GitRepository.getContributors(gitRepo.contributors_url, gitRepo)

    fun setProject(gitRepo: GitRepo) {
        this.gitRepo.set(gitRepo)
    }

    /**
     * A creator is used to inject the gitRepo ID into the ViewModel
     */
    class Factory(private val application: Application, private val gitID: String) :
        ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            return GitViewModel(application, gitID) as T
        }
    }
}
