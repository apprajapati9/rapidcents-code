package com.apprajapati.rapidcents.presentation.new_sale

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.apprajapati.rapidcents.CardInputMethod

@Composable
fun CardInputMethodSection(
    onCardMethodSelected: (CardInputMethod) -> Unit
) {
    var selectedMethod by remember { mutableStateOf<CardInputMethod?>(CardInputMethod.MANUAL) }

    Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
        Text("Select Card Input Method", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            CardInputMethod.entries.forEach { method ->
                FilterChip(
                    modifier = Modifier.weight(0.3f).padding(5.dp),
                    selected = selectedMethod == method,
                    onClick = {
                        selectedMethod = method
                        onCardMethodSelected(method)
                    },
                    label = { Text(method.label) },
                    shape = RoundedCornerShape(10.dp)
                )
            }
        }
    }
}
