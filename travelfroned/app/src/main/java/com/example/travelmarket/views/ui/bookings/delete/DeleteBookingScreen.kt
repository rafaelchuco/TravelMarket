package com.example.travelmarket.views.ui.bookings.delete

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.viewmodels.bookings.DeleteBookingViewModel
import com.example.travelmarket.views.navigation.Routes
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBookingScreen(
    bookingId: Int,
    navController: NavController,
    viewModel: DeleteBookingViewModel = koinViewModel()
) {
    val deleteBookingState by viewModel.deleteBookingState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var hasNavigated by remember { mutableStateOf(false) }

    LaunchedEffect(deleteBookingState) {
        if (!hasNavigated) {
            when (val state = deleteBookingState) {
                is NetworkResult.Success -> {
                    hasNavigated = true
                    snackbarHostState.showSnackbar("✅ Reserva eliminada exitosamente")
                    navController.navigate(Routes.BookingsList.route) {
                        popUpTo(Routes.BookingDetail.route) { inclusive = true }
                    }
                }
                is NetworkResult.Error -> {
                    val errorMessage = state.message
                    if (errorMessage.contains("204") ||
                        errorMessage.contains("No Content") ||
                        errorMessage.contains("Successfully") ||
                        errorMessage.isEmpty()) {
                        hasNavigated = true
                        snackbarHostState.showSnackbar("✅ Reserva eliminada exitosamente")
                        navController.navigate(Routes.BookingsList.route) {
                            popUpTo(Routes.BookingDetail.route) { inclusive = true }
                        }
                    } else {
                        snackbarHostState.showSnackbar("❌ $errorMessage")
                    }
                }
                else -> {}
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Eliminar Reserva") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        enabled = deleteBookingState !is NetworkResult.Loading
                    ) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    titleContentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "¿Estás seguro de que deseas eliminar esta reserva?",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Esta acción no se puede deshacer",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (deleteBookingState is NetworkResult.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp),
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Eliminando reserva...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.weight(1f),
                        enabled = deleteBookingState !is NetworkResult.Loading
                    ) {
                        Text("Cancelar")
                    }

                    Button(
                        onClick = { viewModel.deleteBooking(bookingId) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        ),
                        enabled = deleteBookingState !is NetworkResult.Loading
                    ) {
                        Text("Eliminar")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (deleteBookingState is NetworkResult.Error) {
                val errorMessage = (deleteBookingState as NetworkResult.Error).message
                if (!errorMessage.contains("204") &&
                    !errorMessage.contains("No Content") &&
                    !errorMessage.contains("Successfully") &&
                    errorMessage.isNotEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Text(
                            text = "Error: $errorMessage",
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}