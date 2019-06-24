package gresanu.emanuel.vasile.githubrepofetch.view.callback

import gresanu.emanuel.vasile.githubrepofetch.service.model.GitRepo

interface ProjectClickCallback {
    fun onClick(gitRepo: GitRepo)
}