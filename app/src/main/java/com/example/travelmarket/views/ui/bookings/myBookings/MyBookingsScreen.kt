package com.example.travelmarket.views.ui.bookings.myBookings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.BookingDetail
import com.example.travelmarket.logic.viewmodels.bookings.MyBookingsViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBookingsScreen(
    viewModel: MyBookingsViewModel = koinViewModel()
) {
    val myBookingsState by viewModel.myBookingsState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Reservas") }
            )
        }
    ) { paddingValues ->
        when (myBookingsState) {
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
                val bookings = (myBookingsState as NetworkResult.Success).data
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(bookings) { booking ->
                        MyBookingItem(booking = booking)
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
                    Text(
                        text = "Error: ${(myBookingsState as NetworkResult.Error).message}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
fun MyBookingItem(booking: BookingDetail) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = "ID: ${booking.id}")
        Text(text = "Número: ${booking.bookingNumber}")
        Text(text = "Fecha viaje: ${booking.travelDate ?: "N/A"}")
        Text(text = "Fecha retorno: ${booking.returnDate ?: "N/A"}")
        Text(text = "Adultos: ${booking.numAdults}, Niños: ${booking.numChildren}, Infantes: ${booking.numInfants}")
        Text(text = "Subtotal: ${booking.subtotal}")
        Text(text = "Descuento: ${booking.discountAmount}")
        Text(text = "Impuestos: ${booking.taxAmount}")
        Text(text = "Total: ${booking.totalAmount}")
        Text(text = "Pagado: ${booking.paidAmount}")
        Text(text = "Estado: ${booking.status}")
        Text(text = "Estado pago: ${booking.paymentStatus}")
        booking.specialRequests?.let { Text(text = "Solicitudes: $it") }
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
    }
}
