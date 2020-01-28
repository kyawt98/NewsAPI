package com.kyawt.newsapikotlin.ui

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.kyawt.newsapikotlin.R
import com.kyawt.newsapikotlin.model.Article
import com.kyawt.newsapikotlin.model.ArticleResult
import com.kyawt.newsapikotlin.ui.adapter.ArticlesListAdapter
import com.kyawt.newsapikotlin.viewmodel.ArticleViewModel
import com.kyawt.newsapikotlin.viewmodel.SelectedArticleViewModel
import kotlinx.android.synthetic.main.fragment_article_list.*

/**
 * A simple [Fragment] subclass.
 */
class ArticleListFragment : Fragment(),
ArticlesListAdapter.ClickListener{

    private lateinit var articleListAdapter: ArticlesListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var articlesViewModel: ArticleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewManager = LinearLayoutManager(activity)

        articleListAdapter = ArticlesListAdapter()
        recycler_articles.adapter = articleListAdapter
        recycler_articles.layoutManager = viewManager
        articleListAdapter.setOnClickListener(this)
        observeViewModel()
    }

    fun observeViewModel() {
        articlesViewModel = ViewModelProviders
            .of(this)
            .get(ArticleViewModel::class.java)
        articlesViewModel.getResults().observe(this, Observer<ArticleResult> { result ->
            recycler_articles.visibility = View.VISIBLE
            articleListAdapter.updateList(result.articles)
        })

        articlesViewModel.getError().observe(this, Observer<Boolean> { isError ->
            if (isError) {
                txtError.visibility = View.VISIBLE
                recycler_articles.visibility = View.GONE
            } else {
                txtError.visibility = View.GONE

            }
        })

        articlesViewModel.getLoading().observe(this, Observer<Boolean> { isLoading ->
            loadingView.visibility = (if (isLoading) View.VISIBLE else View.INVISIBLE)
            if (isLoading) {
                txtError.visibility = View.GONE
                recycler_articles.visibility = View.GONE
            }
        })
    }

    override fun onResume() {
        super.onResume()
        articlesViewModel.loadResults()
    }

    override fun onClick(article: Article) { //create function for onClickListener interface of adapter

        if (!TextUtils.isEmpty(article.url)){
            val selectedArticalViewModel : SelectedArticleViewModel =
                ViewModelProviders.of(activity!!).get(SelectedArticleViewModel::class.java)
            selectedArticalViewModel.selectedArticle(article)
            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.screen_container,DetailsFragment())
                .addToBackStack(null) //when pressing back key
                .commit()
        }

    }


}
