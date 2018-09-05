package com.kotlin.douban.demo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.kotlin.douban.demo.R
import com.kotlin.douban.demo.Utils.LogUtils
import com.kotlin.douban.demo.bean.Movie

@Suppress("CAST_NEVER_SUCCEEDS")
class IsPlayingMovieAdapter():BaseAdapter() {

    private lateinit var mInflater: LayoutInflater
    private lateinit var mContext: Context
    private lateinit var mList: ArrayList<Movie>

    init {
        LogUtils.d("zly --> IsPlayingMovieAdapter init.")
    }

    constructor(context:Context?, list:ArrayList<Movie>):this() {
        LogUtils.d("zly --> IsPlayingMovieAdapter constructor.")
        mInflater = LayoutInflater.from(context)
        mContext = context as Context
        mList = list
    }

    @SuppressLint("SetTextI18n")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View? {
        var holder: ViewHolder
        val view: View?
        LogUtils.d("zly --> IsPlayingMovieAdapter getView p1:" + (null == p1))
        if (null == p1) {
            holder = ViewHolder()
            view = mInflater.inflate(R.layout.item_is_playing_movie, p2, false)
            holder.iv = view.findViewById(R.id.iv_movie_icon) as ImageView
            holder.grade = view.findViewById(R.id.tv_movie_grade) as TextView
            holder.name = view.findViewById(R.id.tv_movie_name) as TextView
            view.tag = holder
        } else {
            holder = p1.tag as ViewHolder
            view = p1
        }

        holder.grade.text = "9.0"
        holder.name.text = "Test"
        return view
    }

    override fun getItem(p0: Int): Any {
        return mList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return mList.size
    }

    class ViewHolder {
        lateinit var iv:ImageView
        lateinit var grade: TextView
        lateinit var name: TextView
    }
}