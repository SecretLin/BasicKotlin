package com.example.basickotlin

import android.app.Application
import com.example.basickotlin.data.WordDatabase

class MyApplication : Application() {
    val database: WordDatabase by lazy {
        WordDatabase.getDatabase(this)
    }
}