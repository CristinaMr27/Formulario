package com.example.formulario.ui.screens.request_screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.formulario.data.model.Request
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestList(
    requests: List<Request>,
    isLoading: Boolean,
    errorMessage: String?,
    onRetry: () -> Unit,
    onRefresh: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("All Requests") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Go back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = onRefresh,
                        enabled = !isLoading
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        if (isLoading) {
            LoadingIndicator(
                modifier = Modifier.padding(paddingValues)
            )
        } else if (requests.isEmpty() && errorMessage == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No requests found",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(requests) { request ->
                    RequestCard(request)
                }
            }
        }
        
        if (errorMessage != null) {
            ErrorDialog(
                message = errorMessage,
                onRetry = onRetry,
                onDismiss = onRetry
            )
        }
    }
}

fun formatDate(date: String?): String {
    return date?.let {
        it.take(10)
    } ?: "No date"
}
