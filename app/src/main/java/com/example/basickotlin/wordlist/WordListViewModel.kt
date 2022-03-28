package com.example.basickotlin.wordlist

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.basickotlin.data.Data

class WordListViewModel : ViewModel() {
    /*
     保存recyclerview的state，方便回到滑动位置
     */
    private lateinit var state: Parcelable
    fun saveRecyclerViewState(parcelable: Parcelable) {
        state = parcelable
    }

    fun restoreRecyclerViewState(): Parcelable = state
    fun stateInitialized(): Boolean = ::state.isInitialized


    private val _word = MutableLiveData<List<Data>>()
    val word: LiveData<List<Data>> = _word
    fun saveDataList(list: List<Data>) {
        _word.value = list
    }


}