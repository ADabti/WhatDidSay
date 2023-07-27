package com.example.whatdidsay.ViewModel

import HadithRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatdidsay.Models.Hadith
import com.example.whatdidsay.ViewModel.HadithViewModel
import kotlinx.coroutines.launch

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.*
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import com.example.whatdidsay.DataBase.AppDatabase

class HadithViewModel(private val repository: HadithRepository) : ViewModel() {

    private val _allHadiths = MutableStateFlow<PagingData<Hadith>>(PagingData.empty())
    private val _searchResults = MutableStateFlow<PagingData<Hadith>>(PagingData.empty())
    val searchResults: StateFlow<PagingData<Hadith>> = _searchResults
    val allHadiths: StateFlow<PagingData<Hadith>> = _allHadiths

    init {
        viewModelScope.launch {
            repository.getAllHadiths().collectLatest { pagingData ->
                _allHadiths.value = pagingData
            }
        }
    }

    fun insert(hadith: Hadith) = viewModelScope.launch {
        repository.insert(hadith)
    }



    fun deleteAllHadiths() = viewModelScope.launch {
        repository.deleteAllHadiths()
    }

    fun searchHadiths(query: String) {
        viewModelScope.launch {
            repository.searchDatabase(query).collectLatest { pagingData ->
                _searchResults.value = pagingData
            }
        }
    }
}
