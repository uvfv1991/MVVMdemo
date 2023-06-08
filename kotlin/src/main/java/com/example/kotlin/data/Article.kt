package com.example.kotlin.data

import android.os.Parcel
import android.os.Parcelable

/**
 *  author : jiangxue
 *  date : 2023/6/2 9:07
 *  description :
 */
/*会自动生成包含所有属性的构造方法。kotlin是支持参数设置默认值的。怎么让kotlin自动生成多个构造函数的重载呢？这里，可以使用kotlin提供的注解：@JvmOverloads*/

data class Article(
     var apkLink: String,
     var author: String,
     var chapterId: Int,
     var chapterName: String,
     var collect: Boolean,
     var courseId: Int,
     var desc: String?,//类型后面加?表示可为空
     var envelopePic: String,
     var top: Boolean,
     var fresh: Boolean,
     var id: Int,
     var link: String,
     var niceDate: String,
     var origin: String,
     var projectLink: String,
     var publishTime: Long,
     var superChapterId: Int,
     var superChapterName: String,
     var title: String,
     var type: Int,
     var userId: Int,
     var visible: Int,
     var zan: Int,
     var tags: List<Tag>){
     override fun toString(): String {
          return "Article(apkLink='$apkLink', author='$author', chapterId=$chapterId, chapterName='$chapterName', collect=$collect, courseId=$courseId, desc='$desc', envelopePic='$envelopePic', top=$top, fresh=$fresh, id=$id, link='$link', niceDate='$niceDate', origin='$origin', projectLink='$projectLink', publishTime=$publishTime, superChapterId=$superChapterId, superChapterName='$superChapterName', title='$title', type=$type, userId=$userId, visible=$visible, zan=$zan, tags=$tags)"
     }
}