package com.kotlin.douban.demo.fragment

import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPager
import com.kotlin.douban.demo.R
import com.kotlin.douban.demo.adapter.MainContentAdapter

class MainContentFragment:BaseFragment() {
    lateinit var tl:TabLayout
    lateinit var vp:ViewPager

    enum class TypeFragment {
        IsPlayingMovie,
        WillPlayMovie,
        Top250Movie
    }

    override fun initData() {
        //init TabLayout
        tl.tabMode = TabLayout.MODE_FIXED
        tl.setTabTextColors(ContextCompat.getColor(context!!, android.R.color.holo_red_light), ContextCompat.getColor(context!!, android.R.color.holo_blue_light))
        tl.setSelectedTabIndicatorColor(ContextCompat.getColor(context!!, android.R.color.holo_green_light))
        ViewCompat.setElevation(tl, 10F)
        tl.setupWithViewPager(vp)

        //init content
        val tabListFragment = arrayListOf<BaseFragment>()
        tabListFragment.add(IsPLayingMovieFragment())
        tabListFragment.add(WillPlayMovieFragment())
        tabListFragment.add(Top250MovieFragment())

        val tabListTitle = arrayListOf<String>()
        tabListTitle.add("正在热映")
        tabListTitle.add("即将放映")
        tabListTitle.add("Top250")

        val adapter = MainContentAdapter(childFragmentManager, tabListTitle, tabListFragment)
        vp.adapter = adapter
    }

    override fun initView() {
        tl = getBaseView().findViewById(R.id.tl) as TabLayout
        vp = getBaseView().findViewById(R.id.vp) as ViewPager
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_main_context
    }

}