package com.example.travelmarket.views.ui.bookings.cancel

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.viewmodels.bookings.CancelBookingViewModel
import com.example.travelmarket.views.navigation.Routes
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CancelBookingScreen(
    bookingId: Int,
    navController: NavController,
    viewModel: CancelBookingViewModel = koinViewModel()
) {
    val cancelBookingState by viewModel.cancelBookingState.collectAsState()

    LaunchedEffect(cancelBookingState) {
        when (cancelBookingState) {
            is NetworkResult.Success -> {
                navController.navigate(Routes.BookingDetail.createRoute(bookingId)) {
                    popUpTo(Routes.CancelBooking.route) { inclusive = true }
                }
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cancelar Reserva") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "¿Estás seguro de que deseas cancelar la reserva #$bookingId?",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Esta acción cambiará el estado de la reserva a 'Cancelada'",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Volver")
                }

                Button(
                    onClick = { viewModel.cancelBooking(bookingId) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Cancelar Reserva")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            when (cancelBookingState) {
                is NetworkResult.Loading -> {
                    CircularProgressIndicator()
                }
                is NetworkResult.Error -> {
                    Text(
                        text = "Error: ${(cancelBookingState as NetworkResult.Error).message}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
                else -> {}
            }
        }
    }
}