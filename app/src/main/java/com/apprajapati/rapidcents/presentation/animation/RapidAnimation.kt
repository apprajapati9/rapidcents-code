package com.apprajapati.rapidcents.presentation.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

fun getList() = listOf(
    "POS Terminals",
    "Simplified Transactions",
    "Chargeback Protection",
    "Automated Invoicing",
    "Recurring Payments"
)

@Composable
fun RapidCentsAnimation() {

    val list = getList()

    var index by remember {
        mutableIntStateOf(0)
    }

    var visible by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {

        while (true) {
            delay(1000)
            visible = !visible
            if (visible) {
                delay(1500)
            } else {
                delay(400)
                index = (index + 1)
                if (index == list.size) index = 0
            }
        }
    }

    val transition = rememberInfiniteTransition()

    val alpha by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            repeatMode = RepeatMode.Reverse,
            animation = tween(500, easing = LinearEasing)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(55, 94, 154), Color.White),
                    tileMode = TileMode.Repeated
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.height(70.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedVisibility(
                visible = visible,
                enter = expandHorizontally(
                    expandFrom = Alignment.Start,
                    animationSpec = tween(durationMillis = 400, easing = FastOutLinearInEasing)
                ),
                exit = shrinkHorizontally(
                    shrinkTowards = Alignment.Start,
                    animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
                )
            ) {
                Text(
                    modifier = Modifier
                        .wrapContentWidth()
                        .animateContentSize(),
                    text = list[index],
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Yellow
                    )
                )
            }

            Spacer(Modifier.size(3.dp))
            AnimatedVisibility(
                visible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    modifier = Modifier
                        .wrapContentWidth(), text = "|", style = TextStyle(
                        fontSize = 50.sp,
                        color = Color.Black.copy(alpha = alpha)
                    )
                )
            }
        }

    }
}