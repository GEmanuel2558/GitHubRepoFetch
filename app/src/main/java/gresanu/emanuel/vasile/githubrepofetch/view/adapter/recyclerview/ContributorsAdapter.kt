package gresanu.emanuel.vasile.githubrepofetch.view.adapter.recyclerview

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gresanu.emanuel.vasile.githubrepofetch.R
import gresanu.emanuel.vasile.githubrepofetch.databinding.RecyclerGitContributorsBinding
import gresanu.emanuel.vasile.githubrepofetch.service.model.Contributors

class ContributorsAdapter(val listOfItems: List<Contributors>): RecyclerView.Adapter<ContributorsAdapter.BindingHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): BindingHolder {
        val currentView = DataBindingUtil.inflate<RecyclerGitContributorsBinding>(LayoutInflater.from(parent.context), R.layout.recycler_git_contributors, parent, false)
        return BindingHolder(currentView.root)
    }

    override fun onBindViewHolder(container: BindingHolder, position: Int) {
        val currentItem = listOfItems[position]
        container.bindHolder?.apply {
            contributors = currentItem
        }
    }

    override fun getItemCount() = listOfItems.size


    inner class BindingHolder(view: View): RecyclerView.ViewHolder(view) {
        val bindHolder: RecyclerGitContributorsBinding? = DataBindingUtil.bind(view)
    }
}