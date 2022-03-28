package com.example.basickotlin.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// 将 exportSchema 设为 false，这样就不会保留架构版本记录的备份。
@Database(entities = [Data::class], version = 1, exportSchema = false)
abstract class WordDatabase : RoomDatabase() {

    abstract fun dataDao(): WordDao

    companion object {

        @Volatile
        private var INSTANCE: WordDatabase? = null

        fun getDatabase(context: Context): WordDatabase {
            Log.d("WordDatabase", "get INSTANCE " + INSTANCE)
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordDatabase::class.java,
                    "word_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                Log.d("WordDatabase", "building database.." + instance)
                INSTANCE = instance
                return instance
            }

        }
    }
}