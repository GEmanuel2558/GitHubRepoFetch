package gresanu.emanuel.vasile.githubrepofetch.service.repository

import gresanu.emanuel.vasile.githubrepofetch.BuildConfig
import gresanu.emanuel.vasile.githubrepofetch.service.model.Contributors
import gresanu.emanuel.vasile.githubrepofetch.service.model.GitRepo
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.URL
import java.net.URLConnection
import java.util.concurrent.Executors

object GitHubServiceV2 {

    private const val HTTPS_API_GITHUB_URL = "https://api.github.com/"

    @Throws(JSONException::class)
    fun getProjectList(user: String, updateCallback: (List<GitRepo>) -> Unit) {
        Executors.newSingleThreadExecutor().execute {
            val connection = try {
                //URL("https://api.github.com/repositories?since=$since&visibility=public").openConnection()
                URL("https://api.github.com/users/$user/repos").openConnection()
            } catch (e: IOException) {
                if (BuildConfig.DEBUG) {
                    throw e
                } else {
                    null
                }
            }

            if (null != connection) {
                readRequestBody(connection)
                val jArray = initJsonArray(connection)
                val returnList = mutableListOf<GitRepo>()
                if (null != jArray) {
                    for (index in 0 until jArray.length()) {
                        val jObject = jArray.getJSONObject(index)
                        try {
                            val name = jObject?.getString("name") ?: ""
                            val full_name = jObject?.getString("full_name") ?: ""
                            val id = jObject?.getLong("id") ?: 0L
                            val git_url = jObject?.getString("git_url") ?: ""
                            val forks_count = jObject?.getInt("forks_count") ?: 0
                            val size = jObject?.getDouble("size")?.toFloat() ?: 0.0f
                            val stargazers_count = jObject?.getInt("stargazers_count") ?: 0
                            val contributors_url = jObject?.getString("contributors_url") ?: ""

                            returnList.add(
                                GitRepo(
                                    name = name,
                                    full_name = full_name,
                                    id = id,
                                    git_url = git_url,
                                    forks_count = forks_count,
                                    size = size,
                                    stargazers_count = stargazers_count,
                                    contributors_url = contributors_url
                                )
                            )
                        } catch (e: JSONException) {
                            if (BuildConfig.DEBUG) {
                                throw e
                            }
                            break
                        }
                    }

                    updateCallback(returnList.toList())
                }
            }
        }
    }

    @Throws(JSONException::class)
    fun getProjectDetails(user: String, projectName: String, updateCallback: (GitRepo) -> Unit) {
        Executors.newSingleThreadExecutor().execute {
            val connection = try {
                //URL("https://api.github.com/repositories?since=$since&visibility=public").openConnection()
                URL("https://api.github.com/repos/$user/$projectName").openConnection()
            } catch (e: IOException) {
                if (BuildConfig.DEBUG) {
                    throw e
                } else {
                    null
                }
            }

            if (null != connection) {
                readRequestBody(connection)
                val jObject = initJsonObject(connection)
                if (null != jObject) {
                    try {
                        val name = jObject?.getString("name") ?: ""
                        val full_name = jObject?.getString("full_name") ?: ""
                        val id = jObject?.getLong("id") ?: 0L
                        val git_url = jObject?.getString("git_url") ?: ""
                        val forks_count = jObject?.getInt("forks_count") ?: 0
                        val size = jObject?.getDouble("size")?.toFloat() ?: 0.0f
                        val stargazers_count = jObject?.getInt("stargazers_count") ?: 0
                        val contributors_url = jObject?.getString("contributors_url") ?: ""

                        updateCallback(
                            GitRepo(
                                name = name,
                                full_name = full_name,
                                id = id,
                                git_url = git_url,
                                forks_count = forks_count,
                                size = size,
                                stargazers_count = stargazers_count,
                                contributors_url = contributors_url
                            )
                        )
                    } catch (e: JSONException) {
                        if (BuildConfig.DEBUG) {
                            throw e
                        }
                    }
                }
            }
        }
    }

    @Throws(JSONException::class)
    fun getContributors(url: String, updateCallback: (List<Contributors>) -> Unit) {
        Executors.newSingleThreadExecutor().execute {
            val connection = try {
                URL(url).openConnection()
            } catch (e: IOException) {
                if (BuildConfig.DEBUG) {
                    throw e
                } else {
                    null
                }
            }

            if (null != connection) {
                readRequestBody(connection)
                val jArray = initJsonArray(connection)
                val returnList = mutableListOf<Contributors>()
                if (null != jArray) {
                    for (index in 0 until jArray.length()) {
                        val jObject = jArray.getJSONObject(index)
                        try {
                            val login = jObject?.getString("login") ?: ""
                            val avatar_url = jObject?.getString("avatar_url") ?: ""

                            returnList.add(
                                Contributors(login, avatar_url)
                            )
                        } catch (e: JSONException) {
                            if (BuildConfig.DEBUG) {
                                throw e
                            }
                            break
                        }
                    }

                    updateCallback(returnList.toList())
                }
            }
        }
    }


    private fun initJsonArray(connection: URLConnection): JSONArray? {
        val json = connection.getInputStream().bufferedReader().readText()
        val jObject = try {
            JSONArray(json)
        } catch (e: JSONException) {
            if (BuildConfig.DEBUG) {
                throw e
            } else {
                null
            }
        }
        return jObject
    }

    private fun initJsonObject(connection: URLConnection): JSONObject? {
        val json = connection.getInputStream().bufferedReader().readText()
        val jObject = try {
            JSONObject(json)
        } catch (e: JSONException) {
            if (BuildConfig.DEBUG) {
                throw e
            } else {
                null
            }
        }
        return jObject
    }

    private fun readRequestBody(connection: URLConnection) {
        try {
            connection.addRequestProperty("Accept", "application/vnd.github.v3+json")
            connection.connectTimeout = 5000
            connection.readTimeout = 5000
        } catch (e: IllegalStateException) {
            if (BuildConfig.DEBUG) {
                throw e
            }
        } catch (e2: NullPointerException) {
            if (BuildConfig.DEBUG) {
                throw e2
            }
        } catch (e3: IllegalArgumentException) {
            if (BuildConfig.DEBUG) {
                throw e3
            }
        }
    }

}