package com.example.formulario.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.formulario.ui.screens.request_screen.RequestScreen
import com.example.formulario.ui.screens.request_screen.components.RequestList
import com.example.formulario.ui.screens.welcome_screen.WelcomeScreen
import com.example.formulario.ui.screens.request_screen.RequestViewModel

@Composable
fun AppNavHost() {

    val navController = rememberNavController()
    val viewModel: RequestViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {

        composable("welcome") {
            WelcomeScreen(
                onStartClick = {
                    navController.navigate("form")
                }
            )
        }

        composable("form") {
            RequestScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable("list") {
            val requests by viewModel.requests.collectAsState()
            val formState by viewModel.formState.collectAsState()
            RequestList(
                requests = requests,
                isLoading = formState.isLoading,
                errorMessage = formState.errorMessage,
                onRetry = viewModel::retry,
                onRefresh = viewModel::loadRequests,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
