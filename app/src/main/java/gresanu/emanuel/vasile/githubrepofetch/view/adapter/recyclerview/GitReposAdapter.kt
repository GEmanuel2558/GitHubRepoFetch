package gresanu.emanuel.vasile.githubrepofetch.view.adapter.recyclerview

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import gresanu.emanuel.vasile.githubrepofetch.R
import gresanu.emanuel.vasile.githubrepofetch.databinding.RecyclerGitDetailsBinding
import gresanu.emanuel.vasile.githubrepofetch.service.model.GitRepo
import gresanu.emanuel.vasile.githubrepofetch.view.callback.ProjectClickCallback

class GitReposAdapter(private val projectClickCallback: ProjectClickCallback?) : RecyclerView.Adapter<GitReposAdapter.ProjectViewHolder>() {

    private var gitRepoList: List<GitRepo>? = null

    fun setProjectList(gitRepoList: List<GitRepo>) {
        if (this.gitRepoList == null) {
            this.gitRepoList = gitRepoList
            notifyItemRangeInserted(0, gitRepoList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return gitRepoList!!.size
                }

                override fun getNewListSize(): Int {
                    return gitRepoList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return gitRepoList!![oldItemPosition].id === gitRepoList[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val project = gitRepoList[newItemPosition]
                    val old = gitRepoList[oldItemPosition]
                    return project.id === old.id && project.git_url == old.git_url
                }
            })
            this.gitRepoList = gitRepoList
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val binding = DataBindingUtil
            .inflate<RecyclerGitDetailsBinding>(
                LayoutInflater.from(parent.context), R.layout.recycler_git_details,
                parent, false
            )

        binding.callback = projectClickCallback

        return ProjectViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.binding.gitRepo = gitRepoList!![position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return if (gitRepoList == null) 0 else gitRepoList!!.size
    }

    class ProjectViewHolder(val binding: RecyclerGitDetailsBinding) : RecyclerView.ViewHolder(binding.root)
}
