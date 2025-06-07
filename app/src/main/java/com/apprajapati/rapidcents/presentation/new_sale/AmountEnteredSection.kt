package com.apprajapati.rapidcents.presentation.new_sale

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.text.NumberFormat
import java.util.Currency

@Composable
fun EnterAmountSection(
    modifier: Modifier = Modifier,
    currencySymbol: String = "$",
    maxAmount: Double = 10000.0,
    onAmountEntered: (Double) -> Unit
) {
    var rawInput by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(error) {
        if (error != null && error!!.isNotEmpty()) {
            delay(2000)
            error = null
        }
    }

    val numberFormat = remember {
        NumberFormat.getCurrencyInstance().apply {
            maximumFractionDigits = 2
            currency = Currency.getInstance("USD")
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        OutlinedTextField(
            value = rawInput,
            onValueChange = { input ->
                val filtered = input.filter { it.isDigit() || it == '.' }

                val dotCount = filtered.count { it == '.' }
                if (dotCount > 1) {
                    error = "Can't have more than 1 decimal point"
                    return@OutlinedTextField
                }
                val parts = filtered.split(".")
                if (parts.size > 1 && parts[1].length > 2) {
                    error = "Only 2 decimal places allowed"
                    return@OutlinedTextField
                }

                val amount = filtered.toDoubleOrNull()

                when {
                    amount == null -> {
                        error = "Invalid number"
                    }
                    amount > maxAmount -> {
                        error = "Amount exceeds max limit: ${numberFormat.format(maxAmount)}"
                    }
                    else -> {
                        error = null
                        rawInput = filtered
                        onAmountEntered(amount)
                    }
                }
            },
            label = { Text("Enter Amount") },
            isError = error != null,
            supportingText = {
                error?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            leadingIcon = {
                Text(currencySymbol)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = modifier.fillMaxWidth()
        )
    }

}
