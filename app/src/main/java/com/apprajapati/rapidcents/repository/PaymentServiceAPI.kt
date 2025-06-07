package com.apprajapati.rapidcents.repository

import com.apprajapati.rapidcents.PaymentResult
import com.apprajapati.rapidcents.ReceiptState
import com.apprajapati.rapidcents.presentation.new_sale.Card
import kotlinx.coroutines.flow.Flow

interface PaymentServiceAPI {

    suspend fun startTransaction(
        amount: Long,
        cardDetails: Card,
        receiptState: ReceiptState
    ): PaymentResult
}


