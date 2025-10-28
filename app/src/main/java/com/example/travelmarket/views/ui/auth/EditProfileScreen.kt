package com.example.travelmarket.views.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.viewmodels.auth.ProfileViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
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

    // ✅ SnackbarHost para mostrar mensajes
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

    // ✅ CAMBIO: Mejor manejo con mensaje de éxito y delay
    LaunchedEffect(updateProfileState) {
        when (updateProfileState) {
            is NetworkResult.Success -> {
                if (!showSuccessMessage) {
                    showSuccessMessage = true
                    // Mostrar mensaje de éxito
                    snackbarHostState.showSnackbar(
                        message = "Perfil actualizado exitosamente",
                        duration = SnackbarDuration.Short
                    )
                    // Esperar un momento antes de navegar
                    delay(1000)
                    // Resetear estado
                    viewModel.resetUpdateState()
                    // Volver atrás
                    if (navController.currentBackStackEntry != null) {
                        navController.popBackStack()
                    }
                }
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Perfil") }
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
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Apellidos") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = passportNumber,
                onValueChange = { passportNumber = it },
                label = { Text("Número de Pasaporte") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Dirección") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("Ciudad") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = country,
                onValueChange = { country = it },
                label = { Text("País") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    showSuccessMessage = false
                    viewModel.updateProfile(
                        firstName = firstName.ifEmpty { null },
                        lastName = lastName.ifEmpty { null },
                        phone = phone.ifEmpty { null },
                        nationality =  null,
                        passportNumber = passportNumber.ifEmpty { null },
                        address = address.ifEmpty { null },
                        city = city.ifEmpty { null },
                        country = country.ifEmpty { null }
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = updateProfileState !is NetworkResult.Loading
            ) {
                Text("Guardar Cambios")
            }

            when (updateProfileState) {
                is NetworkResult.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Guardando cambios...")
                    }
                }
                is NetworkResult.Error -> {
                    Text(
                        text = "Error: ${(updateProfileState as NetworkResult.Error).message}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
                null -> {}
                else -> {}
            }
        }
    }
}