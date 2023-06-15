package com.example.whatdidsay.Screens

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.whatdidsay.Models.Hadith
import com.example.whatdidsay.ViewModel.HadithViewModel

@Composable
fun HadithListScreen(hadithViewModel: HadithViewModel) {
    val hadithList by hadithViewModel.allHadiths.collectAsState(initial = emptyList())
    Log.d("HadithListScreen", "Hadiths size: ${hadithList.size}")

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(hadithList) { hadith ->
            Text(text = hadith.text)
        }
    }
}