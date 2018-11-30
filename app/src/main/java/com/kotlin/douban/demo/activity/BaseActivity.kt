package com.kotlin.douban.demo.activity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

@SuppressLint("Registered")
abstract class BaseActivity: AppCompatActivity() {
    private lateinit var mContext: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getResId())
        mContext = this
        initView()
        initData()
    }
d 
    open fun initView() {}

    open fun initData() {}

    abstract fun getResId():Int
}
