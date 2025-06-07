package com.apprajapati.rapidcents.presentation.navigation

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh

import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.apprajapati.rapidcents.presentation.LoginScreen
import com.apprajapati.rapidcents.presentation.animation.RapidCentsAnimation
import com.apprajapati.rapidcents.presentation.navigation.state.Auth
import com.apprajapati.rapidcents.presentation.navigation.state.NavigationKey
import com.apprajapati.rapidcents.presentation.navigation.state.Tabs
import com.apprajapati.rapidcents.presentation.new_sale.NewSaleScreen
import kotlinx.serialization.json.Json

val AppScreenKeySaver = Saver<NavigationKey, String>(
    save = { Json.encodeToString(NavigationKey.serializer(), it) },
    restore = { Json.decodeFromString(NavigationKey.serializer(), it) }
)

@Composable
fun RapidCentsNavigation() {
    val tabs = listOf(Tabs.NewSale, Tabs.Refund, Tabs.Void, Tabs.History)

    var isAuth by rememberSaveable {
        mutableStateOf(false)
    }

    var selectedTab by rememberSaveable(stateSaver = AppScreenKeySaver) {
        mutableStateOf(Tabs.NewSale)
    }

    val loginStack = rememberNavBackStack(Auth.Login)

    val tabBackStacks = remember {
        TabBackStacks<Tabs>(
            startDestinations = tabs.associateWith { it }
        )
    }
    val backStack = tabBackStacks.getStack(selectedTab as Tabs)

    Scaffold(
        Modifier.safeContentPadding(),
        topBar = {
            if (isAuth){
                RapidCentsAnimation()
            }else{
                Text(text = "RapidCents",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp)
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = isAuth,
            ) {
                NavigationBar{
                    tabs.forEach { tab ->
                        val label = when (tab) {
                            Tabs.History -> "History"
                            Tabs.NewSale -> "New Sale"
                            Tabs.Refund -> "Refund"
                            Tabs.Void -> "Void"
                        }
                        val icon = when (tab) {
                            Tabs.History -> Icons.Default.Refresh
                            Tabs.NewSale ->Icons.Default.Home
                            Tabs.Refund -> Icons.Default.Menu
                            Tabs.Void -> Icons.Default.Menu

                        }
                        BottomBarItem(
                            label = label,
                            icon = icon,
                            selected = selectedTab == tab
                        ) {
                            selectedTab = tab
                        }
                    }
                }
            }
        }
    ) { padding ->
        NavDisplay(
            backStack = if(!isAuth) loginStack else backStack,
            modifier = Modifier.padding(padding),
            onBack = { steps -> repeat(steps) { tabBackStacks.pop(selectedTab as Tabs) } },
            entryDecorators = listOf(
                rememberSceneSetupNavEntryDecorator(),
                rememberSavedStateNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            transitionSpec = {
                fadeIn(tween(300)) togetherWith fadeOut(tween(300))
            },
            entryProvider = entryProvider {
                entry<Auth.Login> {
                    LoginScreen {
                        isAuth = true
                    }
                }
                entry<Tabs.NewSale> {
                    NewSaleScreen()
                }
                entry<Tabs.Refund> { key ->
                }
                entry<Tabs.Void> {
                }
                entry<Tabs.History> {
                }

            }
        )
    }
}

class TabBackStacks<T : NavigationKey>(val startDestinations: Map<T, T>) {
    private val stacks = mutableStateMapOf<T, SnapshotStateList<T>>()

    init {
        startDestinations.forEach { (tab, root) ->
            stacks[tab] = mutableStateListOf(root)
        }
    }

    fun getStack(tab: T): SnapshotStateList<T> = stacks[tab]!!

    fun pop(tab: T) {
        stacks[tab]?.removeLastOrNull()
    }
}

@Composable
fun RowScope.BottomBarItem(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = { Icon(icon, contentDescription = label) },
        label = { Text(label) }
    )
}