package com.example.contact.presentation.navigation.appNavigation


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.contact.presentation.ContactViewModel
import com.example.contact.presentation.navigation.routes.Routes
import com.example.contact.presentation.screen.AddEditScreenUI
import com.example.contact.presentation.screen.DetailScreenUI
import com.example.contact.presentation.screen.HomeScreenUI

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun AppNavigation(viewModel: ContactViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.HomeScreen) {
        composable<Routes.HomeScreen> {
            HomeScreenUI(viewModel = viewModel, navController = navController)
        }
        composable<Routes.AddEditScreen> {
            val addEditScreenParams = it.toRoute<Routes.AddEditScreen>()
            AddEditScreenUI(
                viewModel = viewModel,
                navController = navController,
                contactId = addEditScreenParams.contactId
            )
        }
        composable<Routes.DetailScreen> {
            val detailScreenParams = it.toRoute<Routes.DetailScreen>()
            DetailScreenUI(
                navController = navController,
                contactId = detailScreenParams.contactId,
                viewModel = viewModel
            )
        }
    }
}