package com.example.basickotlin.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

data class Word(
    val code: String,
    val datas: List<Data>,
    val msg: String
)

/**
 * 英文单词Bean
 */
@Entity(tableName = "english_word")
@Parcelize
data class Data (
    val class_id: String,
    val class_title: String,
    val course: String,
    @ColumnInfo(name = "desc")
    val desc: String,
    @PrimaryKey
    val name: String,
    val sound: String,
    val symbol: String,
    var remark: String? // 备注。自定义属性
) : Parcelable