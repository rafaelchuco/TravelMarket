package com.example.travelmarket.ui.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationDetailScreen(
    reservationId: Long = 0L,
    navController: androidx.navigation.NavController? = null,
    viewModel: com.example.travelmarket.logic.viewmodels.bookings.BookingDetailViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    val bookingDetailState by viewModel.bookingDetailState.collectAsState()
    
    LaunchedEffect(reservationId) {
        if (reservationId > 0) {
            viewModel.getBookingById(reservationId.toInt())
        }
    }
    
    val reservation = when (val state = bookingDetailState) {
        is com.example.travelmarket.core.network.NetworkResult.Success -> {
            // Convertir BookingDetail a ReservationDetail usando solo propiedades disponibles
            state.data.let { booking ->
                ReservationDetail(
                    bookingNumber = booking.bookingNumber,
                    packageName = "Paquete #${booking.packageId ?: "N/A"}", // TODO: obtener nombre del paquete
                    destination = "", // TODO: obtener del paquete
                    travelDate = booking.travelDate ?: "",
                    numAdults = booking.numAdults,
                    numChildren = booking.numChildren,
                    status = booking.status,
                    totalAmount = "S/. ${booking.totalAmount}",
                    passengers = emptyList(), // TODO: mapear pasajeros desde API
                    itinerary = emptyList(), // TODO: mapear itinerario desde paquete
                    emergencyContacts = emptyList(),
                    hotelBooking = HotelBooking(
                        hotelName = "", // TODO: obtener del paquete
                        destination = "",
                        checkInDate = booking.travelDate ?: "",
                        checkOutDate = booking.returnDate ?: "",
                        numRooms = 1, // TODO: obtener del booking
                        roomType = "",
                        totalNights = 0,
                        pricePerNight = "0.00",
                        totalPrice = "0.00",
                        confirmationNumber = ""
                    ),
                    flightBooking = FlightBooking(
                        bookingType = "outbound",
                        airline = "", // TODO: obtener del paquete
                        flightNumber = "",
                        origin = "",
                        originAirport = "",
                        destination = "",
                        destinationAirport = "",
                        departureTime = "",
                        arrivalTime = "",
                        numPassengers = booking.numAdults + booking.numChildren,
                        seatNumbers = "",
                        pricePerPerson = "0.00",
                        totalPrice = "0.00",
                        pnrNumber = ""
                    )
                )
            }
        }
        is com.example.travelmarket.core.network.NetworkResult.Loading -> {
            getSampleReservation() // Mostrar placeholder mientras carga
        }
        is com.example.travelmarket.core.network.NetworkResult.Error -> {
            getSampleReservation() // Mostrar placeholder en caso de error
        }
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
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController?.popBackStack() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Atrás",
                        tint = Color.White
                    )
                }
                
                Text(
                    text = "Detalle de Reserva",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Información principal de la reserva
            ReservationInfoCard(reservation = reservation)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // QR Code y acciones
            QRActionsCard(
                bookingNumber = reservation.bookingNumber,
                onDownloadVoucher = { /* TODO: Descargar voucher */ },
                onShare = { /* TODO: Compartir */ }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Información de pasajeros
            PassengersInfoCard(passengers = reservation.passengers)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Itinerario
            ItineraryCard(itinerary = reservation.itinerary)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Información de contacto de emergencia
            EmergencyContactCard(emergencyContacts = reservation.emergencyContacts)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Información de hotel y vuelo
            HotelFlightInfoCard(
                hotelBooking = reservation.hotelBooking,
                flightBooking = reservation.flightBooking
            )
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ReservationInfoCard(reservation: ReservationDetail) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header con título y estado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = reservation.packageName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
                
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = when (reservation.status) {
                            "Confirmada" -> Color(0xFF10B981).copy(alpha = 0.1f)
                            "Pendiente" -> Color(0xFFF59E0B).copy(alpha = 0.1f)
                            "Cancelada" -> Color(0xFFEF4444).copy(alpha = 0.1f)
                            else -> Color(0xFF6B7280).copy(alpha = 0.1f)
                        }
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = reservation.status,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = when (reservation.status) {
                            "Confirmada" -> Color(0xFF10B981)
                            "Pendiente" -> Color(0xFFF59E0B)
                            "Cancelada" -> Color(0xFFEF4444)
                            else -> Color(0xFF6B7280)
                        },
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Información básica
            InfoRowWithIcon(
                label = "Destino",
                value = reservation.destination,
                icon = Icons.Default.LocationOn
            )
            
            InfoRowWithIcon(
                label = "Fecha de viaje",
                value = reservation.travelDate,
                icon = Icons.Default.CalendarToday
            )
            
            InfoRowWithIcon(
                label = "Pasajeros",
                value = "${reservation.numAdults} adultos${if (reservation.numChildren > 0) ", ${reservation.numChildren} niños" else ""}",
                icon = Icons.Default.Person
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Divider()
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Información de pago
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Número de reserva",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = reservation.bookingNumber,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total pagado",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = reservation.totalAmount,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE53E3E)
                )
            }
        }
    }
}

