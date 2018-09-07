package com.kotlin.douban.demo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kotlin.douban.demo.R
import com.kotlin.douban.demo.bean.Movie
import kotlinx.android.synthetic.main.item_card_view.view.*



class RecyclerViewAdapter(): RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder>() {
    private lateinit var mList:ArrayList<Movie>
    private lateinit var mContext: Context
    private lateinit var mInflater: LayoutInflater

    constructor(context: Context, list:ArrayList<Movie>):this() {
        mContext = context
        mList = list
        mInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomViewHolder {
        var view = mInflater.inflate(R.layout.item_is_playing_movie, p0, false)

        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(p0: CustomViewHolder, p1: Int) {
        if (p1 < mList.size) {
            val options = RequestOptions().centerCrop()
            Glide.with(mContext)
                    .load(mList[p1].iconUrl)
                    .apply(options)
                    .into(p0.iv)

            p0.type.text = mList[p1].type
            p0.grade.text = mList[p1].grade.toString()
            p0.name.text = mList[p1].name
        }
    }

    class CustomViewHolder(view:View) : RecyclerView.ViewHolder(view) {
        var iv: ImageView = view.findViewById(R.id.iv_movie_icon)
        var type:TextView = view.findViewById(R.id.tv_movie_type)
        var grade:TextView = /*view.findViewById(R.id.tv_movie_grade)*/view.tv_movie_grade
        var name:TextView = /*view.findViewById(R.id.tv_movie_name)*/view.tv_movie_name
    }
}
