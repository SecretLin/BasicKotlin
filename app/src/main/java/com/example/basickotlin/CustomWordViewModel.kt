package com.example.basickotlin

import androidx.lifecycle.*
import com.example.basickotlin.data.Data
import com.example.basickotlin.data.WordDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class WordViewModelFactory(val wordDao: WordDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomWordViewModel::class.java)) {
            return CustomWordViewModel(wordDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}

class CustomWordViewModel(val wordDao: WordDao) : ViewModel() {

    var dataList: LiveData<List<Data>> = wordDao.getAllData().asLiveData()

    var word = MutableLiveData<Data>(null)

    // 返回list的位置
    fun getPosition(data: Data): Int {
        return dataList.value?.indexOf(data) ?: -1
    }


    fun insert(data: Data) {
        viewModelScope.launch {
            wordDao.insert(data)
        }
    }

    fun update(data: Data) {
        viewModelScope.launch {
            wordDao.update(data)
        }
    }


    fun getData(data: Data): LiveData<Data> {
        return wordDao.getData(data.name).asLiveData()
    }

    fun delete(data: Data) {
        viewModelScope.launch {
            wordDao.delete(data)
        }
    }


}