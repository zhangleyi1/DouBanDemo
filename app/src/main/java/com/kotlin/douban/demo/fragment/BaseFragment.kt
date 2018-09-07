package com.kotlin.douban.demo.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.douban.demo.Utils.LogUtils

abstract class BaseFragment: Fragment() {

    private lateinit var mView: View

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        LogUtils.d("zly --> onActivityCreated begin.")
        initView()
        initData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        LogUtils.d("zly --> onCreateView begin.")
        mView = inflater.inflate(getLayoutResId(), container, false)
        return mView
    }

    abstract fun getLayoutResId():Int
    abstract fun initView()
    abstract fun initData()

    open fun getBaseView():View {
        return mView
    }
}