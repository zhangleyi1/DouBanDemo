package com.kotlin.douban.demo.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.kotlin.douban.demo.fragment.BaseFragment

class MainContentAdapter(fm:FragmentManager, var listTitle: ArrayList<String>, var listFragment: ArrayList<BaseFragment>): FragmentPagerAdapter(fm) {
    override fun getItem(p0: Int): Fragment {
        return listFragment[p0]
    }

    override fun getCount(): Int {
        return listFragment.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return listTitle[position]
    }
}