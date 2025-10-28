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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationsScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf("Próximas", "Pasadas", "Canceladas")
    
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
        
        // Tabs con contadores según el Figma
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
                        text = "Próximas (2)",
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
                        text = "Pasadas (2)",
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
                        text = "Canceladas (1)",
                        fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Normal,
                        color = if (selectedTab == 2) Color(0xFFE53E3E) else Color.Gray
                    )
                }
            )
        }
        
        // Content based on selected tab
        when (selectedTab) {
            0 -> UpcomingReservations()
            1 -> PastReservations()
            2 -> CancelledReservations()
        }
    }
}

@Composable
fun UpcomingReservations() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ReservationCardFigma(
            title = "Tour Machu Picchu 3D/2N",
            location = "Cusco",
            date = "14 dic. 2025",
            passengers = "2 pasajero(s)",
            bookingNumber = "#PERU12345678",
            status = "Próxima",
            statusColor = Color(0xFF10B981),
            price = "S/. 1700",
            onViewDetails = { /* TODO: Ver detalles */ },
            onCancel = { /* TODO: Cancelar reserva */ }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        ReservationCardFigma(
            title = "Amazonía 4D/3N",
            location = "Iquitos",
            date = "9 feb. 2026",
            passengers = "3 pasajero(s)",
            bookingNumber = "#PERU12345679",
            status = "Próxima",
            statusColor = Color(0xFF10B981),
            price = "S/. 2160",
            onViewDetails = { /* TODO: Ver detalles */ },
            onCancel = { /* TODO: Cancelar reserva */ }
        )
    }
}

@Composable
fun PastReservations() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ReservationCardFigma(
            title = "Montaña de Colores",
            location = "Cusco",
            date = "10 Nov 2024",
            passengers = "2 pasajero(s)",
            bookingNumber = "#PERU12345680",
            status = "Completada",
            statusColor = Color(0xFF6B7280),
            price = "S/ 200.00",
            onViewDetails = { /* TODO: Ver detalles */ },
            onCancel = { /* TODO: Cancelar reserva */ }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        ReservationCardFigma(
            title = "Laguna Humantay",
            location = "Cusco",
            date = "05 Oct 2024",
            passengers = "1 pasajero(s)",
            bookingNumber = "#PERU12345681",
            status = "Completada",
            statusColor = Color(0xFF6B7280),
            price = "S/ 150.00",
            onViewDetails = { /* TODO: Ver detalles */ },
            onCancel = { /* TODO: Cancelar reserva */ }
        )
    }
}

@Composable
fun CancelledReservations() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ReservationCardFigma(
            title = "Salineras de Maras",
            location = "Cusco",
            date = "20 Nov 2024",
            passengers = "2 pasajero(s)",
            bookingNumber = "#PERU12345682",
            status = "Cancelada",
            statusColor = Color(0xFFEF4444),
            price = "S/ 100.00",
            onViewDetails = { /* TODO: Ver detalles */ },
            onCancel = { /* TODO: Cancelar reserva */ }
        )
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
