package com.example.travelmarket.views.ui.bookings.delete

import androidx.compose.foundation.layout.*
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

    LaunchedEffect(deleteBookingState) {
        when (deleteBookingState) {
            is NetworkResult.Success -> {
                navController.navigate(Routes.BookingsList.route) {
                    popUpTo(Routes.DeleteBooking.route) { inclusive = true }
                }
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Eliminar Reserva") }
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
                text = "¿Estás seguro de que deseas eliminar la reserva #$bookingId?",
                style = MaterialTheme.typography.titleMedium
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
                    Text("Cancelar")
                }

                Button(
                    onClick = { viewModel.deleteBooking(bookingId) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Eliminar")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            when (deleteBookingState) {
                is NetworkResult.Loading -> {
                    CircularProgressIndicator()
                }
                is NetworkResult.Error -> {
                    Text(
                        text = "Error: ${(deleteBookingState as NetworkResult.Error).message}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
                else -> {}
            }
        }
    }
}