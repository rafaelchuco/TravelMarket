package com.example.travelmarket.views.ui.bookings.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.travelmarket.logic.viewmodels.bookings.BookingDetailViewModel
import com.example.travelmarket.views.navigation.Routes
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingDetailScreen(
    bookingId: Int,
    navController: NavController,
    viewModel: BookingDetailViewModel = koinViewModel()
) {
    val bookingDetailState by viewModel.bookingDetailState.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showCancelDialog by remember { mutableStateOf(false) }

    LaunchedEffect(bookingId) {
        viewModel.getBookingById(bookingId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Detalle de Reserva",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            "Volver",
                            tint = Color(0xFFDC143C)
                        )
                    }
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
            when (bookingDetailState) {
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
                                "Cargando detalles...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        }
                    }
                }

                is NetworkResult.Success -> {
                    val booking = (bookingDetailState as NetworkResult.Success).data

                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(16.dp)
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // HEADER ROJO
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFDC143C)
                                ),
                                elevation = CardDefaults.cardElevation(4.dp),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(20.dp),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Text(
                                        text = "Reserva #${booking.bookingNumber}",
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        Surface(
                                            shape = RoundedCornerShape(20.dp),
                                            color = Color.White
                                        ) {
                                            Text(
                                                text = getStatusText(booking.status),
                                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = getStatusColor(booking.status)
                                            )
                                        }
                                        Surface(
                                            shape = RoundedCornerShape(20.dp),
                                            color = Color.White
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
                                }
                            }

                            // FECHAS
                            DetailSection(title = "Fechas del Viaje", icon = Icons.Default.CalendarToday) {
                                DetailRow("Fecha de viaje", booking.travelDate ?: "N/A")
                                DetailRow("Fecha de retorno", booking.returnDate ?: "N/A")
                                DetailRow("Fecha de reserva", booking.bookingDate)
                                DetailRow("Última actualización", booking.updatedAt)
                            }

                            // PASAJEROS
                            DetailSection(title = "Pasajeros", icon = Icons.Default.People) {
                                DetailRow("Adultos", booking.numAdults.toString())
                                DetailRow("Niños", booking.numChildren.toString())
                                DetailRow("Infantes", booking.numInfants.toString())
                            }

                            // MONTOS
                            DetailSection(title = "Resumen de Pago", icon = Icons.Default.AttachMoney) {
                                DetailRow("Subtotal", "$${booking.subtotal}")
                                DetailRow("Descuento", "-$${booking.discountAmount}")
                                DetailRow("Impuestos", "$${booking.taxAmount}")

                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    color = Color(0xFFE0E0E0)
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Total",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF212121)
                                    )
                                    Text(
                                        text = "$${booking.totalAmount}",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFDC143C)
                                    )
                                }

                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    color = Color(0xFFE0E0E0)
                                )

                                DetailRow("Pagado", "$${booking.paidAmount}")
                            }

                            // SOLICITUDES ESPECIALES
                            booking.specialRequests?.let { requests ->
                                if (requests.isNotBlank()) {
                                    DetailSection(title = "Solicitudes Especiales", icon = Icons.Default.Notes) {
                                        Text(
                                            text = requests,
                                            fontSize = 14.sp,
                                            color = Color(0xFF212121),
                                            lineHeight = 20.sp
                                        )
                                    }
                                }
                            }

                            // INFORMACIÓN ADICIONAL
                            DetailSection(title = "Información Adicional", icon = Icons.Default.Info) {
                                DetailRow("ID Cliente", booking.customer.toString())
                                DetailRow("ID Paquete", booking.packageId?.toString() ?: "N/A")
                            }
                        }

                        // BOTONES DE ACCIÓN (FOOTER FIJO)
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shadowElevation = 8.dp,
                            color = Color.White
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                when (booking.status.lowercase()) {
                                    "cancelled", "canceled" -> {
                                        Card(
                                            modifier = Modifier.fillMaxWidth(),
                                            colors = CardDefaults.cardColors(
                                                containerColor = Color(0xFFFFEBEE)
                                            )
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(12.dp),
                                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(
                                                    Icons.Default.Cancel,
                                                    contentDescription = null,
                                                    tint = Color(0xFFDC143C)
                                                )
                                                Text(
                                                    text = "Esta reserva está cancelada",
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.Medium,
                                                    color = Color(0xFFDC143C)
                                                )
                                            }
                                        }

                                        Button(
                                            onClick = { showDeleteDialog = true },
                                            modifier = Modifier.fillMaxWidth(),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color(0xFFDC143C)
                                            ),
                                            shape = RoundedCornerShape(12.dp)
                                        ) {
                                            Icon(Icons.Default.Delete, contentDescription = null)
                                            Spacer(Modifier.width(8.dp))
                                            Text("Eliminar Reserva", fontSize = 16.sp)
                                        }
                                    }

                                    else -> {
                                        Button(
                                            onClick = {
                                                navController.navigate(
                                                    Routes.UpdateBooking.createRoute(bookingId)
                                                )
                                            },
                                            modifier = Modifier.fillMaxWidth(),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color(0xFFDC143C)
                                            ),
                                            shape = RoundedCornerShape(12.dp)
                                        ) {
                                            Icon(Icons.Default.Edit, contentDescription = null)
                                            Spacer(Modifier.width(8.dp))
                                            Text("Actualizar Reserva", fontSize = 16.sp)
                                        }

                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                                        ) {
                                            OutlinedButton(
                                                onClick = { showCancelDialog = true },
                                                modifier = Modifier.weight(1f),
                                                colors = ButtonDefaults.outlinedButtonColors(
                                                    contentColor = Color(0xFFFF9800)
                                                ),
                                                shape = RoundedCornerShape(12.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Close,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(20.dp)
                                                )
                                                Spacer(Modifier.width(4.dp))
                                                Text("Cancelar")
                                            }

                                            OutlinedButton(
                                                onClick = { showDeleteDialog = true },
                                                modifier = Modifier.weight(1f),
                                                colors = ButtonDefaults.outlinedButtonColors(
                                                    contentColor = Color(0xFFDC143C)
                                                ),
                                                shape = RoundedCornerShape(12.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Delete,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(20.dp)
                                                )
                                                Spacer(Modifier.width(4.dp))
                                                Text("Eliminar")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // DIALOG ELIMINAR
                    if (showDeleteDialog) {
                        AlertDialog(
                            onDismissRequest = { showDeleteDialog = false },
                            icon = {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = null,
                                    tint = Color(0xFFDC143C),
                                    modifier = Modifier.size(48.dp)
                                )
                            },
                            title = {
                                Text(
                                    "Eliminar Reserva",
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            text = {
                                Text("¿Estás seguro de que deseas eliminar esta reserva?\n\nEsta acción no se puede deshacer.")
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        showDeleteDialog = false
                                        navController.navigate(Routes.DeleteBooking.createRoute(bookingId))
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFDC143C)
                                    )
                                ) {
                                    Text("Eliminar")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDeleteDialog = false }) {
                                    Text("Cancelar", color = Color.Gray)
                                }
                            }
                        )
                    }

                    // DIALOG CANCELAR
                    if (showCancelDialog) {
                        AlertDialog(
                            onDismissRequest = { showCancelDialog = false },
                            icon = {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = null,
                                    tint = Color(0xFFFF9800),
                                    modifier = Modifier.size(48.dp)
                                )
                            },
                            title = {
                                Text(
                                    "Cancelar Reserva",
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            text = {
                                Text("¿Estás seguro de que deseas cancelar esta reserva?\n\nEl estado cambiará a 'Cancelada'.")
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        showCancelDialog = false
                                        navController.navigate(Routes.CancelBooking.createRoute(bookingId))
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFFF9800)
                                    )
                                ) {
                                    Text("Sí, cancelar")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showCancelDialog = false }) {
                                    Text("No, volver", color = Color.Gray)
                                }
                            }
                        )
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
                                    "Error al cargar la reserva",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    (bookingDetailState as NetworkResult.Error).message,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                                Button(
                                    onClick = { viewModel.getBookingById(bookingId) },
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

                null -> {}
            }
        }
    }
}

@Composable
private fun DetailSection(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = Color(0xFFDC143C),
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121)
                )
            }

            HorizontalDivider(color = Color(0xFFE0E0E0))

            content()
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF212121)
        )
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
