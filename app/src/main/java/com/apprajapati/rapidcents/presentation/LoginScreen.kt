package com.apprajapati.rapidcents.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(onLogin : () -> Unit) {
    var username by remember { mutableStateOf("admin") }
    var password by remember { mutableStateOf("admin") }

    var confirmPassword by remember { mutableStateOf("admin") }
    var initialState by remember {
        mutableStateOf(true)
    }
    val isPasswordMatched by remember {
        derivedStateOf {
            (confirmPassword == password) && confirmPassword.isNotEmpty()
        }
    }


    Column(
        modifier = Modifier
            .imePadding()
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(modifier = Modifier.padding(5.dp),
            text = "Login",
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(5.dp),
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(5.dp),
            value = password,
            onValueChange = { if (it.length <= 32) { //32 char pass limit
                password = it
            } },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        // Confirm Password Field
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(5.dp),
            value = confirmPassword,
            onValueChange = {
                initialState = false
                confirmPassword = it
            },
            label = { Text("Confirm Password") },
            isError = !isPasswordMatched && !initialState,
            visualTransformation = PasswordVisualTransformation(),
            supportingText = {
                if (!isPasswordMatched && confirmPassword.isNotEmpty()) {
                    Text("Passwords do not match", color = MaterialTheme.colorScheme.error)
                }
            },
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (username == "admin" && password == "admin" && confirmPassword == "admin" &&  isPasswordMatched) {
                    onLogin()
                } else {

                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen({})
}

