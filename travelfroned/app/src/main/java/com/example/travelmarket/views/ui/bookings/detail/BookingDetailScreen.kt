package com.example.travelmarket.views.ui.bookings.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.BookingDetail
import com.example.travelmarket.logic.viewmodels.bookings.BookingDetailViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingDetailScreen(
    bookingId: Int,
    viewModel: BookingDetailViewModel = koinViewModel()
) {
    val bookingDetailState by viewModel.bookingDetailState.collectAsState()

    LaunchedEffect(bookingId) {
        viewModel.getBookingById(bookingId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Reserva") }
            )
        }
    ) { paddingValues ->
        when (bookingDetailState) {
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
                val booking = (bookingDetailState as NetworkResult.Success).data
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "ID: ${booking.id}", style = MaterialTheme.typography.titleLarge)
                    Text(text = "Número de reserva: ${booking.bookingNumber}")

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(text = "FECHAS", style = MaterialTheme.typography.titleMedium)
                    Text(text = "Fecha viaje: ${booking.travelDate ?: "N/A"}")
                    Text(text = "Fecha retorno: ${booking.returnDate ?: "N/A"}")
                    Text(text = "Fecha reserva: ${booking.bookingDate}")
                    Text(text = "Última actualización: ${booking.updatedAt}")

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(text = "PASAJEROS", style = MaterialTheme.typography.titleMedium)
                    Text(text = "Adultos: ${booking.numAdults}")
                    Text(text = "Niños: ${booking.numChildren}")
                    Text(text = "Infantes: ${booking.numInfants}")

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(text = "MONTOS", style = MaterialTheme.typography.titleMedium)
                    Text(text = "Subtotal: ${booking.subtotal}")
                    Text(text = "Descuento: ${booking.discountAmount}")
                    Text(text = "Impuestos: ${booking.taxAmount}")
                    Text(text = "Total: ${booking.totalAmount}")
                    Text(text = "Pagado: ${booking.paidAmount}")

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(text = "ESTADO", style = MaterialTheme.typography.titleMedium)
                    Text(text = "Estado: ${booking.status}")
                    Text(text = "Estado de pago: ${booking.paymentStatus}")

                    booking.specialRequests?.let {
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        Text(text = "SOLICITUDES ESPECIALES", style = MaterialTheme.typography.titleMedium)
                        Text(text = it)
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(text = "OTROS", style = MaterialTheme.typography.titleMedium)
                    Text(text = "ID Cliente: ${booking.customer}")
                    Text(text = "ID Paquete: ${booking.packageId ?: "N/A"}")
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
                        text = "Error: ${(bookingDetailState as NetworkResult.Error).message}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}