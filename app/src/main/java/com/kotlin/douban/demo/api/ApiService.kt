package com.kotlin.douban.demo.api

import com.kotlin.douban.demo.bean.MoviesBean
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.GET

interface ApiService {
    @GET("/v2/movie/in_theaters")
    fun getIsPlayingMovie(@Field("start") start: Int, @Field("count") count: Int): Observable<MoviesBean>

}