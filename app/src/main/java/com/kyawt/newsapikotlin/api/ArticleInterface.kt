package com.kyawt.newsapikotlin.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import com.kyawt.newsapikotlin.model.ArticleResult as ArticleResult

interface ArticleInterface{

    @Headers("X-Api-Key: c4349a84570648eaa7be3cd673cc262b")
    @GET("v2/top-headlines?country=us")
    fun getArticles(): Call<ArticleResult>
}