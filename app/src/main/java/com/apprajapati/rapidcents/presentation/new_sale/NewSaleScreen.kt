package com.apprajapati.rapidcents.presentation.new_sale

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.apprajapati.rapidcents.RapidViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.apprajapati.rapidcents.PaymentResult

@Composable
fun NewSaleScreen(viewModel: RapidViewModel = hiltViewModel()) {

    val paymentResult by viewModel.paymentResult.collectAsState()

    var cardData by remember {
        mutableStateOf(Card())
    }

    var amount by remember {
        mutableDoubleStateOf(0.0)
    }

    when (paymentResult) {
        is PaymentResult.Approved -> {
            ShowPaymentResult(message = "Payment Successful", color = Color.Green){
                viewModel.resetPaymentState()
                amount = 0.0
                cardData = Card()
            }
        }

        PaymentResult.Cancelled -> {
            ShowPaymentResult(message = "Payment Cancelled", color = Color.Red) {
                viewModel.resetPaymentState()
            }
        }

        is PaymentResult.Declined ->
            ShowPaymentResult(
                message = (paymentResult as PaymentResult.Declined).error,
                color = Color.Red
        ){
                viewModel.resetPaymentState()
                amount = 0.0
                cardData = Card()
            }

        PaymentResult.None -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                CardInputMethodSection { insertedCardType ->
                    {
                        Log.d("CardInputMethodSection", "Selected Card Type: $insertedCardType")
                    }
                }

                CardDetailsSection(
                    onCardNumber = { isValid, number ->
                        if (isValid) {
                            cardData.number = number
                        }
                    },
                    onExpiry = { isValid, expiry ->
                        if (isValid) {
                            cardData.expiry = expiry
                        }
                    },
                    onCvv = { isValid, cvv ->
                        if (isValid) {
                            cardData.cvv = cvv
                        }
                    }
                )
                EnterAmountSection { enteredAmount ->
                    amount = enteredAmount
                    Log.d("Ajay", "Entered Amount: $enteredAmount")
                }

                Button(onClick = {
                    viewModel.submitPayment(
                        amount = amount.toLong(),
                        cardDetails = cardData,
                    )
                }) {
                    Text(text = "Submit")
                }
            }
        }

        PaymentResult.Processing -> {
            ShowProgress()
        }

        PaymentResult.Validating -> {
            ShowProgress()
        }
    }
}

@Composable
fun ShowPaymentResult(message: String, color: Color, onReset: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = message,
            fontWeight = FontWeight.Bold,
            color = color)

        Button(onClick = onReset) {
            Text(text = "Back")
        }
    }
}


@Composable
fun ShowProgress() {
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator(
            modifier = Modifier.size(50.dp)
        )
    }

}

data class Card(
    var number: String = "",
    var expiry: String = "",
    var cvv: String = ""
) {
    fun validateCard(): Boolean = number.length != 16

    fun validateExpiry(): Boolean = expiry.isValidExpiry()

    fun validCVV(): Boolean = cvv.length == 3

    fun String.isValidExpiry(): Boolean {
        if (this.isEmpty()) {
            return false
        }
        if (this.length > 4) {
            return false
        }

        if (this.length < 4) {
            return false
        }

        val month = this.substring(0, 2).toInt()
        val year = this.substring(2, 4).toInt()

        return ((month > 0 && month <= 12)
                && year > 24)
    }

    fun isCardValid() = validateCard() && validateExpiry() && validCVV()
}