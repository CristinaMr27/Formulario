package com.example.formulario.ui.screens.request_screen

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.formulario.ui.screens.request_screen.components.RequestForm
import com.example.formulario.ui.screens.request_screen.RequestViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestScreen(
    navController: NavController,
    viewModel: RequestViewModel = hiltViewModel(),
) {
    val formState by viewModel.formState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(formState.successMessage) {
        formState.successMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            viewModel.clearForm()
        }
    }

    RequestForm(
        formState = formState,
        onTitleChange = viewModel::updateTitle,
        onDescriptionChange = viewModel::updateDescription,
        onCategoryChange = viewModel::updateCategory,
        onPriorityChange = viewModel::updatePriority,
        onEmailChange = viewModel::updateEmail,
        onSubmit = viewModel::submitRequest,
        onViewRequests = {
            viewModel.loadRequests()
            navController.navigate("list")
        },
        onRetry = viewModel::retry,
        onBackClick = {
            navController.popBackStack()
        }
    )
}
