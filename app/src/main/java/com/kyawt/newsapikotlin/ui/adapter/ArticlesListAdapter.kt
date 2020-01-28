package com.kyawt.newsapikotlin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kyawt.newsapikotlin.R
import com.kyawt.newsapikotlin.model.Article
import com.kyawt.newsapikotlin.toSimpleString
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.item_articles.view.*

class ArticlesListAdapter(var articleList: List<Article> = ArrayList()) :
    RecyclerView.Adapter<ArticlesListAdapter.ArticlesViewHolder>() {

    var mClickListener : ClickListener? =null //assign null into ClickListener interface

    fun setOnClickListener(clickListener: ClickListener) {  // 1...assign clickListener into mClickListener
        this.mClickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_articles, parent, false)
        return ArticlesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        holder.bindArticle(articleList.get(position))
    }

    fun updateList(article: List<Article>) {
        this.articleList = article
        notifyDataSetChanged()
    }

    inner class ArticlesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var view: View = itemView
        private lateinit var article: Article

        init {
            itemView.setOnClickListener(this)
        }

        fun bindArticle(article: Article) {
            this.article = article
            Picasso.get()
                .load(article.urlToImage)
                .placeholder(R.drawable.article)
                .into(view.articleImage)
            view.articleTitle.text = article.title
            view.articleDescription.text = article.description
            view.articleDate.text = toSimpleString(article.publishedAt)
        }

        override fun onClick(v: View?) {
            mClickListener?.onClick(article) //2.. article.onclickListener
        }
    }

    interface ClickListener {
        fun onClick(article: Article)
    }

}