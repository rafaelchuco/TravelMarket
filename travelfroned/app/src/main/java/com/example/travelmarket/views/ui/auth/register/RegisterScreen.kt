package com.example.travelmarket.views.ui.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.viewmodels.auth.RegisterViewModel
import com.example.travelmarket.views.navigation.Routes
import com.example.travelmarket.views.ui.auth.components.AuthButton
import com.example.travelmarket.views.ui.auth.components.AuthHeader
import com.example.travelmarket.views.ui.auth.components.AuthTextField
import com.example.travelmarket.views.ui.auth.components.AuthTextButton

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordConfirm by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var nationality by remember { mutableStateOf("") }
    var passportNumber by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var passwordConfirmVisible by remember { mutableStateOf(false) }

    val registerState by viewModel.registerState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(registerState) {
        when (registerState) {
            is NetworkResult.Success -> {
                snackbarHostState.showSnackbar(
                    message = "✅ Registro exitoso. Redirigiendo...",
                    duration = SnackbarDuration.Short
                )
                kotlinx.coroutines.delay(1000)
                navController.navigate(Routes.Login.route) {
                    popUpTo(Routes.Register.route) { inclusive = true }
                }
            }
            else -> {}
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))  // ✅ FONDO GRIS CLARO
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // ✅ HEADER
            AuthHeader(
                title = "Crear Cuenta",
                subtitle = "Completa tus datos para registrarte"
            )

            // ✅ FORMULARIO EN CARD BLANCA
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // ===== DATOS DE ACCESO =====
                    Text(
                        text = "Datos de Acceso",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFFDC143C),
                        fontWeight = FontWeight.Bold
                    )

                    AuthTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = "Usuario *",
                        leadingIcon = Icons.Default.Person
                    )

                    AuthTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email *",
                        leadingIcon = Icons.Default.Email
                    )

                    AuthTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "Contraseña *",
                        leadingIcon = Icons.Default.Lock,
                        visualTransformation = if (passwordVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = null,
                                    tint = Color(0xFFDC143C)
                                )
                            }
                        },
                        supportingText = "Mínimo 8 caracteres"
                    )

                    AuthTextField(
                        value = passwordConfirm,
                        onValueChange = { passwordConfirm = it },
                        label = "Confirmar Contraseña *",
                        leadingIcon = Icons.Default.Lock,
                        visualTransformation = if (passwordConfirmVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        trailingIcon = {
                            IconButton(onClick = { passwordConfirmVisible = !passwordConfirmVisible }) {
                                Icon(
                                    imageVector = if (passwordConfirmVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = null,
                                    tint = Color(0xFFDC143C)
                                )
                            }
                        },
                        isError = password.isNotEmpty() && passwordConfirm.isNotEmpty() && password != passwordConfirm
                    )

                    if (password.isNotEmpty() && passwordConfirm.isNotEmpty() && password != passwordConfirm) {
                        Text(
                            text = "❌ Las contraseñas no coinciden",
                            color = Color(0xFFC62828),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = Color(0xFFE0E0E0))
                    Spacer(modifier = Modifier.height(8.dp))

                    // ===== INFORMACIÓN PERSONAL =====
                    Text(
                        text = "Información Personal",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFFDC143C),
                        fontWeight = FontWeight.Bold
                    )

                    AuthTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        label = "Nombre *",
                        leadingIcon = Icons.Default.Badge
                    )

                    AuthTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        label = "Apellidos *",
                        leadingIcon = Icons.Default.Badge
                    )

                    AuthTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = "Teléfono (Opcional)",
                        leadingIcon = Icons.Default.Phone
                    )

                    AuthTextField(
                        value = nationality,
                        onValueChange = { nationality = it },
                        label = "Nacionalidad *",
                        leadingIcon = Icons.Default.Public,
                        supportingText = "No podrá editarse después"
                    )

                    AuthTextField(
                        value = passportNumber,
                        onValueChange = { passportNumber = it },
                        label = "Pasaporte (Opcional)",
                        leadingIcon = Icons.Default.CardTravel
                    )

                    // ✅ ERROR
                    when (registerState) {
                        is NetworkResult.Error -> {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFFFEBEE)
                                )
                            ) {
                                Text(
                                    text = (registerState as NetworkResult.Error).message,
                                    color = Color(0xFFC62828),
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }
                        else -> {}
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // ✅ BOTÓN REGISTRAR
                    AuthButton(
                        text = "Registrarse",
                        onClick = {
                            viewModel.registerUser(
                                username = username,
                                email = email,
                                password = password,
                                passwordConfirm = passwordConfirm,
                                firstName = firstName,
                                lastName = lastName,
                                phone = phone.ifEmpty { null },
                                nationality = nationality,
                                passportNumber = passportNumber.ifEmpty { null },
                                address = null,
                                city = null,
                                country = null
                            )
                        },
                        enabled = username.isNotEmpty() &&
                                email.isNotEmpty() &&
                                password.isNotEmpty() &&
                                passwordConfirm.isNotEmpty() &&
                                password == passwordConfirm &&
                                firstName.isNotEmpty() &&
                                lastName.isNotEmpty() &&
                                nationality.isNotEmpty() &&
                                registerState !is NetworkResult.Loading
                    )

                    if (registerState is NetworkResult.Loading) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color(0xFFDC143C))
                        }
                    }
                }
            }

            // ✅ LINK LOGIN (FUERA DE LA CARD)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "¿Ya tienes cuenta?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF212121)  // ✅ NEGRO OSCURO
                )
                Spacer(modifier = Modifier.width(4.dp))
                AuthTextButton(
                    text = "Inicia sesión",
                    onClick = {
                        navController.navigate(Routes.Login.route) {
                            popUpTo(Routes.Register.route) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
