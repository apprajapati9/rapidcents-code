package com.apprajapati.rapidcents.presentation.new_sale

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun CardDetailsSection(
    onCardNumber: (Boolean, String) -> Unit,
    onExpiry: (Boolean, String) -> Unit,
    onCvv: (Boolean, String) -> Unit
) {
    var cardNumber by remember { mutableStateOf("1239998371273617") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = cardNumber,
            onValueChange = {
                cardNumber = it.filter { ch -> ch.isDigit() }.take(16)
                onCardNumber(cardNumber.length != 16, cardNumber)
            },
            label = { Text("Card Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = expiryDate,
            onValueChange = {
                expiryDate = it.filter { ch -> ch.isDigit() }.take(4)
                onExpiry(expiryDate.isValidExpiry(), expiryDate)
            },
            label = { Text("Expiry (MM/YY)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = cvv,
            onValueChange = {
                cvv = it.filter { ch -> ch.isDigit() }.take(3)
                onCvv(cvv.length == 3, cvv)
            },
            label = { Text("CVV") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

fun String.isValidExpiry(): Boolean {
    if (this.isEmpty()) {
        return false
    }
    if (this.length > 4) {
        return false
    }

    if(this.length < 4){
        return false
    }

    val month = this.substring(0, 2).toInt()
    val year = this.substring(2, 4).toInt()

    return ((month > 0 && month <= 12)
            && year > 24)
}
