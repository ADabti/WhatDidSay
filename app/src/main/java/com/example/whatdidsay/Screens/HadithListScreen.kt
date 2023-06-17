package com.example.whatdidsay.Screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.whatdidsay.Models.Hadith
import com.example.whatdidsay.ViewModel.HadithViewModel


@Composable
fun HadithListScreen(viewModel: HadithViewModel) {
    val query = remember { mutableStateOf("") }
    val searchResults = viewModel.searchResults.collectAsState(initial = emptyList())

    Column {
        SearchBar(
            query = query.value,
            onQueryChange = { newQuery -> query.value = newQuery },
            onSearchExecute = { viewModel.searchHadiths(query.value) }
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(searchResults.value) { hadith ->
                HadithCard(hadith)
            }
        }
    }
}
@Composable
fun HadithCard(hadith: Hadith) {
    Card(modifier = Modifier.padding(8.dp), elevation = 8.dp) {
        Text(
            text = hadith.text,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(16.dp)
        )
    }
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchExecute: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = query,
        onValueChange = onQueryChange,
        label = { Text(text = "بحث") }, // "Search" in Arabic
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchExecute()
                keyboardController?.hide() // This will hide the keyboard
            }
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

