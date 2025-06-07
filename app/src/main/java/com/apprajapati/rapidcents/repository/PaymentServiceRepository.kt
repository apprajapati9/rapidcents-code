package com.apprajapati.rapidcents.repository

import com.apprajapati.rapidcents.PaymentResult
import com.apprajapati.rapidcents.ReceiptState
import com.apprajapati.rapidcents.presentation.new_sale.Card
import kotlin.random.Random

class PaymentServiceRepository : PaymentServiceAPI {

    override suspend fun startTransaction(
        amount: Long,
        cardDetails: Card,
        receiptState: ReceiptState
    ): PaymentResult {

        val random = Random.nextInt(0, 2)
        if (random == 0) {
            return PaymentResult.Approved(amount, cardDetails, receiptState)
        //Mimicking API call here. ideally retrofit api call here to get data in the form of DTO class
        } else {
            return PaymentResult.Declined("Something went wrong")
        }
    }
}