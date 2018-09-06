package com.kotlin.douban.demo.api

import com.kotlin.douban.demo.bean.MoviesBean
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/v2/movie/in_theaters")
    fun getIsPlayingMovie(@Query("start") start: Int, @Query("count") count: Int): Observable<MoviesBean>

}