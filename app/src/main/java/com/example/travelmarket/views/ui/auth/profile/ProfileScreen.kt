package com.example.travelmarket.views.ui.auth.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.viewmodels.auth.ProfileViewModel
import com.example.travelmarket.views.navigation.Routes
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = koinViewModel()
) {
    val profileState by viewModel.profileState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    // ✅ Recargar datos cada vez que la pantalla vuelva a estar activa
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.currentStateFlow.collectLatest { state ->
            if (state == Lifecycle.State.RESUMED) {
                viewModel.getUserProfile()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Perfil") }
            )
        }
    ) { paddingValues ->
        when (profileState) {
            is NetworkResult.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is NetworkResult.Success -> {
                val user = (profileState as NetworkResult.Success).data
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Información Personal",
                                style = MaterialTheme.typography.titleMedium
                            )
                            HorizontalDivider()
                            ProfileItem("Usuario", user.username)
                            ProfileItem("Email", user.email)
                            ProfileItem("Nombre", user.firstName)
                            ProfileItem("Apellidos", user.lastName)
                            user.phone?.let { ProfileItem("Teléfono", it) }
                        }
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Información Adicional",
                                style = MaterialTheme.typography.titleMedium
                            )
                            HorizontalDivider()
                            user.nationality?.let {
                                ProfileItem("Nacionalidad", it)  // ✅ Solo lectura
                            }
                            user.passportNumber?.let { ProfileItem("Pasaporte", it) }
                            user.address?.let { ProfileItem("Dirección", it) }
                            user.city?.let { ProfileItem("Ciudad", it) }
                            user.country?.let { ProfileItem("País", it) }
                        }
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Información de Cuenta",
                                style = MaterialTheme.typography.titleMedium
                            )
                            HorizontalDivider()
                            ProfileItem("Tipo de Usuario", user.userType)
                            ProfileItem("Activo", if (user.isActive) "Sí" else "No")
                            ProfileItem("Fecha de Registro", user.dateJoined)
                        }
                    }

                    Button(
                        onClick = {
                            navController.navigate(Routes.EditProfile.route)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Editar Perfil")
                    }
                }
            }
            is NetworkResult.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Error: ${(profileState as NetworkResult.Error).message}",
                            color = MaterialTheme.colorScheme.error
                        )
                        Button(onClick = { viewModel.getUserProfile() }) {
                            Text("Reintentar")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileItem(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}