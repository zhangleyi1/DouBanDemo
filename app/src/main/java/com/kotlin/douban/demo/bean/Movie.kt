package com.kotlin.douban.demo.bean

class Movie() {
    lateinit var iconUrl:String
    var grade:Double = 0.0
    lateinit var name:String
    lateinit var type:String

    constructor(bitmapUrl: String, grade: Double, name: String,type:String) : this() {
        iconUrl = bitmapUrl
        this.grade = grade
        this.name = name
        this.type = type
    }
}
