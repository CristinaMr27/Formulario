package com.example.formulario.ui.screens.request_screen.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun ErrorDialog(
    message: String,
    onRetry: () -> Unit,
    onDismiss: () -> Unit
) {
    val simplifiedMessage = when {
        message.contains("timeout", ignoreCase = true) || message.contains("timed out", ignoreCase = true) -> 
            "Connection timed out. Please try again."
        message.contains("network", ignoreCase = true) || message.contains("connection", ignoreCase = true) -> 
            "Connection error. Please check your internet connection."
        message.contains("server", ignoreCase = true) || message.contains("500", ignoreCase = true) -> 
            "Server is unavailable. Please try again later."
        message.contains("unauthorized", ignoreCase = true) || message.contains("401", ignoreCase = true) -> 
            "Authentication error. Please verify your credentials."
        message.contains("not found", ignoreCase = true) || message.contains("404", ignoreCase = true) -> 
            "Resource not found."
        else -> "An unexpected error occurred. Please try again."
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Error",
                tint = MaterialTheme.colorScheme.error
            )
        },
        title = {
            Text(text = "Error")
        },
        text = {
            Text(text = simplifiedMessage)
        },
        confirmButton = {
            TextButton(onClick = onRetry) {
                Text("Retry")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}
