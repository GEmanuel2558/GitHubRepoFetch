package gresanu.emanuel.vasile.githubrepofetch.view.adapter.ui

import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import gresanu.emanuel.vasile.githubrepofetch.service.model.Contributors
import gresanu.emanuel.vasile.githubrepofetch.view.adapter.recyclerview.ContributorsAdapter
import java.net.URL


@BindingAdapter("visibleGone")
fun showHide(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.GONE
}


@BindingAdapter("imageUrl")
fun setImage(view: ImageView, imageUrl: String) {
   Thread {
       val image: Bitmap = BitmapFactory.decodeStream(URL(imageUrl).openConnection().getInputStream());
       Handler(Looper.getMainLooper()).post {
           view.setImageBitmap(image)
       }
   }.start()
}


@BindingAdapter("setContributors")
fun setAdapter(view: RecyclerView, repoItems: List<Contributors>?) {
    if (repoItems == null) {
        return
    }
    val layoutManager = view.layoutManager
    if (layoutManager == null) {
        view.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
    }
    var adapter = view.adapter as ContributorsAdapter?
    if (adapter == null) {
        adapter = ContributorsAdapter(repoItems)
        view.adapter = adapter
    }
}