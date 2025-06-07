package com.apprajapati.rapidcents.usecases

import android.util.Log
import com.apprajapati.rapidcents.PaymentResult
import com.apprajapati.rapidcents.ReceiptState
import com.apprajapati.rapidcents.Resource
import com.apprajapati.rapidcents.presentation.new_sale.Card
import com.apprajapati.rapidcents.repository.PaymentServiceRepository
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PerformTransactionUseCase @Inject constructor(private val repository: PaymentServiceRepository) {
    operator fun invoke(
        card: Card,
        amount: Long,
    ): Flow<Resource<PaymentResult>> = flow {
        emit(Resource.Loading())
        delay(3000)

        Log.d("Ajay", "Card ${card.number} : ${card.expiry} : ${card.cvv}")
        Log.d("Ajay", "Amount $amount")


        if (!card.isCardValid()) {
            emit(Resource.Error("Invalid Card"))
        }

        if (amount <= 0) {
            emit(Resource.Error("Invalid Amount"))
        }

        if(amount > 0 && card.isCardValid()){
            val result = repository.startTransaction(
                cardDetails = card,
                amount = amount,
                receiptState = ReceiptState.SHOWN
            )
            when (result) {
                is PaymentResult.Approved -> {
                    emit(Resource.Success(result))
                }

                is PaymentResult.Declined -> {
                    emit(Resource.Error(result.error))
                }

                else -> {
                    emit(Resource.Error("Something went wrong"))
                }
            }
        }
    }
}