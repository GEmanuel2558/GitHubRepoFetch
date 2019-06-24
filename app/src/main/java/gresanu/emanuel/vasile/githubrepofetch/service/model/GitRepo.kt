package gresanu.emanuel.vasile.githubrepofetch.service.model

data class GitRepo(
    var id: Long = 0,
    var name: String = "",
    var full_name: String = "",
    var git_url: String = "",
    var forks_count: Int = 0,
    var size: Float = 0.0f,
    var stargazers_count: Int = 0,
    var contributors_url: String = "",
    var contributors:List<Contributors>? = null
)