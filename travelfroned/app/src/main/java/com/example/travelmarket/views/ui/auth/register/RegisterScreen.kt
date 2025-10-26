package com.example.travelmarket.views.ui.auth.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.viewmodels.auth.RegisterViewModel
import com.example.travelmarket.views.navigation.Routes
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = koinViewModel()
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
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }

    val registerState by viewModel.registerState.collectAsState()

    // ✅ Snackbar para mensaje de éxito
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(registerState) {
        when (registerState) {
            is NetworkResult.Success -> {
                snackbarHostState.showSnackbar(
                    message = "Usuario registrado exitosamente. Redirigiendo...",
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
        topBar = {
            TopAppBar(
                title = { Text("Registro de Usuario") }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }  // ✅ AGREGADO
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Campos obligatorios (*)",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Usuario *") },
                modifier = Modifier.fillMaxWidth(),
                isError = registerState is NetworkResult.Error &&
                        (registerState as NetworkResult.Error).message.contains("username", ignoreCase = true)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email *") },
                modifier = Modifier.fillMaxWidth(),
                isError = registerState is NetworkResult.Error &&
                        (registerState as NetworkResult.Error).message.contains("email", ignoreCase = true)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña *") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                supportingText = { Text("Mínimo 8 caracteres") },
                isError = registerState is NetworkResult.Error &&
                        (registerState as NetworkResult.Error).message.contains("password", ignoreCase = true)
            )

            OutlinedTextField(
                value = passwordConfirm,
                onValueChange = { passwordConfirm = it },
                label = { Text("Confirmar Contraseña *") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                isError = password.isNotEmpty() && passwordConfirm.isNotEmpty() && password != passwordConfirm
            )

            if (password.isNotEmpty() && passwordConfirm.isNotEmpty() && password != passwordConfirm) {
                Text(
                    text = "Las contraseñas no coinciden",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("Nombre *") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Apellidos *") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Teléfono (Opcional)") },
                modifier = Modifier.fillMaxWidth()
            )

            // ✅ CAMBIO: Nacionalidad REQUERIDA
            OutlinedTextField(
                value = nationality,
                onValueChange = { nationality = it },
                label = { Text("Nacionalidad *") },
                modifier = Modifier.fillMaxWidth(),
                supportingText = { Text("No podrá editarse después") }
            )

            OutlinedTextField(
                value = passportNumber,
                onValueChange = { passportNumber = it },
                label = { Text("Número de Pasaporte (Opcional)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Dirección (Opcional)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("Ciudad (Opcional)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = country,
                onValueChange = { country = it },
                label = { Text("País de Residencia (Opcional)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.registerUser(
                        username = username,
                        email = email.ifEmpty { null },
                        password = password,
                        passwordConfirm = passwordConfirm,
                        firstName = firstName.ifEmpty { null },
                        lastName = lastName.ifEmpty { null },
                        phone = phone.ifEmpty { null },
                        nationality = nationality.ifEmpty { null },
                        passportNumber = passportNumber.ifEmpty { null },
                        address = address.ifEmpty { null },
                        city = city.ifEmpty { null },
                        country = country.ifEmpty { null }
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = username.isNotEmpty() &&
                        email.isNotEmpty() &&
                        password.isNotEmpty() &&
                        passwordConfirm.isNotEmpty() &&
                        password == passwordConfirm &&  // ✅ AGREGAR validación
                        firstName.isNotEmpty() &&
                        lastName.isNotEmpty() &&
                        nationality.isNotEmpty() &&  // ✅ AGREGAR validación
                        registerState !is NetworkResult.Loading
            ) {
                Text("Registrar")
            }

            when (registerState) {
                is NetworkResult.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Registrando usuario...")
                    }
                }
                is NetworkResult.Error -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "Error al registrar:",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )

                            // ✅ MEJORADO: Parsear el mensaje de error
                            val errorMessage = (registerState as NetworkResult.Error).message
                            val friendlyMessage = when {
                                errorMessage.contains("username", ignoreCase = true) ->
                                    "El nombre de usuario ya existe o no es válido"
                                errorMessage.contains("email", ignoreCase = true) ->
                                    "El email ya existe o no es válido"
                                errorMessage.contains("password", ignoreCase = true) ->
                                    "La contraseña no cumple los requisitos"
                                errorMessage.contains("400") ->
                                    "Datos inválidos. Revisa los campos marcados"
                                errorMessage.contains("network", ignoreCase = true) ->
                                    "Error de conexión. Verifica tu internet"
                                else -> errorMessage
                            }

                            Text(
                                text = friendlyMessage,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                    }
                }
                null -> {}
                else -> {}
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = {
                    navController.navigate(Routes.Login.route) {
                        popUpTo(Routes.Register.route) { inclusive = true }
                    }
                }
            ) {
                Text("¿Ya tienes cuenta? Inicia sesión")
            }
        }
    }
}