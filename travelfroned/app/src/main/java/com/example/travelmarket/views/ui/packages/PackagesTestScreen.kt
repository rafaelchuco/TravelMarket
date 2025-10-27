package com.example.travelmarket.views.ui.test

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.viewmodels.bookings.CreateBookingViewModel
import com.example.travelmarket.logic.viewmodels.packages.PackagesListViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PackagesTestScreen(
    onBack: () -> Unit,
    navController: NavController
) {
    val vm: PackagesListViewModel = hiltViewModel()
    val bookingVm: CreateBookingViewModel = koinViewModel()

    val packages by vm.packages.collectAsState()
    val loading by vm.loading.collectAsState()
    val error by vm.error.collectAsState()
    val createBookingState by bookingVm.createBookingState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        vm.loadPackages()
    }

    LaunchedEffect(createBookingState) {
        when (createBookingState) {
            is NetworkResult.Success -> {
                snackbarHostState.showSnackbar("✅ Reserva creada")
                bookingVm.resetCreateState()
            }
            is NetworkResult.Error -> {
                snackbarHostState.showSnackbar("❌ Error al crear reserva")
                bookingVm.resetCreateState()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Packages API") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("GET /packages/", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(4.dp))
                    Text("Lista de paquetes turísticos", style = MaterialTheme.typography.bodySmall)
                }
            }

            Spacer(Modifier.height(16.dp))

            when {
                loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                error != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Error", style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(8.dp))
                            Text(error ?: "Error desconocido")
                            Spacer(Modifier.height(16.dp))
                            Button(onClick = { vm.loadPackages() }) {
                                Text("Reintentar")
                            }
                        }
                    }
                }
                packages.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No hay paquetes disponibles")
                    }
                }
                else -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(packages) { pkg ->
                            Card(modifier = Modifier.fillMaxWidth()) {
                                Column(Modifier.padding(16.dp)) {
                                    Text(pkg.title, style = MaterialTheme.typography.titleMedium)
                                    Spacer(Modifier.height(4.dp))
                                    Text(pkg.description, style = MaterialTheme.typography.bodyMedium)
                                    Spacer(Modifier.height(8.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column {
                                            Text("💵 $${pkg.price}", style = MaterialTheme.typography.titleSmall)
                                            Text("📅 ${pkg.durationDays} días", style = MaterialTheme.typography.bodySmall)
                                        }

                                        Button(
                                            onClick = {
                                                bookingVm.createBooking(
                                                    packageId = pkg.id.toInt(),
                                                    travelDate = "2025-12-25",
                                                    returnDate = "2025-12-30",
                                                    numAdults = 2,
                                                    numChildren = 0,
                                                    numInfants = 0,
                                                    specialRequests = "Test"
                                                )
                                            },
                                            enabled = createBookingState !is NetworkResult.Loading
                                        ) {
                                            Text("Reservar")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}