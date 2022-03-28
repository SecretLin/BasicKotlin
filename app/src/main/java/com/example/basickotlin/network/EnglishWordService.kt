package com.example.basickotlin.network

import com.example.basickotlin.data.Word
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

private const val BASE_URL = "http://rw.ylapi.cn/reciteword/"

const val UID = 12057
const val APPKEY = "dbf4c1384147aa7e5c64a716805a691d"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface EnglishWordService {
    @FormUrlEncoded
    @POST("wordlist.u")
    suspend fun getData(
        @Field("uid") uid: Int,
        @Field("appkey") appkey: String,
        @Field("class_id") class_id: String,
        @Field("course") course: String
    ): Word
}

object EnglishWordApi {
    val retrofitService: EnglishWordService by lazy {
        retrofit.create()
    }
}