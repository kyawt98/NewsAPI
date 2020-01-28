package com.kyawt.newsapikotlin.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kyawt.newsapikotlin.api.ArticlesApi
import com.kyawt.newsapikotlin.model.ArticleResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleViewModel : ViewModel(){

    var results : MutableLiveData<ArticleResult> = MutableLiveData()

    var articalLoadError : MutableLiveData<Boolean> = MutableLiveData()

    var loading : MutableLiveData<Boolean> = MutableLiveData()

    fun getResults() : LiveData<ArticleResult> = results

    fun getError() : LiveData<Boolean> = articalLoadError

    fun getLoading() : LiveData<Boolean> = loading

    private val articleApi: ArticlesApi = ArticlesApi()

    fun loadResults(){

        loading.value = true

        val call = articleApi.getResults()
        call.enqueue(object : Callback<ArticleResult>{
            override fun onFailure(call: Call<ArticleResult>, t: Throwable) {
                articalLoadError.value = true
                loading.value = false

            }

            override fun
                    onResponse(call: Call<ArticleResult>, response: Response<ArticleResult>) {
                response.isSuccessful.let {

                    loading.value = false

                    val resultList = ArticleResult(response?.body()?.articles?: emptyList())
                    results.value = resultList

                }
            }

        })
    }
}