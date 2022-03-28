package com.example.basickotlin.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(data: Data)

    @Update
    suspend fun update(data: Data)

    @Delete
    suspend fun delete(data: Data)

    @Query("select *from english_word")
    fun getAllData(): Flow<List<Data>>

    @Query("select *from english_word where name = :name")
    fun getData(name: String): Flow<Data>

}