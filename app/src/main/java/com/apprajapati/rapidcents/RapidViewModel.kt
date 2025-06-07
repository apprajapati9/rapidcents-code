package com.apprajapati.rapidcents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apprajapati.rapidcents.presentation.new_sale.Card
import com.apprajapati.rapidcents.repository.PaymentServiceRepository
import com.apprajapati.rapidcents.usecases.PerformTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class CardInputMethod(val label: String) {
    CHIP("Chip"),
    TAP("Tap"),
    MANUAL("Manual Entry")
}

sealed class CardDetectionResult {
    data class Detected(val cardType: String) : CardDetectionResult()
    object Idle : CardDetectionResult()
    data class Approved(val result : PaymentResult) : CardDetectionResult()
    object Done : CardDetectionResult()
}

enum class ReceiptState{
    SHOWN,
    PRINTED
}

sealed class PaymentResult {
    data class Approved(val amount: Long, val cardDetails: Card, val receiptState: ReceiptState) : PaymentResult()
    data class Declined(val error: String) : PaymentResult()
    object Cancelled : PaymentResult()
    object None : PaymentResult()
    object Processing : PaymentResult()
    object Validating : PaymentResult()
}

@HiltViewModel
class RapidViewModel @Inject constructor(private val transactionUseCase: PerformTransactionUseCase) : ViewModel() {

    private var _paymentResult = MutableStateFlow<PaymentResult>(PaymentResult.None)
    val paymentResult : StateFlow<PaymentResult> = _paymentResult

    fun submitPayment(amount: Long, cardDetails: Card){
        viewModelScope.launch {
            transactionUseCase.invoke(amount = amount, card = cardDetails).collect {
                result ->
                when(result){
                    is Resource.Success -> {
                        _paymentResult.value = result.data
                    }
                    is Resource.Error -> {
                        _paymentResult.value = PaymentResult.Declined(result.message ?: "Something went wrong")
                    }
                    is Resource.Loading -> {
                        _paymentResult.value = PaymentResult.Processing
                    }
                }
            }
        }
    }

    fun resetPaymentState() {
        _paymentResult.update {
            PaymentResult.None
        }
    }
}