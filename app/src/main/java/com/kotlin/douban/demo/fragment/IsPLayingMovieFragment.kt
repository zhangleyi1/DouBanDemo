package com.kotlin.douban.demo.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Handler
import android.os.Message
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.AbsListView
import android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE
import android.widget.Adapter
import android.widget.ListView
import com.kotlin.douban.demo.R
import com.kotlin.douban.demo.Utils.LogUtils
import com.kotlin.douban.demo.adapter.IsPlayingMovieAdapter
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
import retrofit2.adapter.rxjava2.HttpException
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.*
import java.util.concurrent.TimeUnit

class IsPLayingMovieFragment:BaseFragment(), AbsListView.OnScrollListener {
    private lateinit var mLv:ListView
    private lateinit var mAdapter: Adapter
    private lateinit var mListData: ArrayList<Movie>
    private lateinit var mSwipRefreshLayout: SwipeRefreshLayout
    private lateinit var footerView: View

    override fun getLayoutResId(): Int {
        return R.layout.fragment_movie
    }

    @SuppressLint("InflateParams")
    override fun initView() {
        mLv = getBaseView().findViewById(R.id.lv) as ListView
        mLv.setOnScrollListener(this)

        mSwipRefreshLayout = getBaseView().findViewById(R.id.swipe_refresh_layout) as SwipeRefreshLayout
        mSwipRefreshLayout.setColorSchemeColors(Color.parseColor("#FF0000"))
        mSwipRefreshLayout.setOnRefreshListener({
            LogUtils.d("zly --> OnRefreshListener")
//            Handler.po
//            mSwipRefreshLayout.isRefreshing = false
            mHandler.sendEmptyMessage(0)
        })

    }

    override fun initData() {
        mListData = arrayListOf()
        mAdapter = IsPlayingMovieAdapter(context, mListData)
        mAdapter.let {
            mLv.adapter = it as IsPlayingMovieAdapter
        }

        getNetData(0, 20)
    }

    var handlerWork:Handler = Handler()
    var mHandler: Handler = Handler {
        message: Message? ->
        when (message!!.what) {
            0 -> {
                LogUtils.d("zly -->mHandler 0.")
                handlerWork.post(Runnable {
                    kotlin.run {
                        LogUtils.d("zly -->handlerWork run.")
                        getNetData(0, 20)
                    }
                })
                true
            }
            else -> {
                true
            }
        }
    }

    private val observerMovieBean = object : Observer<MoviesBean> {
        override fun onNext(p0: MoviesBean) {
            LogUtils.d("zly -->Observer onNext.")
            LogUtils.d("zly --> start:" + p0.start + " count" + p0.count)
            for (data in p0.subjects) {
                val movie = Movie(data.images.small, data.rating.average, data.title, data.genres.toString())
                mListData.add(movie)
            }
            (mAdapter as IsPlayingMovieAdapter).notifyDataSetChanged()
        }

        override fun onComplete() {
            LogUtils.d("zly -->Observer onComplete.")
            mSwipRefreshLayout.isRefreshing = false
            footerView = layoutInflater.inflate(R.layout.lv_footer,null)
//            mLv.addFooterView(footerView)
        }

        override fun onSubscribe(p0: Disposable) {
            LogUtils.d("zly -->Observer onSubscribe.")
        }

        override fun onError(p0: Throwable) {
            LogUtils.d("zly -->Observer onError:" + p0.toString())
            if (p0 is HttpException) {
                val httpException = p0 as HttpException
                val code = httpException.code()
                if (code == 500 || code == 404) {
                    LogUtils.d("zly --> 服务器出错!")
                }
            } else if (p0 is ConnectException) {
                LogUtils.d("zly --> 网络断开,请打开网络!")
            } else if (p0 is SocketTimeoutException) {
                LogUtils.d("zly --> 网络连接超时!")
            } else {
                LogUtils.d("zly --> 发生未知错误:" + p0.message)
            }
            var i = 0
            while (i++<100) {
                var movie = Movie("1", 9.0, "11", "111")
                mListData.add(movie)
            }
            (mAdapter as IsPlayingMovieAdapter).notifyDataSetChanged()
        }
    }

 /*   private fun getLatestData() {
        val client =  OkHttpClient()
                .newBuilder().readTimeout(60, TimeUnit.MINUTES)
                .connectTimeout(12, TimeUnit.MINUTES)
                .build()

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(ContactCommon.douBanUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
        val service:ApiService = retrofit.create(ApiService::class.java)
        val observer = service.getIsPlayingMovie(0, 20)
        observer.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(observerMovieBean)
    }*/

    private fun getNetData(start:Int, count:Int) {
        if (start == 0) {
            mListData.clear()
        }

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
        val service:ApiService = retrofit.create(ApiService::class.java)
        val observer = service.getIsPlayingMovie(start, count)
        observer.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observerMovieBean)
    }

    private var hasFooterView: Boolean = false
    override fun onScroll(p0: AbsListView?, p1: Int, p2: Int, p3: Int) {
        LogUtils.d("zly --> firstVisibleItem:$p1 visibleItemCount:$p2 totalItemCount:$p3")
        var loadtotal = p3
        var lastItemid = mLv.lastVisiblePosition
        if ((lastItemid+1) == loadtotal && loadtotal > 0) {
            var currentpage = if (loadtotal%20 == 0) loadtotal/20 else loadtotal/20+1
            var nextPage = currentpage + 1
            LogUtils.d("zly --> onScroll currentpage:$currentpage nextPage:$nextPage")
            if (nextPage < 5) {
                getNetData(currentpage * 20, 20)
//                if (hasFooterView) {
//                    mLv.removeFooterView(footerView)
//                    hasFooterView = false
//                }
            }
//             else if (!hasFooterView) {
//                hasFooterView = true
//                mLv.addFooterView(footerView)
//            }
        }
    }

    override fun onScrollStateChanged(p0: AbsListView?, p1: Int) {
        LogUtils.d("zly --> onScrollStateChanged:$p1")
        val position = p0!!.lastVisiblePosition
        if ((p1 == SCROLL_STATE_IDLE) && (position == (p0.count-1))) {
        }
    }
}
