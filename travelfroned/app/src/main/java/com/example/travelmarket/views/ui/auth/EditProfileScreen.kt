package com.example.travelmarket.views.ui.auth

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.viewmodels.auth.ProfileViewModel
import com.example.travelmarket.views.ui.auth.components.AuthButton
import com.example.travelmarket.views.ui.auth.components.AuthHeader
import com.example.travelmarket.views.ui.auth.components.AuthTextField
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = koinViewModel()
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var passportNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }

    val profileState by viewModel.profileState.collectAsState()
    val updateProfileState by viewModel.updateProfileState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var showSuccessMessage by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.resetUpdateState()
        viewModel.getUserProfile()
    }

    LaunchedEffect(profileState) {
        when (val state = profileState) {
            is NetworkResult.Success -> {
                val user = state.data
                firstName = user.firstName
                lastName = user.lastName
                phone = user.phone ?: ""
                passportNumber = user.passportNumber ?: ""
                address = user.address ?: ""
                city = user.city ?: ""
                country = user.country ?: ""
            }
            else -> {}
        }
    }

    LaunchedEffect(updateProfileState) {
        when (updateProfileState) {
            is NetworkResult.Success -> {
                if (!showSuccessMessage) {
                    showSuccessMessage = true
                    snackbarHostState.showSnackbar(
                        message = "✅ Perfil actualizado exitosamente",
                        duration = SnackbarDuration.Short
                    )
                    delay(1000)
                    viewModel.resetUpdateState()
                    if (navController.currentBackStackEntry != null) {
                        navController.popBackStack()
                    }
                }
            }
            else -> {}
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color(0xFFF5F5F5)  // ✅ FONDO GRIS CLARO
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5))  // ✅ FONDO GRIS CLARO
                .verticalScroll(rememberScrollState())
        ) {
            // ✅ HEADER
            AuthHeader(
                title = "Editar Perfil",
                subtitle = "Actualiza tu información personal",
                showLogo = false
            )

            // ✅ FORMULARIO
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Información Personal",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFFDC143C),
                    fontWeight = FontWeight.Bold
                )

                AuthTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = "Nombre",
                    leadingIcon = Icons.Default.Badge
                )

                AuthTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = "Apellidos",
                    leadingIcon = Icons.Default.Badge
                )

                AuthTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = "Teléfono",
                    leadingIcon = Icons.Default.Phone
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Información de Viaje",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFFDC143C),
                    fontWeight = FontWeight.Bold
                )

                AuthTextField(
                    value = passportNumber,
                    onValueChange = { passportNumber = it },
                    label = "Número de Pasaporte",
                    leadingIcon = Icons.Default.CardTravel
                )

                AuthTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = "Dirección",
                    leadingIcon = Icons.Default.LocationOn
                )

                AuthTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = "Ciudad",
                    leadingIcon = Icons.Default.LocationCity
                )

                AuthTextField(
                    value = country,
                    onValueChange = { country = it },
                    label = "País",
                    leadingIcon = Icons.Default.Flag
                )

                // ✅ ERROR
                when (updateProfileState) {
                    is NetworkResult.Error -> {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFFFEBEE)  // Rojo claro
                            )
                        ) {
                            Text(
                                text = (updateProfileState as NetworkResult.Error).message,
                                color = Color(0xFFC62828),  // Rojo oscuro
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }
                    else -> {}
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ✅ BOTÓN GUARDAR
                AuthButton(
                    text = "Guardar Cambios",
                    onClick = {
                        showSuccessMessage = false
                        viewModel.updateProfile(
                            firstName = firstName.ifEmpty { null },
                            lastName = lastName.ifEmpty { null },
                            phone = phone.ifEmpty { null },
                            nationality = null,
                            passportNumber = passportNumber.ifEmpty { null },
                            address = address.ifEmpty { null },
                            city = city.ifEmpty { null },
                            country = country.ifEmpty { null }
                        )
                    },
                    enabled = updateProfileState !is NetworkResult.Loading
                )

                if (updateProfileState is NetworkResult.Loading) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            CircularProgressIndicator(color = Color(0xFFDC143C))
                            Text(
                                text = "Guardando cambios...",
                                color = Color(0xFF212121),  // ✅ NEGRO OSCURO
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
