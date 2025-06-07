package com.apprajapati.rapidcents.presentation.navigation.state

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface NavigationKey : NavKey

sealed interface Auth: NavigationKey {
    @Serializable
    object Login : Auth
}

@Serializable
sealed interface Tabs : NavigationKey {
    @Serializable
    object NewSale : Tabs

    @Serializable
    object Refund : Tabs

    @Serializable
    object Void : Tabs

    @Serializable
    object History : Tabs
}