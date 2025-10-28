package com.example.travelmarket.views.ui.bookings.myBookings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Booking
import com.example.travelmarket.logic.viewmodels.bookings.MyBookingsViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBookingsScreen(
    viewModel: MyBookingsViewModel = koinViewModel()
) {
    val myBookingsState by viewModel.myBookingsState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getMyBookings()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Mis Reservas",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
        ) {
            when (myBookingsState) {
                is NetworkResult.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            CircularProgressIndicator(color = Color(0xFFDC143C))
                            Text(
                                "Cargando reservas...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        }
                    }
                }

                is NetworkResult.Success -> {
                    val bookings = (myBookingsState as NetworkResult.Success<List<Booking>>).data

                    if (bookings.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Icon(
                                    Icons.Default.EventBusy,
                                    contentDescription = null,
                                    tint = Color.Gray,
                                    modifier = Modifier.size(64.dp)
                                )
                                Text(
                                    text = "No tienes reservas",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Tus reservas aparecerán aquí",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(bookings) { booking ->
                                MyBookingCard(booking = booking)
                            }
                        }
                    }
                }

                is NetworkResult.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Icon(
                                    Icons.Default.ErrorOutline,
                                    contentDescription = null,
                                    tint = Color(0xFFDC143C),
                                    modifier = Modifier.size(64.dp)
                                )
                                Text(
                                    "Error al cargar reservas",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    (myBookingsState as NetworkResult.Error).message,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                                Button(
                                    onClick = { viewModel.getMyBookings() },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFDC143C)
                                    )
                                ) {
                                    Icon(Icons.Default.Refresh, contentDescription = null)
                                    Spacer(Modifier.width(8.dp))
                                    Text("Reintentar")
                                }
                            }
                        }
                    }
                }

                null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0xFFDC143C))
                    }
                }
            }
        }
    }
}

@Composable
fun MyBookingCard(booking: Booking) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Reserva #${booking.bookingNumber}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF212121)
                    )
                    Text(
                        text = getStatusText(booking.status),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = getStatusColor(booking.status)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = getPaymentStatusColor(booking.paymentStatus).copy(alpha = 0.15f)
                ) {
                    Text(
                        text = getPaymentStatusText(booking.paymentStatus),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = getPaymentStatusColor(booking.paymentStatus)
                    )
                }
            }

            HorizontalDivider(color = Color(0xFFE0E0E0))

            // Fechas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.FlightTakeoff,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = Color(0xFFDC143C)
                        )
                        Text(
                            text = "Viaje",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                    Text(
                        text = booking.travelDate ?: "N/A",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF212121)
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.FlightLand,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = Color(0xFFDC143C)
                        )
                        Text(
                            text = "Retorno",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                    Text(
                        text = booking.returnDate ?: "N/A",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF212121)
                    )
                }
            }

            // Pasajeros
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.People,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color(0xFF2196F3)
                    )
                    Text(
                        text = "${booking.numAdults} adultos, ${booking.numChildren} niños, ${booking.numInfants} infantes",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            HorizontalDivider(color = Color(0xFFE0E0E0))

            // Total
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "$${booking.totalAmount}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFDC143C)
                )
            }
        }
    }
}

@Composable
private fun getStatusColor(status: String): Color {
    return when (status.lowercase()) {
        "confirmed" -> Color(0xFF4CAF50)
        "pending" -> Color(0xFFFF9800)
        "cancelled", "canceled" -> Color(0xFFDC143C)
        "completed" -> Color(0xFF2196F3)
        else -> Color.Gray
    }
}

@Composable
private fun getStatusText(status: String): String {
    return when (status.lowercase()) {
        "confirmed" -> "Confirmada"
        "pending" -> "Pendiente"
        "cancelled", "canceled" -> "Cancelada"
        "completed" -> "Completada"
        else -> status
    }
}

@Composable
private fun getPaymentStatusColor(status: String): Color {
    return when (status.lowercase()) {
        "paid" -> Color(0xFF4CAF50)
        "unpaid" -> Color(0xFFDC143C)
        "partial" -> Color(0xFFFF9800)
        "refunded" -> Color(0xFF2196F3)
        else -> Color.Gray
    }
}

@Composable
private fun getPaymentStatusText(status: String): String {
    return when (status.lowercase()) {
        "paid" -> "Pagado"
        "unpaid" -> "Sin Pagar"
        "partial" -> "Pago Parcial"
        "refunded" -> "Reembolsado"
        else -> status
    }
}
