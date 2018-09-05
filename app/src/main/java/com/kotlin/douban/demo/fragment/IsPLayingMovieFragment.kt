package com.kotlin.douban.demo.fragment

import android.widget.*
import com.kotlin.douban.demo.R
import com.kotlin.douban.demo.api.ApiService
import com.kotlin.douban.demo.bean.Movie
import com.kotlin.douban.demo.bean.MoviesBean
import com.kotlin.douban.demo.common.ContactCommon
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.OkHttpClient
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import io.reactivex.disposables.Disposable




class IsPLayingMovieFragment:BaseFragment() {
    private lateinit var mLv:ListView
    private lateinit var mAdapter: Adapter
    private lateinit var mListData: ArrayList<Movie>
    private val retrofit: Retrofit? = null

    override fun getLayoutResId(): Int {
        return R.layout.fragment_movie
    }

    override fun initView() {
        mLv = getBaseView().findViewById(R.id.lv) as ListView
    }

    override fun initData() {
        mListData = arrayListOf()

        val client =  OkHttpClient()
                .newBuilder().readTimeout(60, TimeUnit.MINUTES)
                .connectTimeout(12, TimeUnit.MINUTES)
                .build()

        val observerTest = object : Observer<MoviesBean> {
            override fun onNext(p0: MoviesBean) {
            }

            override fun onComplete() {
            }

            override fun onSubscribe(p0: Disposable) {
            }

            override fun onError(p0: Throwable) {
            }

        }

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(ContactCommon.douBanUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        val service:ApiService = retrofit.create(ApiService::class.java)
        val observer = service.getIsPlayingMovie(0, 20)
        observer.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread() as Scheduler)
                .subscribe(observerTest)

//        mAdapter = IsPlayingMovieAdapter(context, mListData)
//        mAdapter.let {
//            mLv.adapter = it as BaseAdapter
//        }
    }
}
