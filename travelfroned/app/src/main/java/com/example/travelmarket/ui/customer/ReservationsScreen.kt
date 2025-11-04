package com.example.travelmarket.ui.customer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.travelmarket.logic.viewmodels.bookings.MyBookingsViewModel
import com.example.travelmarket.logic.domain.models.Booking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationsScreen(
    navController: androidx.navigation.NavController? = null,
    viewModel: MyBookingsViewModel = hiltViewModel()
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf("Próximas", "Pasadas", "Canceladas")
    
    val bookingsState by viewModel.myBookingsState.collectAsState()
    
    val bookings = when (val state = bookingsState) {
        is com.example.travelmarket.core.network.NetworkResult.Success -> state.data
        is com.example.travelmarket.core.network.NetworkResult.Loading -> emptyList()
        else -> emptyList()
    }
    
    val isLoading = bookingsState is com.example.travelmarket.core.network.NetworkResult.Loading
    
    // Guardar el estado en una variable local para evitar smart cast issues
    val currentState = bookingsState
    val error = when (currentState) {
        is com.example.travelmarket.core.network.NetworkResult.Error -> currentState.message
        else -> null
    }
    
    // Calcular contadores dinámicos
    val upcomingCount = bookings.count { it.status == "confirmed" || it.status == "pending" }
    val pastCount = bookings.count { it.status == "completed" || it.status == "finished" }
    val cancelledCount = bookings.count { it.status == "cancelled" || it.status == "canceled" }
    
    LaunchedEffect(Unit) {
        viewModel.getMyBookings()
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE53E3E))
                .padding(16.dp)
        ) {
            Text(
                text = "Mis Reservas",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        
        // Tabs con contadores dinámicos según datos de la API
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.White,
            contentColor = Color(0xFFE53E3E),
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = Color(0xFFE53E3E)
                )
            }
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = {
                    Text(
                        text = "Próximas ($upcomingCount)",
                        fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal,
                        color = if (selectedTab == 0) Color(0xFFE53E3E) else Color.Gray
                    )
                }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = {
                    Text(
                        text = "Pasadas ($pastCount)",
                        fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal,
                        color = if (selectedTab == 1) Color(0xFFE53E3E) else Color.Gray
                    )
                }
            )
            Tab(
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 },
                text = {
                    Text(
                        text = "Canceladas ($cancelledCount)",
                        fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Normal,
                        color = if (selectedTab == 2) Color(0xFFE53E3E) else Color.Gray
                    )
                }
            )
        }
        
        // Mostrar estado de carga o error
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (error != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Error al cargar datos: $error",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                    Button(
                        onClick = { viewModel.getMyBookings() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE53E3E)
                        )
                    ) {
                        Text("Reintentar")
                    }
                }
            }
        } else {
            // Content based on selected tab
            when (selectedTab) {
                0 -> UpcomingReservations(bookings, navController)
                1 -> PastReservations(bookings, navController)
                2 -> CancelledReservations(bookings, navController)
            }
        }
    }
}

