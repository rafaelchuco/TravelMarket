package com.example.travelmarket.views.ui.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.core.storage.TokenManager
import com.example.travelmarket.logic.viewmodels.auth.LoginViewModel
import com.example.travelmarket.views.navigation.Routes
import com.example.travelmarket.views.ui.auth.components.AuthButton
import com.example.travelmarket.views.ui.auth.components.AuthHeader
import com.example.travelmarket.views.ui.auth.components.AuthTextField
import com.example.travelmarket.views.ui.auth.components.AuthTextButton

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
    tokenManager: TokenManager = hiltViewModel()
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val loginState by viewModel.loginState.collectAsState()

    LaunchedEffect(loginState) {
        when (loginState) {
            is NetworkResult.Success -> {
                val loginResponse = (loginState as NetworkResult.Success).data
                tokenManager.saveTokens(
                    accessToken = loginResponse.accessToken,
                    refreshToken = loginResponse.refreshToken
                )
                navController.navigate(Routes.Home.route) {
                    popUpTo(Routes.Login.route) { inclusive = true }
                }
            }
            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // ✅ HEADER ROJO CON LOGO
            AuthHeader(
                title = "Bienvenido",
                subtitle = "Inicia sesión para continuar"
            )

            // ✅ SPACER PARA CENTRAR MEJOR
            Spacer(modifier = Modifier.height(40.dp))

            // ✅ FORMULARIO EN CARD BLANCA
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // ✅ CAMPO USUARIO
                    AuthTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = "Usuario",
                        leadingIcon = Icons.Default.Person,
                        isError = loginState is NetworkResult.Error
                    )

                    // ✅ CAMPO CONTRASEÑA
                    AuthTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "Contraseña",
                        leadingIcon = Icons.Default.Lock,
                        visualTransformation = if (passwordVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) {
                                        Icons.Default.Visibility
                                    } else {
                                        Icons.Default.VisibilityOff
                                    },
                                    contentDescription = if (passwordVisible) {
                                        "Ocultar contraseña"
                                    } else {
                                        "Mostrar contraseña"
                                    },
                                    tint = Color(0xFFDC143C)
                                )
                            }
                        },
                        isError = loginState is NetworkResult.Error
                    )

                    // ✅ MENSAJE DE ERROR
                    when (loginState) {
                        is NetworkResult.Error -> {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFFFEBEE)
                                )
                            ) {
                                Text(
                                    text = (loginState as NetworkResult.Error).message,
                                    color = Color(0xFFC62828),
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }
                        else -> {}
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // ✅ BOTÓN INICIAR SESIÓN
                    AuthButton(
                        text = "Iniciar Sesión",
                        onClick = { viewModel.login(username, password) },
                        enabled = username.isNotEmpty() &&
                                password.isNotEmpty() &&
                                loginState !is NetworkResult.Loading
                    )

                    // ✅ LOADING
                    if (loginState is NetworkResult.Loading) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color(0xFFDC143C))
                        }
                    }
                }
            }

            // ✅ SPACER ADICIONAL
            Spacer(modifier = Modifier.height(24.dp))

            // ✅ LINK REGISTRO
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "¿No tienes cuenta?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF212121)  // ✅ NEGRO OSCURO
                )
                Spacer(modifier = Modifier.width(4.dp))
                AuthTextButton(
                    text = "Regístrate",
                    onClick = { navController.navigate(Routes.Register.route) }
                )
            }

            // ✅ SPACER FINAL PARA PADDING INFERIOR
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
