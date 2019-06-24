package gresanu.emanuel.vasile.githubrepofetch.service.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import gresanu.emanuel.vasile.githubrepofetch.service.model.GitRepo

object GitRepository  {

    private val gitHubServiceV2: GitHubServiceV2 = GitHubServiceV2

    fun getProjectList(userId: String= "Google"): LiveData<List<GitRepo>> {
        val data = MutableLiveData<List<GitRepo>>()

        gitHubServiceV2.getProjectList(userId) {
            data.postValue(it)
        }

        return data
    }

    fun getProjectDetails(projectName: String, userID: String = "Google"): LiveData<GitRepo> {
        val data = MutableLiveData<GitRepo>()

        gitHubServiceV2.getProjectDetails(userID, projectName) {
            data.postValue(it)
        }

        return data
    }

    fun getContributors(urlString: String, gitRepo: GitRepo) : LiveData<GitRepo> {
        val data = MutableLiveData<GitRepo>()

        gitHubServiceV2.getContributors(urlString) {
            gitRepo.contributors = it
            data.postValue(gitRepo)
        }

        return data
    }

}
