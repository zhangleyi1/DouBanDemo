package com.kotlin.douban.demo.fragment

import android.annotation.SuppressLint
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.kotlin.douban.demo.R
import com.kotlin.douban.demo.Utils.LogUtils
import com.kotlin.douban.demo.adapter.RecyclerViewAdapter
import com.kotlin.douban.demo.api.ApiService
import com.kotlin.douban.demo.bean.Movie
import com.kotlin.douban.demo.bean.MoviesBean
import com.kotlin.douban.demo.common.ContactCommon
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class WillPlayMovieFragment:BaseFragment() {

    private lateinit var mRy: RecyclerView
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mListData:ArrayList<Movie>
    private lateinit var mAdapter: RecyclerViewAdapter

    override fun getLayoutResId(): Int {
        return R.layout.fragment_will_play_movie
    }

    @SuppressLint("ResourceAsColor")
    override fun initView() {
        mSwipeRefreshLayout = getBaseView().findViewById(R.id.swipe_refresh_layout) as SwipeRefreshLayout
        mSwipeRefreshLayout.setColorSchemeColors(android.R.color.holo_green_light)
        mSwipeRefreshLayout.setOnRefreshListener {
            LogUtils.d("zly --> OnRefreshListener.")
            getNetData(0, 20)
        }

        mRy = getBaseView().findViewById(R.id.ry) as RecyclerView

    }

    override fun initData() {
        mListData = arrayListOf<Movie>()
        mAdapter = RecyclerViewAdapter(context!!, mListData)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mRy.layoutManager = layoutManager
//        mRy.layoutManager = GridLayoutManager(context, 3)
        mRy.itemAnimator = DefaultItemAnimator()
        mRy.adapter = mAdapter
    }

    private val observerMovieBean = object: Observer<MoviesBean> {
        override fun onComplete() {
            LogUtils.d("zly --> observerMovieBean onComplete")
            mSwipeRefreshLayout.isRefreshing = false
        }

        override fun onSubscribe(d: Disposable) {
            LogUtils.d("zly --> observerMovieBean onSubscribe")
        }

        override fun onNext(t: MoviesBean) {
            LogUtils.d("zly --> observerMovieBean onNext")
            mListData.clear()
            for (movie in t.subjects) {
                var data = Movie(movie.images.small, movie.rating.average, movie.title, movie.genres.toString())
                mListData.add(data)
            }
            LogUtils.d("zly --> mListData.size: ${mListData.size}")
            mAdapter.notifyDataSetChanged()
        }

        override fun onError(e: Throwable) {
            LogUtils.d("zly --> observerMovieBean onError")
            Toast.makeText(context, "onError!", Toast.LENGTH_LONG).show()
        }
    }

    private fun getNetData(start:Int, count:Int) {

        val client =  OkHttpClient()
                .newBuilder().readTimeout(60, TimeUnit.MINUTES)
                .connectTimeout(12, TimeUnit.MINUTES)
                .build()

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(ContactCommon.douBanUrl)
                //.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
        val service: ApiService = retrofit.create(ApiService::class.java)
        val observer = service.getWillPlayMovie(start, count)
        observer.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observerMovieBean)
    }
}