@Composable
fun UpcomingReservations(
    bookings: List<Booking>,
    navController: androidx.navigation.NavController? = null
) {
    val upcomingBookings = bookings.filter {
        it.status == "confirmed" || it.status == "pending"
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        if (upcomingBookings.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No hay reservas próximas",
                    color = Color.Gray
                )
            }
        } else {
            upcomingBookings.forEach { booking ->
                ReservationCardFigma(
                    title = booking.bookingNumber, // TODO: Obtener nombre del paquete
                    location = "Ubicación", // TODO: Obtener de booking
                    date = booking.travelDate ?: "Fecha no disponible",
                    passengers = "${booking.numAdults + booking.numChildren + booking.numInfants} pasajero(s)",
                    bookingNumber = "#${booking.bookingNumber}",
                    status = booking.status,
                    statusColor = Color(0xFF10B981),
                    price = booking.totalAmount,
                    onViewDetails = { 
                        navController?.navigate(
                            com.example.travelmarket.views.navigation.Routes.ReservationDetail.createRoute(booking.id.toLong())
                        )
                    },
                    onCancel = { /* TODO: Cancelar reserva */ }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun PastReservations(
    bookings: List<Booking>,
    navController: androidx.navigation.NavController? = null
) {
    val pastBookings = bookings.filter {
        it.status == "completed" || it.status == "finished"
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        if (pastBookings.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No hay reservas pasadas",
                    color = Color.Gray
                )
            }
        } else {
            pastBookings.forEach { booking ->
                ReservationCardFigma(
                    title = booking.bookingNumber, // TODO: Obtener nombre del paquete
                    location = "Ubicación", // TODO: Obtener de booking
                    date = booking.travelDate ?: "Fecha no disponible",
                    passengers = "${booking.numAdults + booking.numChildren + booking.numInfants} pasajero(s)",
                    bookingNumber = "#${booking.bookingNumber}",
                    status = "Completada",
                    statusColor = Color(0xFF6B7280),
                    price = booking.totalAmount,
                    onViewDetails = { 
                        navController?.navigate(
                            com.example.travelmarket.views.navigation.Routes.ReservationDetail.createRoute(booking.id.toLong())
                        )
                    },
                    onCancel = { /* TODO: Cancelar reserva */ }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun CancelledReservations(
    bookings: List<Booking>,
    navController: androidx.navigation.NavController? = null
) {
    val cancelledBookings = bookings.filter {
        it.status == "cancelled" || it.status == "canceled"
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        if (cancelledBookings.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No hay reservas canceladas",
                    color = Color.Gray
                )
            }
        } else {
            cancelledBookings.forEach { booking ->
                ReservationCardFigma(
                    title = booking.bookingNumber, // TODO: Obtener nombre del paquete
                    location = "Ubicación", // TODO: Obtener de booking
                    date = booking.travelDate ?: "Fecha no disponible",
                    passengers = "${booking.numAdults + booking.numChildren + booking.numInfants} pasajero(s)",
                    bookingNumber = "#${booking.bookingNumber}",
                    status = "Cancelada",
                    statusColor = Color(0xFFEF4444),
                    price = booking.totalAmount,
                    onViewDetails = { 
                        navController?.navigate(
                            com.example.travelmarket.views.navigation.Routes.ReservationDetail.createRoute(booking.id.toLong())
                        )
                    },
                    onCancel = { /* TODO: Cancelar reserva */ }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ReservationCardFigma(
    title: String,
    location: String,
    date: String,
    passengers: String,
    bookingNumber: String,
    status: String,
    statusColor: Color,
    price: String,
    onViewDetails: () -> Unit,
    onCancel: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen del paquete (placeholder)
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color(0xFFF3F4F6), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "IMG",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Información de la reserva
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Ubicación",
                        modifier = Modifier.size(12.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = location,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                
                Spacer(modifier = Modifier.height(2.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = "Fecha",
                        modifier = Modifier.size(12.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = date,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                
                Spacer(modifier = Modifier.height(2.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Pasajeros",
                        modifier = Modifier.size(12.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = passengers,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = bookingNumber,
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
            
            // Estado y precio
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = statusColor.copy(alpha = 0.1f)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = status,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = statusColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = price,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE53E3E)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReservationsScreenPreview() {
    ReservationsScreen()
}

@Preview(showBackground = true)
@Composable
fun ReservationCardPreview() {
    ReservationCardFigma(
        title = "Valle Sagrado + Ollantaytambo",
        location = "Cusco",
        date = "22 Dic 2024",
        passengers = "1 pasajero(s)",
        bookingNumber = "#PERU12345683",
        status = "Pendiente",
        statusColor = Color(0xFFF59E0B),
        price = "S/ 180.00",
        onViewDetails = { },
        onCancel = { }
    )
}
