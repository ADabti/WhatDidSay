package com.example.whatdidsay

import HadithRepository
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.whatdidsay.Screens.HadithListScreen
import com.example.whatdidsay.ViewModel.HadithViewModel
import com.example.whatdidsay.ui.theme.WhatDidSayTheme

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: HadithViewModel

    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = HadithRepository(applicationContext) // Create an instance of HadithRepository

        viewModel = HadithViewModel(repository) // Instantiate HadithViewModel

        setContent {
            WhatDidSayTheme {
              //  viewModel.deleteAllHadiths()
                Log.d("hell", viewModel.allHadiths.value.toString())
                HadithListScreen(viewModel)

            }
        }
    }

}

