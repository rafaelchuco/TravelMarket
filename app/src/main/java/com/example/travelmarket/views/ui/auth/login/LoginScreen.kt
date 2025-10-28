package com.example.travelmarket.views.ui.auth.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.core.storage.TokenManager
import com.example.travelmarket.logic.viewmodels.auth.LoginViewModel
import com.example.travelmarket.views.navigation.Routes
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = koinViewModel(),
    tokenManager: TokenManager = koinInject()
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginState by viewModel.loginState.collectAsState()

    LaunchedEffect(loginState) {
        when (loginState) {
            is NetworkResult.Success -> {
                val loginResponse = (loginState as NetworkResult.Success).data
                tokenManager.saveTokens(
                    accessToken = loginResponse.accessToken,
                    refreshToken = loginResponse.refreshToken
                )
                navController.navigate(Routes.Home.route) {  // ✅ BIEN
                    popUpTo(Routes.Login.route) { inclusive = true }
                }
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Iniciar Sesión") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Usuario") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.login(username, password)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = username.isNotEmpty() && password.isNotEmpty()
            ) {
                Text("Iniciar Sesión")
            }

            when (loginState) {
                is NetworkResult.Loading -> {
                    Spacer(modifier = Modifier.height(16.dp))
                    CircularProgressIndicator()
                }
                is NetworkResult.Error -> {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Error: ${(loginState as NetworkResult.Error).message}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
                null -> {}  // ✅ AGREGADO: Estado inicial
                else -> {}
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = {
                    navController.navigate(Routes.Register.route)
                }
            ) {
                Text("¿No tienes cuenta? Regístrate")
            }
        }
    }
}