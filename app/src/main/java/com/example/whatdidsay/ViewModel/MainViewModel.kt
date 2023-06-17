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
import com.example.whatdidsay.DataBase.AppDatabase

class HadithViewModel(private val repository: HadithRepository) : ViewModel() {

    private val _allHadiths = MutableStateFlow<List<Hadith>>(emptyList())
    private val _searchResults = MutableStateFlow<List<Hadith>>(emptyList())
    val searchResults: StateFlow<List<Hadith>> = _searchResults
    val allHadiths: StateFlow<List<Hadith>> = _allHadiths

    init {

        viewModelScope.launch {
            repository.allHadiths.collect { hadiths ->
                Log.d("HadithViewModel", "Hadiths from repository: $hadiths")  // <-- Add this line
                _allHadiths.value = hadiths
            }
        }


    }

    fun insert(hadith: Hadith) = viewModelScope.launch {
        repository.insert(hadith)
    }

    fun searchDatabase(searchQuery: String) = viewModelScope.launch {
        val results = repository.searchDatabase(searchQuery)
        results.collect { hadiths ->
            _allHadiths.value = hadiths
        }
    }
    fun deleteAllHadiths() = viewModelScope.launch {
        repository.deleteAllHadiths()
    }
    fun searchHadiths(query: String) {
        viewModelScope.launch {
            val results = repository.allHadiths // Replace with your actual method of getting Hadiths
                .collect { list ->
                    val filteredList = list.filter { it.text.contains(query, ignoreCase = true) }
                    _searchResults.emit(filteredList)
                }
        }
    }
}
