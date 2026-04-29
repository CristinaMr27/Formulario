package com.example.formulario.ui.screens.request_screen.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.formulario.R
import com.example.formulario.data.model.Request
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@RequiresApi(Build.VERSION_CODES.O)
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
    val sortedRequests = remember(requests) {
        requests.sortedByDescending { it.createdAt ?: "" }
    }

    val filterState = rememberDateFilterState()

    val filteredRequests = remember(
        sortedRequests,
        filterState.dateFilterTypeValue,
        filterState.customDateTextValue
    ) {
        sortedRequests.filter { request ->
            filterByDate(
                requestDate = request.createdAt,
                filterType = filterState.dateFilterTypeValue,
                customDateText = filterState.customDateTextValue,
                today = filterState.today,
                formatter = filterState.formatter
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.all_requests)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.go_back))
                    }
                },
                actions = {
                    IconButton(
                        onClick = onRefresh,
                        enabled = !isLoading
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = stringResource(R.string.refresh)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        if (isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(5) {
                        RequestCardSkeleton()
                    }
                }
            }
        } else if (requests.isEmpty() && errorMessage == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.no_requests_found),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                DateFilterSection(
                    filterState = filterState,
                    onFilterTypeChange = { newType ->
                        filterState.dateFilterTypeValue = newType
                        filterState.customDateErrorValue = null
                    },
                    onCustomDateChange = { newValue ->
                        filterState.customDateTextValue = newValue
                        filterState.dateFilterTypeValue = DateFilterType.CUSTOM_DAY
                        filterState.customDateErrorValue =
                            validateCustomDate(newValue, filterState.formatter)
                    },
                    onClearCustomDate = {
                        filterState.customDateTextValue = ""
                        filterState.customDateErrorValue = null
                        filterState.dateFilterTypeValue = DateFilterType.ALL
                    }
                )

                if (filteredRequests.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        val msg = when (filterState.dateFilterTypeValue) {
                            DateFilterType.ALL ->
                                stringResource(R.string.no_requests_found)
                            else ->
                                stringResource(R.string.no_requests_for_date)

                        }

                        Text(
                            text = msg,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(filteredRequests) { request ->
                            RequestCard(request)
                        }
                    }
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

private enum class DateFilterType { ALL, TODAY, LAST_7_DAYS, THIS_MONTH, CUSTOM_DAY }

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun rememberDateFilterState(): DateFilterState {
    val formatter = remember { DateTimeFormatter.ISO_LOCAL_DATE }
    val today = remember { LocalDate.now() }

    return remember {
        DateFilterState(
            dateFilterType = mutableStateOf(DateFilterType.ALL),
            customDateText = mutableStateOf(""),
            customDateError = mutableStateOf(null),
            formatter = formatter,
            today = today
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private data class DateFilterState(
    val dateFilterType: MutableState<DateFilterType>,
    val customDateText: MutableState<String>,
    val customDateError: MutableState<String?>,
    val formatter: DateTimeFormatter,
    val today: LocalDate
) {
    var dateFilterTypeValue: DateFilterType
        get() = dateFilterType.value
        set(value) {
            dateFilterType.value = value
        }

    var customDateTextValue: String
        get() = customDateText.value
        set(value) {
            customDateText.value = value
        }

    var customDateErrorValue: String?
        get() = customDateError.value
        set(value) {
            customDateError.value = value
        }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun DateFilterSection(
    filterState: DateFilterState,
    onFilterTypeChange: (DateFilterType) -> Unit,
    onCustomDateChange: (String) -> Unit,
    onClearCustomDate: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = stringResource(R.string.filter_by_date),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DateFilterChip(
                selected = filterState.dateFilterTypeValue == DateFilterType.ALL,
                label = stringResource(R.string.filter_all),
                onClick = {
                    onFilterTypeChange(DateFilterType.ALL)
                    filterState.customDateErrorValue = null
                }
            )
            DateFilterChip(
                selected = filterState.dateFilterTypeValue == DateFilterType.TODAY,
                label = stringResource(R.string.filter_today),
                onClick = {
                    onFilterTypeChange(DateFilterType.TODAY)
                    filterState.customDateErrorValue = null
                }
            )
            DateFilterChip(
                selected = filterState.dateFilterTypeValue == DateFilterType.LAST_7_DAYS,
                label = stringResource(R.string.filter_last_7_days),
                onClick = {
                    onFilterTypeChange(DateFilterType.LAST_7_DAYS)
                    filterState.customDateErrorValue = null
                }
            )
            DateFilterChip(
                selected = filterState.dateFilterTypeValue == DateFilterType.THIS_MONTH,
                label = stringResource(R.string.filter_this_month),
                onClick = {
                    onFilterTypeChange(DateFilterType.THIS_MONTH)
                    filterState.customDateErrorValue = null
                }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = filterState.customDateTextValue,
            onValueChange = onCustomDateChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.filter_custom_label)) },
            placeholder = { Text(stringResource(R.string.filter_custom_placeholder)) },
            singleLine = true,
            isError = filterState.customDateErrorValue != null,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = stringResource(R.string.filter_date_icon)
                )
            },
            trailingIcon = {
                if (filterState.customDateTextValue.isNotBlank()) {
                    IconButton(onClick = onClearCustomDate) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.filter_delete_date)
                        )
                    }
                }
            }
        )

        filterState.customDateErrorValue?.let { errorText ->
            Text(
                text = errorText,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
private fun DateFilterChip(
    selected: Boolean,
    label: String,
    onClick: () -> Unit
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(label) }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
private fun validateCustomDate(
    value: String,
    formatter: DateTimeFormatter
): String? {
    if (value.isBlank()) return null

    return try {
        LocalDate.parse(value.trim(), formatter)
        null
    } catch (e: DateTimeParseException) {
        "Invalid format. Use YYYY-MM-DD"
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun filterByDate(
    requestDate: String?,
    filterType: DateFilterType,
    customDateText: String,
    today: LocalDate,
    formatter: DateTimeFormatter
): Boolean {
    val createdStr = requestDate?.take(10) ?: return false
    val createdDate = try {
        LocalDate.parse(createdStr, formatter)
    } catch (e: DateTimeParseException) {
        return false
    }

    return when (filterType) {
        DateFilterType.ALL -> true
        DateFilterType.TODAY -> createdDate == today
        DateFilterType.LAST_7_DAYS -> !createdDate.isBefore(today.minusDays(7))
        DateFilterType.THIS_MONTH ->
            createdDate.year == today.year && createdDate.month == today.month
        DateFilterType.CUSTOM_DAY -> {
            if (customDateText.isBlank()) {
                true
            } else {
                try {
                    val custom = LocalDate.parse(customDateText.trim(), formatter)
                    createdDate == custom
                } catch (e: DateTimeParseException) {
                    false
                }
            }
        }
    }
}

fun formatDate(date: String?): String {
    return date?.let {
        it.take(10)
    } ?: "No date"
}
