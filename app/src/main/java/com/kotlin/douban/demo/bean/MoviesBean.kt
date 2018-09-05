package com.kotlin.douban.demo.bean

class MoviesBean() {
    var count:Int = 0
    var start:Int = 0
    var total:Int = 0

    lateinit var subjects:List<SubjectBean>
    var title = ""

    class SubjectBean {
        lateinit var rating:RatingBean
        lateinit var genres:List<String>
        lateinit var title:String
        lateinit var casts:List<CastsBean>
        var collect_count:Int = 0
        lateinit var original_title:String
        lateinit var subtype:String
        lateinit var directors:DirectorsBean
        lateinit var year:String
        lateinit var images:ImagesBean
        lateinit var alt:String
        var id:Int = 0
    }

    class RatingBean {
        var max:Int = 0
            get() = field
            set(value) {
                field = value
            }

        var average = 0.0
            get() = field
            set(value) {
                field = value
            }
        var stars = ""
            get() = field
            set(value) {
                field = value
            }
        var min = 0
            get() = field
            set(value) {
                field = value
            }
    }

    class CastsBean {
        lateinit var alt:String
        lateinit var avatars:AvatarsBean
        lateinit var name:String
        lateinit var id:String
    }

    class AvatarsBean {
        lateinit var small:String
        lateinit var large:String
        lateinit var medium:String
    }

    class DirectorsBean {
        lateinit var alt:String
        lateinit var avatars:AvatarsBean
        lateinit var name:String
        var id:Int = 0
    }

    class ImagesBean {
        lateinit var small:String
        lateinit var large:String
        lateinit var medium:String
    }
}