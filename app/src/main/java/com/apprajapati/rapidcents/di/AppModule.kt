package com.apprajapati.rapidcents.di

import com.apprajapati.rapidcents.repository.PaymentServiceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providePaymentServiceRepository(): PaymentServiceRepository {
        return PaymentServiceRepository()
    }
}