@Composable
fun QRActionsCard(
    bookingNumber: String,
    onDownloadVoucher: () -> Unit,
    onShare: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Código QR de Reserva",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // QR Code placeholder
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color(0xFFF3F4F6), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.QrCode,
                    contentDescription = "QR Code",
                    modifier = Modifier.size(60.dp),
                    tint = Color.Gray
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Muestra este código en el punto de encuentro",
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Botones de acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onDownloadVoucher,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFFE53E3E)
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFE53E3E))
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = "Descargar",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Voucher")
                }
                
                OutlinedButton(
                    onClick = onShare,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFFE53E3E)
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFE53E3E))
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Compartir",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Compartir")
                }
            }
        }
    }
}

@Composable
fun PassengersInfoCard(passengers: List<Passenger>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Información de Pasajeros",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            passengers.forEach { passenger ->
                PassengerItem(passenger = passenger)
                if (passenger != passengers.last()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun PassengerItem(passenger: Passenger) {
    Column {
        Text(
            text = "${passenger.firstName} ${passenger.lastName}",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "DNI: ${passenger.dni}",
                fontSize = 12.sp,
                color = Color.Gray
            )
            Text(
                text = passenger.nationality,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
        
        Spacer(modifier = Modifier.height(2.dp))
        
        Text(
            text = "Fecha de nacimiento: ${passenger.dateOfBirth}",
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun ItineraryCard(itinerary: List<ItineraryDay>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Itinerario",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            itinerary.forEach { day ->
                ItineraryDayItem(day = day)
                if (day != itinerary.last()) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun ItineraryDayItem(day: ItineraryDay) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Día número
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(Color(0xFFE53E3E), RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = day.dayNumber.toString(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = day.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = day.description,
                fontSize = 12.sp,
                color = Color.Gray,
                lineHeight = 16.sp
            )
            
            if (day.mealsIncluded.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Incluye: ${day.mealsIncluded}",
                    fontSize = 11.sp,
                    color = Color(0xFF10B981),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun EmergencyContactCard(emergencyContacts: List<EmergencyContact>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Contactos de Emergencia",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            emergencyContacts.forEach { contact ->
                EmergencyContactItem(contact = contact)
                if (contact != emergencyContacts.last()) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun EmergencyContactItem(contact: EmergencyContact) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Phone,
            contentDescription = "Teléfono",
            modifier = Modifier.size(20.dp),
            tint = Color(0xFFE53E3E)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column {
            Text(
                text = contact.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = contact.phone,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun HotelFlightInfoCard(
    hotelBooking: HotelBooking?,
    flightBooking: FlightBooking?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Información Adicional",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            if (hotelBooking != null) {
                HotelBookingItem(hotelBooking = hotelBooking)
                if (flightBooking != null) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
            
            if (flightBooking != null) {
                FlightBookingItem(flightBooking = flightBooking)
            }
        }
    }
}

@Composable
fun HotelBookingItem(hotelBooking: HotelBooking) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Hotel Reservado",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            
            if (hotelBooking.confirmationNumber.isNotEmpty()) {
                Text(
                    text = "Conf: ${hotelBooking.confirmationNumber}",
                    fontSize = 10.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // RF-090: Mostrar hotel nested con departamento
        Text(
            text = hotelBooking.hotelName,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        
        if (hotelBooking.destination.isNotEmpty()) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = hotelBooking.destination,
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Check-in",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
                Text(
                    text = hotelBooking.checkInDate,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }
            
            Column {
                Text(
                    text = "Check-out",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
                Text(
                    text = hotelBooking.checkOutDate,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // RF-090: Mostrar detalles de habitaciones
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${hotelBooking.numRooms} habitación(es)",
                fontSize = 11.sp,
                color = Color.Gray
            )
            Text(
                text = "${hotelBooking.totalNights} noche(s)",
                fontSize = 11.sp,
                color = Color.Gray
            )
        }
        
        if (hotelBooking.roomType.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Tipo: ${hotelBooking.roomType}",
                fontSize = 11.sp,
                color = Color.Gray
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Divider()
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = "S/ ${hotelBooking.totalPrice}",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE53E3E)
            )
        }
    }
}

@Composable
fun FlightBookingItem(flightBooking: FlightBooking) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Vuelo ${if (flightBooking.bookingType == "outbound") "Ida" else "Vuelta"}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            
            if (flightBooking.pnrNumber.isNotEmpty()) {
                Text(
                    text = "PNR: ${flightBooking.pnrNumber}",
                    fontSize = 10.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // RF-091: Mostrar vuelo nested con ruta nacional
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = flightBooking.origin,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = flightBooking.originAirport,
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
            
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            
            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                Text(
                    text = flightBooking.destination,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.End
                )
                Text(
                    text = flightBooking.destinationAirport,
                    fontSize = 11.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.End
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Divider()
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Aerolínea",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
                Text(
                    text = "${flightBooking.airline} ${flightBooking.flightNumber}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "Salida",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
                Text(
                    text = flightBooking.departureTime,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }
        }
        
        if (flightBooking.arrivalTime.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Llegada: ${flightBooking.arrivalTime}",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${flightBooking.numPassengers} pasajero(s)",
                fontSize = 11.sp,
                color = Color.Gray
            )
            
            if (flightBooking.seatNumbers.isNotEmpty()) {
                Text(
                    text = "Asientos: ${flightBooking.seatNumbers}",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Divider()
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = "S/ ${flightBooking.totalPrice}",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE53E3E)
            )
        }
    }
}


// Data classes
data class ReservationDetail(
    val bookingNumber: String,
    val packageName: String,
    val destination: String,
    val travelDate: String,
    val numAdults: Int,
    val numChildren: Int,
    val status: String,
    val totalAmount: String,
    val passengers: List<Passenger>,
    val itinerary: List<ItineraryDay>,
    val emergencyContacts: List<EmergencyContact>,
    val hotelBooking: HotelBooking?,
    val flightBooking: FlightBooking?
)

data class Passenger(
    val firstName: String,
    val lastName: String,
    val dni: String,
    val nationality: String,
    val dateOfBirth: String
)

data class ItineraryDay(
    val dayNumber: Int,
    val title: String,
    val description: String,
    val mealsIncluded: String
)

data class EmergencyContact(
    val name: String,
    val phone: String
)

// RF-090: Datos completos de hotel booking
data class HotelBooking(
    val hotelName: String,
    val destination: String, // departamento
    val checkInDate: String,
    val checkOutDate: String,
    val numRooms: Int,
    val roomType: String,
    val totalNights: Int,
    val pricePerNight: String,
    val totalPrice: String,
    val confirmationNumber: String
)

// RF-091: Datos completos de flight booking
data class FlightBooking(
    val bookingType: String, // "outbound" o "return"
    val airline: String,
    val flightNumber: String,
    val origin: String,
    val originAirport: String,
    val destination: String,
    val destinationAirport: String,
    val departureTime: String,
    val arrivalTime: String,
    val numPassengers: Int,
    val seatNumbers: String,
    val pricePerPerson: String,
    val totalPrice: String,
    val pnrNumber: String
)

fun getSampleReservation(): ReservationDetail {
    return ReservationDetail(
        bookingNumber = "#PERU12345678",
        packageName = "Tour Machu Picchu 3D/2N",
        destination = "Cusco",
        travelDate = "14 dic. 2025",
        numAdults = 2,
        numChildren = 0,
        status = "Confirmada",
        totalAmount = "S/. 1700",
        passengers = listOf(
            Passenger(
                firstName = "María",
                lastName = "García",
                dni = "12345678",
                nationality = "Peruana",
                dateOfBirth = "15/03/1990"
            ),
            Passenger(
                firstName = "Carlos",
                lastName = "García",
                dni = "87654321",
                nationality = "Peruana",
                dateOfBirth = "22/07/1988"
            )
        ),
        itinerary = listOf(
            ItineraryDay(
                dayNumber = 1,
                title = "Llegada a Cusco",
                description = "Recepción en el aeropuerto y traslado al hotel. Tiempo libre para aclimatación.",
                mealsIncluded = "Desayuno"
            ),
            ItineraryDay(
                dayNumber = 2,
                title = "Machu Picchu Full Day",
                description = "Salida temprano hacia Machu Picchu. Visita guiada de la ciudadela inca.",
                mealsIncluded = "Desayuno, Almuerzo"
            ),
            ItineraryDay(
                dayNumber = 3,
                title = "City Tour Cusco",
                description = "Recorrido por los principales atractivos de la ciudad imperial.",
                mealsIncluded = "Desayuno"
            )
        ),
        emergencyContacts = listOf(
            EmergencyContact(
                name = "Oficina Principal",
                phone = "+51 84 123456"
            ),
            EmergencyContact(
                name = "Emergencias 24h",
                phone = "+51 84 987654"
            )
        ),
        hotelBooking = HotelBooking(
            hotelName = "Hotel San Blas",
            destination = "Cusco",
            checkInDate = "14 dic. 2025",
            checkOutDate = "17 dic. 2025",
            numRooms = 1,
            roomType = "Doble",
            totalNights = 3,
            pricePerNight = "150.00",
            totalPrice = "450.00",
            confirmationNumber = "HOTEL-123456"
        ),
        flightBooking = FlightBooking(
            bookingType = "outbound",
            airline = "LATAM Perú",
            flightNumber = "LA 2025",
            origin = "Lima",
            originAirport = "Aeropuerto Internacional Jorge Chávez",
            destination = "Cusco",
            destinationAirport = "Aeropuerto Internacional Alejandro Velasco Astete",
            departureTime = "08:30",
            arrivalTime = "10:00",
            numPassengers = 2,
            seatNumbers = "12A, 12B",
            pricePerPerson = "350.00",
            totalPrice = "700.00",
            pnrNumber = "ABC123"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun ReservationDetailScreenPreview() {
    ReservationDetailScreen()
}
