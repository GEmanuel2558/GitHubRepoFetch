package gresanu.emanuel.vasile.githubrepofetch.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import gresanu.emanuel.vasile.githubrepofetch.service.model.GitRepo
import gresanu.emanuel.vasile.githubrepofetch.service.repository.GitRepository

class GitListViewModel(application: Application) : AndroidViewModel(application) {
    /**
     * Expose the LiveData Projects query so the UI can observe it.
     */
    val gitRepoListObservable: LiveData<List<GitRepo>> = GitRepository.getProjectList()

}
