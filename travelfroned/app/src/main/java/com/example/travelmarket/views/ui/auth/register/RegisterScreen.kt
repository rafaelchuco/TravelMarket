package com.example.travelmarket.views.ui.auth.register

import android.os.Build.VERSION_CODES.S
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.Badge
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.viewmodels.auth.RegisterViewModel
import com.example.travelmarket.ui.theme.RedMain
import com.example.travelmarket.ui.theme.WhitePure
import com.example.travelmarket.views.ui.auth.components.AuthTextField
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
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

    LaunchedEffect(registerState) {
        if (registerState is NetworkResult.Success) {
            onRegisterSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Crear Cuenta",
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Regístrate para comenzar tu aventura",
            color = Color.DarkGray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            AuthTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = "Nombre *",
                icon = Icons.Default.Person,
                keyboardType = KeyboardType.Text,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            AuthTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = "Apellido *",
                icon = Icons.Default.Person,
                keyboardType = KeyboardType.Text,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = username,
            onValueChange = { username = it },
            label = "Usuario *",
            icon = Icons.Default.Person,
            keyboardType = KeyboardType.Text,
            modifier = Modifier.fillMaxWidth(),
            isError = registerState is NetworkResult.Error &&
                    (registerState as NetworkResult.Error).message.contains("username", ignoreCase = true)
        )
        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = passportNumber,
            onValueChange = { passportNumber = it },
            label = "DNI / Pasaporte *",
            icon = Icons.Default.Badge,
            keyboardType = KeyboardType.Text,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email *",
            icon = Icons.Default.Email,
            keyboardType = KeyboardType.Email,
            modifier = Modifier.fillMaxWidth(),
            isError = registerState is NetworkResult.Error &&
                    (registerState as NetworkResult.Error).message.contains("email", ignoreCase = true)
        )
        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = nationality,
            onValueChange = { nationality = it },
            label = "Nacionalidad *",
            icon = Icons.Default.Public,
            keyboardType = KeyboardType.Text,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = country,
            onValueChange = { country = it },
            label = "País de Residencia",
            icon = Icons.Default.Place,
            keyboardType = KeyboardType.Text,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = city,
            onValueChange = { city = it },
            label = "Ciudad",
            icon = Icons.Default.LocationCity,
            keyboardType = KeyboardType.Text,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = phone,
            onValueChange = { phone = it },
            label = "Teléfono",
            icon = Icons.Default.Phone,
            keyboardType = KeyboardType.Phone,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = password,
            onValueChange = { password = it },
            label = "Contraseña *",
            icon = Icons.Default.Lock,
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = registerState is NetworkResult.Error &&
                    (registerState as NetworkResult.Error).message.contains("password", ignoreCase = true)
        )
        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = passwordConfirm,
            onValueChange = { passwordConfirm = it },
            label = "Confirmar Contraseña *",
            icon = Icons.Default.Lock,
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = password.isNotEmpty() && passwordConfirm.isNotEmpty() && password != passwordConfirm
        )

        if (password.isNotEmpty() && passwordConfirm.isNotEmpty() && password != passwordConfirm) {
            Text(
                text = "Las contraseñas no coinciden",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

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
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = RedMain,
                contentColor = WhitePure
            ),
            shape = RoundedCornerShape(50),
            enabled = username.isNotEmpty() &&
                    email.isNotEmpty() &&
                    password.isNotEmpty() &&
                    passwordConfirm.isNotEmpty() &&
                    password == passwordConfirm &&
                    firstName.isNotEmpty() &&
                    lastName.isNotEmpty() &&
                    nationality.isNotEmpty() &&
                    registerState !is NetworkResult.Loading
        ) {
            Text(text = "Registrarse", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (registerState) {
            is NetworkResult.Loading -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is NetworkResult.Error -> {
                val errorMessage = (registerState as NetworkResult.Error).message
                val friendlyMessage = when {
                    errorMessage.contains("username", ignoreCase = true) ->
                        "El nombre de usuario ya existe o no es válido"
                    errorMessage.contains("email", ignoreCase = true) ->
                        "El email ya existe o no es válido"
                    errorMessage.contains("password", ignoreCase = true) ->
                        "La contraseña no cumple los requisitos"
                    else -> "Error: $errorMessage"
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = friendlyMessage,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                S           }
            else -> {}
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}