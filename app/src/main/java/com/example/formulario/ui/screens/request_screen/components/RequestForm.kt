package com.example.formulario.ui.screens.request_screen.components

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.formulario.data.model.FormState

@ExperimentalMaterial3Api
@Composable
fun RequestForm(
    formState: FormState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onPriorityChange: (Int) -> Unit,
    onEmailChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onViewRequests: () -> Unit,
    onRetry: () -> Unit,
    onBackClick: () -> Unit
) {
    val isTitleValid = formState.title.length in 5..60
    val isDescriptionValid = formState.description.length in 20..500
    val isEmailValid = formState.email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(formState.email).matches()
    val isFormValid = isTitleValid && isDescriptionValid && isEmailValid && formState.category.isNotEmpty()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Request") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

       Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            Card(
                modifier = Modifier
                    .padding(top = 16.dp, start = 12.dp, end = 12.dp, bottom = 12.dp)
                    .fillMaxSize(),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.weight(1f)
                    ) {

                        OutlinedTextField(
                            value = formState.title,
                            onValueChange = onTitleChange,
                            label = { Text("Title") },
                            isError = formState.title.isNotEmpty() && !isTitleValid,
                            supportingText = {
                                if (formState.title.isNotEmpty() && !isTitleValid) {
                                    Text("Must be between 5 and 60 characters")
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        )

                        OutlinedTextField(
                            value = formState.description,
                            onValueChange = onDescriptionChange,
                            label = { Text("Description") },
                            isError = formState.description.isNotEmpty() && !isDescriptionValid,
                            supportingText = {
                                if (formState.description.isNotEmpty() && !isDescriptionValid) {
                                    Text("Must be between 20 and 500 characters")
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            shape = RoundedCornerShape(16.dp)
                        )

                        CategoryDropdown(
                            selectedCategory = formState.category,
                            onCategoryChange = onCategoryChange
                        )

                        PrioritySlider(
                            priority = formState.priority,
                            onPriorityChange = onPriorityChange
                        )

                        OutlinedTextField(
                            value = formState.email,
                            onValueChange = onEmailChange,
                            label = { Text("Email") },
                            isError = formState.email.isNotEmpty() && !isEmailValid,
                            supportingText = {
                                if (formState.email.isNotEmpty() && !isEmailValid) {
                                    Text("Please enter a valid email")
                                }
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        )

                        if (formState.isLoading) {
                            LoadingIndicator()
                        }

                        if (formState.successMessage != null) {
                            SuccessCard(message = formState.successMessage)
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = onSubmit,
                            enabled = !formState.isLoading && isFormValid,
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            shape = RoundedCornerShape(18.dp)
                        ) {
                            Text(if (formState.isLoading) "Sending..." else "Submit")
                        }

                        Button(
                            onClick = onViewRequests,
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            shape = RoundedCornerShape(18.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary
                            )
                        ) {
                            Text("View Requests")
                        }
                    }
                }
            }
        }
        
        if (formState.errorMessage != null) {
            ErrorDialog(
                message = formState.errorMessage,
                onRetry = {
                    onRetry()
                },
                onDismiss = {
                    onRetry()
                }
            )
        }
    }
}
