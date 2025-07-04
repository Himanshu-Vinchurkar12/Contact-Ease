package com.example.contact

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.contact.data.DataBase.DataBaseInit
import com.example.contact.data.repository.ContactRepository
import com.example.contact.presentation.ContactViewModel
import com.example.contact.presentation.navigation.appNavigation.AppNavigation

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val repository = ContactRepository(DataBaseInit.getDatabase(this).dao)
            val viewModel = viewModel {
                ContactViewModel(repository)
            }
            AppNavigation(viewModel = viewModel)

        }
    }
}

