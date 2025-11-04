package com.example.travelmarket.ui.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.text.input.KeyboardType
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelmarket.ui.components.InfoRow
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingFlowScreen(
    packageId: Long? = null, // ID del paquete seleccionado
    navController: androidx.navigation.NavController? = null
) {
    var currentStep by remember { mutableStateOf(0) }
    var bookingData by remember { mutableStateOf(BookingData()) }
    
    // Conectar con CreateBookingViewModel usando Koin
    val viewModel: com.example.travelmarket.logic.viewmodels.bookings.CreateBookingViewModel = org.koin.androidx.compose.koinViewModel()
    val createBookingState by viewModel.createBookingState.collectAsState()
    
    // Observar estado de creación
    LaunchedEffect(createBookingState) {
        when (val state = createBookingState) {
            is com.example.travelmarket.core.network.NetworkResult.Success -> {
                // Navegar a detalle de reserva
                state.data?.let { booking ->
                    navController?.navigate(
                        com.example.travelmarket.views.navigation.Routes.ReservationDetail.createRoute(booking.id.toLong())
                    ) {
                        popUpTo(com.example.travelmarket.views.navigation.Routes.BookingFlow.route) { inclusive = true }
                    }
                }
            }
            is com.example.travelmarket.core.network.NetworkResult.Error -> {
                // Mostrar error (TODO: usar Snackbar)
            }
            else -> {}
        }
    }
    
    val steps = listOf("Información", "Pasajeros", "Confirmación")
    
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE53E3E))
                    .padding(16.dp)
            ) {
                Column {
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
                            text = "Crear Reserva",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Indicador de progreso
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        steps.forEachIndexed { index, step ->
                            StepIndicator(
                                stepNumber = index + 1,
                                stepTitle = step,
                                isActive = index == currentStep,
                                isCompleted = index < currentStep
                            )
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        when (currentStep) {
            0 -> BookingStep1(
                data = bookingData,
                onDataChange = { bookingData = it },
                onNext = { currentStep = 1 },
                modifier = Modifier.padding(paddingValues)
            )
            1 -> BookingStep2(
                data = bookingData,
                onDataChange = { bookingData = it },
                onNext = { currentStep = 2 },
                onPrevious = { currentStep = 0 },
                modifier = Modifier.padding(paddingValues)
            )
            2 -> BookingStep3(
                data = bookingData,
                onDataChange = { bookingData = it },
                onConfirm = { 
                    // Calcular total desde BookingStep3
                    val priceAdult = 250.0 // TODO: obtener del paquete seleccionado
                    val priceChild = 200.0
                    val subtotal = (bookingData.adultCount * priceAdult) + (bookingData.childCount * priceChild)
                    val discount = bookingData.discountAmount
                    val igv = (subtotal - discount) * 0.18
                    val total = subtotal - discount + igv
                    
                    // Conectar con CreateBookingViewModel
                    viewModel.createBooking(
                        packageId = (packageId ?: 0).toInt(),
                        travelDate = bookingData.travelDate,
                        returnDate = bookingData.returnDate.takeIf { it.isNotEmpty() },
                        numAdults = bookingData.adultCount,
                        numChildren = bookingData.childCount,
                        numInfants = bookingData.infantCount,
                        totalAmount = total,
                        specialRequests = bookingData.specialRequests.takeIf { it.isNotEmpty() }
                    )
                },
                onPrevious = { currentStep = 1 },
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@Composable
fun StepIndicator(
    stepNumber: Int,
    stepTitle: String,
    isActive: Boolean,
    isCompleted: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    color = when {
                        isCompleted -> Color.White
                        isActive -> Color.White
                        else -> Color.White.copy(alpha = 0.3f)
                    },
                    shape = androidx.compose.foundation.shape.CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isCompleted) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Completado",
                    tint = Color(0xFFE53E3E),
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Text(
                    text = stepNumber.toString(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isActive) Color(0xFFE53E3E) else Color.Gray
                )
            }
        }
        
        Text(
            text = stepTitle,
            fontSize = 12.sp,
            color = if (isActive) Color.White else Color.White.copy(alpha = 0.7f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingStep1(
    data: BookingData,
    onDataChange: (BookingData) -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = if (data.travelDate.isNotEmpty()) {
            try {
                val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                format.parse(data.travelDate)?.time
            } catch (e: Exception) {
                null
            }
        } else null
    )
    
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Información del Paquete",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Información del paquete seleccionado
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
                    text = "Machu Picchu Full Day",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                
                Text(
                    text = "Cusco, Perú",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Fecha de viaje",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = data.travelDate.ifEmpty { "Seleccionar fecha" },
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Precio por persona",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "S/ 250.00",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE53E3E)
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Campo de fecha mejorado
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = "Fecha",
                        tint = Color(0xFFE53E3E),
                        modifier = Modifier.size(24.dp)
                    )
                    Column {
                        Text(
                            text = "Fecha de viaje",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = data.travelDate.ifEmpty { "Seleccionar fecha" },
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (data.travelDate.isEmpty()) Color.Gray else Color.Black
                        )
                    }
                }
                TextButton(
                    onClick = { showDatePicker = true },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color(0xFFE53E3E)
                    )
                ) {
                    Text("Seleccionar", fontWeight = FontWeight.Bold)
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // ✅ SELECTOR DE NÚMERO DE PASAJEROS (Movido aquí desde Paso 2)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Número de pasajeros",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Adultos",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { 
                                if (data.adultCount > 1) {
                                    onDataChange(data.copy(adultCount = data.adultCount - 1))
                                }
                            }
                        ) {
                            Text("-", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                        
                        Text(
                            text = data.adultCount.toString(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        
                        IconButton(
                            onClick = { 
                                if (data.adultCount < 6) {
                                    onDataChange(data.copy(adultCount = data.adultCount + 1))
                                }
                            }
                        ) {
                            Text("+", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Niños (2-11 años)",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { 
                                if (data.childCount > 0) {
                                    onDataChange(data.copy(childCount = data.childCount - 1))
                                }
                            }
                        ) {
                            Text("-", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                        
                        Text(
                            text = data.childCount.toString(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        
                        IconButton(
                            onClick = { 
                                if (data.childCount < 4) {
                                    onDataChange(data.copy(childCount = data.childCount + 1))
                                }
                            }
                        ) {
                            Text("+", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Bebés (0-2 años)",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { 
                                if (data.infantCount > 0) {
                                    onDataChange(data.copy(infantCount = data.infantCount - 1))
                                }
                            }
                        ) {
                            Text("-", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                        
                        Text(
                            text = data.infantCount.toString(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        
                        IconButton(
                            onClick = { 
                                if (data.infantCount < 2) {
                                    onDataChange(data.copy(infantCount = data.infantCount + 1))
                                }
                            }
                        ) {
                            Text("+", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE53E3E)
            ),
            shape = RoundedCornerShape(12.dp),
            enabled = data.adultCount > 0 && data.travelDate.isNotEmpty(),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 4.dp,
                pressedElevation = 6.dp
            )
        ) {
            Text(
                text = "Continuar",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Siguiente",
                modifier = Modifier.size(20.dp)
            )
        }
    }
    
    // DatePicker Dialog
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                            val selectedDate = dateFormat.format(Date(millis))
                            onDataChange(data.copy(travelDate = selectedDate))
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("Confirmar", color = Color(0xFFE53E3E), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar", color = Color.Gray)
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
fun BookingStep2(
    data: BookingData,
    onDataChange: (BookingData) -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Información de Pasajeros",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Selector de número de pasajeros
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
                    text = "Número de pasajeros",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Adultos",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { 
                                if (data.adultCount > 1) {
                                    onDataChange(data.copy(adultCount = data.adultCount - 1))
                                }
                            }
                        ) {
                            Text("-", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                        
                        Text(
                            text = data.adultCount.toString(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        
                        IconButton(
                            onClick = { 
                                if (data.adultCount < 6) {
                                    onDataChange(data.copy(adultCount = data.adultCount + 1))
                                }
                            }
                        ) {
                            Text("+", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Niños (2-11 años)",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { 
                                if (data.childCount > 0) {
                                    onDataChange(data.copy(childCount = data.childCount - 1))
                                }
                            }
                        ) {
                            Text("-", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                        
                        Text(
                            text = data.childCount.toString(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        
                        IconButton(
                            onClick = { 
                                if (data.childCount < 4) {
                                    onDataChange(data.copy(childCount = data.childCount + 1))
                                }
                            }
                        ) {
                            Text("+", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Bebés (0-1 años)",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { 
                                if (data.infantCount > 0) {
                                    onDataChange(data.copy(infantCount = data.infantCount - 1))
                                }
                            }
                        ) {
                            Text("-", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                        
                        Text(
                            text = data.infantCount.toString(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        
                        IconButton(
                            onClick = { 
                                if (data.infantCount < 2) {
                                    onDataChange(data.copy(infantCount = data.infantCount + 1))
                                }
                            }
                        ) {
                            Text("+", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Información de pasajeros individuales
        if (data.adultCount + data.childCount + data.infantCount > 0) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Pasajeros",
                    tint = Color(0xFFE53E3E),
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Datos de los pasajeros",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Mostrar formularios para cada pasajero
            repeat(data.adultCount) { index ->
                PassengerForm(
                    title = "Adulto ${index + 1}",
                    passenger = data.passengers.getOrNull(index) ?: PassengerData(),
                    onPassengerChange = { passenger ->
                        val updatedPassengers = data.passengers.toMutableList()
                        if (index < updatedPassengers.size) {
                            updatedPassengers[index] = passenger
                        } else {
                            updatedPassengers.add(passenger)
                        }
                        onDataChange(data.copy(passengers = updatedPassengers))
                    }
                )
                
                if (index < data.adultCount - 1) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            
            repeat(data.childCount) { index ->
                Spacer(modifier = Modifier.height(16.dp))
                PassengerForm(
                    title = "Niño ${index + 1}",
                    passenger = data.passengers.getOrNull(data.adultCount + index) ?: PassengerData(),
                    onPassengerChange = { passenger ->
                        val updatedPassengers = data.passengers.toMutableList()
                        val childIndex = data.adultCount + index
                        if (childIndex < updatedPassengers.size) {
                            updatedPassengers[childIndex] = passenger
                        } else {
                            updatedPassengers.add(passenger)
                        }
                        onDataChange(data.copy(passengers = updatedPassengers))
                    }
                )
            }
            
            repeat(data.infantCount) { index ->
                Spacer(modifier = Modifier.height(16.dp))
                PassengerForm(
                    title = "Bebé ${index + 1}",
                    passenger = data.passengers.getOrNull(data.adultCount + data.childCount + index) ?: PassengerData(),
                    onPassengerChange = { passenger ->
                        val updatedPassengers = data.passengers.toMutableList()
                        val infantIndex = data.adultCount + data.childCount + index
                        if (infantIndex < updatedPassengers.size) {
                            updatedPassengers[infantIndex] = passenger
                        } else {
                            updatedPassengers.add(passenger)
                        }
                        onDataChange(data.copy(passengers = updatedPassengers))
                    }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Datos de contacto (según Figma)
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
                    text = "Datos de contacto",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                OutlinedTextField(
                    value = data.contactEmail,
                    onValueChange = { onDataChange(data.copy(contactEmail = it)) },
                    label = { Text("Email") },
                    placeholder = { Text("tu@email.com") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFE53E3E),
                        unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email",
                            tint = Color(0xFFE53E3E),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = data.contactPhone,
                    onValueChange = { 
                        // Solo números y caracteres permitidos en teléfono
                        val filtered = it.filter { it.isDigit() || it == '+' || it == ' ' || it == '-' || it == '(' || it == ')' }
                        onDataChange(data.copy(contactPhone = filtered))
                    },
                    label = { Text("Teléfono") },
                    placeholder = { Text("+51 987654321") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFE53E3E),
                        unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = "Teléfono",
                            tint = Color(0xFFE53E3E),
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    singleLine = true
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = data.emergencyContact,
                    onValueChange = { onDataChange(data.copy(emergencyContact = it)) },
                    label = { Text("Contacto de emergencia") },
                    placeholder = { Text("Nombre y teléfono") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFE53E3E),
                        unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                    ),
                    minLines = 2,
                    maxLines = 3
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Resumen de precios
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Resumen de precios",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${data.adultCount} Adultos × S/ 250.00",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "S/ ${data.adultCount * 250}.00",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }
                
                if (data.childCount > 0) {
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${data.childCount} Niños × S/ 200.00",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "S/ ${data.childCount * 200}.00",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }
                }
                
                if (data.infantCount > 0) {
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${data.infantCount} Bebés × S/ 0.00",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "S/ 0.00",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Divider()
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Subtotal",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "S/ ${data.adultCount * 250 + data.childCount * 200}.00",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE53E3E)
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Botones de navegación profesionales
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Botón Atrás
            OutlinedButton(
                onClick = onPrevious,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFE53E3E)
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 1.5.dp
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Atrás",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Atrás", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            
            // Botón Continuar - más prominente
            Button(
                onClick = onNext,
                modifier = Modifier.weight(2f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE53E3E),
                    disabledContainerColor = Color.Gray.copy(alpha = 0.3f)
                ),
                shape = RoundedCornerShape(12.dp),
                enabled = data.passengers.size >= data.adultCount + data.childCount + data.infantCount &&
                         data.passengers.all { it.isComplete() } &&
                         data.contactEmail.isNotEmpty() &&
                         data.contactPhone.isNotEmpty(),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 6.dp,
                    disabledElevation = 0.dp
                )
            ) {
                Text("Continuar", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Siguiente",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun BookingStep3(
    data: BookingData,
    onDataChange: (BookingData) -> Unit,
    onConfirm: () -> Unit,
    onPrevious: () -> Unit,
    modifier: Modifier = Modifier
) {
    var couponCode by remember { mutableStateOf(data.couponCode) }
    var specialRequests by remember { mutableStateOf(data.specialRequests) }
    var couponError by remember { mutableStateOf<String?>(null) }
    var couponSuccess by remember { mutableStateOf<String?>(null) }
    
    // RF-067: Calcular subtotal
    val priceAdult = 250.0 // TODO: obtener del paquete seleccionado
    val priceChild = 200.0 // TODO: obtener del paquete seleccionado
    val subtotal = (data.adultCount * priceAdult) + (data.childCount * priceChild)
    val discount = data.discountAmount
    // RF-069: Calcular IGV 18%
    val igv = (subtotal - discount) * 0.18
    // RF-070: Calcular total
    val total = subtotal - discount + igv
    
    // Función para validar y aplicar cupón (RF-068)
    fun applyCoupon(code: String) {
        if (code.isBlank()) {
            couponError = "Ingrese un código de cupón"
            return
        }
        
        // TODO: Buscar cupón en la lista de cupones disponibles desde API
        // Por ahora, validación básica
        couponError = null
        couponSuccess = null
        
        // Ejemplo de validación local (idealmente viene del backend)
        val validCodes = mapOf(
            "FIESTASP2024" to (20.0 to "percentage"), // 20% descuento
            "VERANO50" to (50.0 to "fixed") // S/ 50 fijo
        )
        
        when {
            code.uppercase() in validCodes -> {
                val (value, type) = validCodes[code.uppercase()]!!
                val calculatedDiscount = when (type) {
                    "percentage" -> {
                        val discountAmount = subtotal * (value / 100.0)
                        // RF-068: Aplicar max_discount_amount si existe
                        minOf(discountAmount, subtotal * 0.3) // ejemplo: máximo 30% del subtotal
                    }
                    "fixed" -> minOf(value, subtotal * 0.5) // máximo 50% del subtotal
                    else -> 0.0
                }
                
                onDataChange(data.copy(
                    couponCode = code.uppercase(),
                    discountAmount = calculatedDiscount
                ))
                couponSuccess = "Cupón aplicado: -S/ ${String.format("%.2f", calculatedDiscount)}"
            }
            else -> {
                couponError = "Código de cupón inválido o expirado"
            }
        }
    }
    
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Confirmar Reserva",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Resumen completo del paquete
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
                    text = "Machu Picchu Full Day",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                
                Text(
                    text = "Cusco, Perú",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                InfoRow("Fecha de viaje", data.travelDate.ifEmpty { "No seleccionada" })
                InfoRow("Pasajeros", "${data.adultCount} adultos${if (data.childCount > 0) ", ${data.childCount} niños" else ""}${if (data.infantCount > 0) ", ${data.infantCount} bebés" else ""}")
                
                if (data.specialRequests.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    InfoRow("Solicitudes especiales", data.specialRequests)
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Campo de cupón
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
                    text = "Cupón de Descuento",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = couponCode,
                        onValueChange = { couponCode = it },
                        label = { Text("Código de cupón") },
                        placeholder = { Text("Ej: FIESTAS2024") },
                        modifier = Modifier.weight(1f)
                    )
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Button(
                        onClick = { applyCoupon(couponCode) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE53E3E)
                        ),
                        shape = RoundedCornerShape(8.dp),
                        enabled = couponCode.isNotBlank()
                    ) {
                        Text("Aplicar")
                    }
                }
                
                if (couponError != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = couponError!!,
                        fontSize = 12.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Medium
                    )
                }
                
                if (couponSuccess != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = couponSuccess!!,
                        fontSize = 12.sp,
                        color = Color(0xFF10B981),
                        fontWeight = FontWeight.Medium
                    )
                }
                
                if (data.discountAmount > 0 && couponSuccess == null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Descuento aplicado: S/ ${String.format("%.2f", data.discountAmount)}",
                        fontSize = 12.sp,
                        color = Color(0xFF10B981),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Solicitudes especiales
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
                    text = "Solicitudes Especiales",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                OutlinedTextField(
                    value = specialRequests,
                    onValueChange = { specialRequests = it },
                    label = { Text("Comentarios adicionales") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    maxLines = 5,
                    placeholder = { 
                        Text(
                            text = "Alergias alimentarias, necesidades especiales, preferencias de habitación, etc.",
                            color = Color.Gray
                        )
                    }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Resumen de precios completo
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Resumen de Precios",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Subtotal",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "S/ ${String.format("%.2f", subtotal)}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                }
                
                if (discount > 0) {
                Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Descuento",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "-S/ ${String.format("%.2f", discount)}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF10B981)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "IGV (18%)",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "S/ ${String.format("%.2f", igv)}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Divider()
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total a Pagar",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "S/ ${String.format("%.2f", total)}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE53E3E)
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Términos y condiciones
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Términos y Condiciones",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                Text(
                    text = "• Cancelación gratuita hasta 24 horas antes del viaje\n" +
                            "• Política de reembolso del 100% en caso de cancelación por clima\n" +
                            "• Incluye transporte, guía y almuerzo\n" +
                            "• No incluye entrada a Machu Picchu (S/ 45 adicionales)\n" +
                            "• Los precios incluyen IGV\n" +
                            "• Se requiere presentar DNI original el día del viaje",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    lineHeight = 16.sp
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Botones de navegación
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = onPrevious,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFE53E3E)
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFE53E3E))
                )
            ) {
                Text(
                    text = "Atrás",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            
            Button(
                onClick = onConfirm,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE53E3E)
                ),
                shape = RoundedCornerShape(8.dp),
                enabled = data.travelDate.isNotEmpty() && 
                         data.passengers.size >= data.adultCount + data.childCount + data.infantCount &&
                         data.passengers.all { it.isComplete() }
            ) {
                Text(
                    text = "Confirmar Reserva",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Información adicional
        Text(
            text = "Al confirmar tu reserva, recibirás un email con todos los detalles y el voucher de viaje.",
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

data class BookingData(
    val travelDate: String = "",
    val returnDate: String = "",
    val adultCount: Int = 1,
    val childCount: Int = 0,
    val infantCount: Int = 0,
    val specialRequests: String = "",
    val passengers: List<PassengerData> = emptyList(),
    val couponCode: String = "",
    val discountAmount: Double = 0.0,
    // Datos de contacto (RF-083)
    val contactEmail: String = "",
    val contactPhone: String = "",
    val emergencyContact: String = ""
)

data class PassengerData(
    val passengerType: String = "adult", // adult, child, infant (RF-078)
    val title: String = "", // mr, mrs, ms, dr (RF-079)
    val firstName: String = "",
    val lastName: String = "",
    val dateOfBirth: String = "",
    val gender: String = "", // M, F, O (RF-080)
    val passportNumber: String = "", // DNI o pasaporte (RF-081: 8 dígitos para DNI peruano)
    val nationality: String = "Peruana"
) {
    fun isComplete(): Boolean {
        return title.isNotEmpty() && // RF-079
               firstName.isNotEmpty() && 
               lastName.isNotEmpty() && 
               dateOfBirth.isNotEmpty() && 
               gender.isNotEmpty() && // RF-080: M, F, O
               passportNumber.isNotEmpty() && 
               nationality.isNotEmpty() &&
               isValidDNI(passportNumber) // RF-081: validar DNI 8 dígitos
    }
    
    // RF-081: Validar DNI peruano (8 dígitos numéricos)
    // Para carnet de extranjería: 12 dígitos alfanuméricos
    private fun isValidDNI(doc: String): Boolean {
        val cleaned = doc.trim()
        // DNI peruano: exactamente 8 dígitos
        if (cleaned.matches(Regex("^\\d{8}$"))) return true
        // Carnet de extranjería: 12 caracteres alfanuméricos
        if (cleaned.matches(Regex("^[A-Z0-9]{12}$"))) return true
        return false
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassengerForm(
    title: String,
    passenger: PassengerData,
    onPassengerChange: (PassengerData) -> Unit
) {
    var titleExpanded by remember { mutableStateOf(false) }
    var genderExpanded by remember { mutableStateOf(false) }
    var nationalityExpanded by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    
    val titleLabels = mapOf("mr" to "Sr.", "mrs" to "Sra.", "ms" to "Srta.", "dr" to "Dr.")
    val displayTitle = titleLabels[passenger.title] ?: ""
    
    val genderLabels = mapOf("M" to "Masculino", "F" to "Femenino", "O" to "Otro")
    val displayGender = genderLabels[passenger.gender] ?: ""
    
    // DatePicker para fecha de nacimiento
    val dateOfBirthPickerState = rememberDatePickerState(
        initialSelectedDateMillis = if (passenger.dateOfBirth.isNotEmpty()) {
            try {
                val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                format.parse(passenger.dateOfBirth)?.time
            } catch (e: Exception) {
                null
            }
        } else null
    )
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Header con icono
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = title,
                    tint = Color(0xFFE53E3E),
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Título (Sr., Sra., etc.) - RF-079
            ExposedDropdownMenuBox(
                expanded = titleExpanded,
                onExpandedChange = { titleExpanded = it }
            ) {
                OutlinedTextField(
                    value = displayTitle,
                    onValueChange = { },
                    label = { Text("Título") },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = titleExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFE53E3E),
                        unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                    )
                )
                
                ExposedDropdownMenu(
                    expanded = titleExpanded,
                    onDismissRequest = { titleExpanded = false }
                ) {
                    // RF-079: mr, mrs, ms, dr
                    listOf("mr" to "Sr.", "mrs" to "Sra.", "ms" to "Srta.", "dr" to "Dr.").forEach { (value, label) ->
                        DropdownMenuItem(
                            text = { Text(label) },
                            onClick = { 
                                onPassengerChange(passenger.copy(title = value))
                                titleExpanded = false
                            }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Nombre y apellido
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = passenger.firstName,
                    onValueChange = { onPassengerChange(passenger.copy(firstName = it)) },
                    label = { Text("Nombre") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFE53E3E),
                        unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                    )
                )
                
                OutlinedTextField(
                    value = passenger.lastName,
                    onValueChange = { onPassengerChange(passenger.copy(lastName = it)) },
                    label = { Text("Apellido") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFE53E3E),
                        unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                    )
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Fecha de nacimiento - Campo completo
            OutlinedTextField(
                value = passenger.dateOfBirth,
                onValueChange = { },
                readOnly = true,
                label = { Text("Fecha de nacimiento") },
                placeholder = { Text("DD/MM/AAAA") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker = true },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = "Fecha",
                        tint = Color(0xFFE53E3E),
                        modifier = Modifier.size(22.dp)
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = { showDatePicker = true },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Text(
                            text = "Seleccionar",
                            fontSize = 12.sp,
                            color = Color(0xFFE53E3E),
                            fontWeight = FontWeight.Medium
                        )
                    }
                },
                supportingText = {
                    Text(
                        text = "Toque para seleccionar fecha",
                        color = Color.Gray,
                        fontSize = 11.sp
                    )
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFE53E3E),
                    unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                ),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Género - RF-080: M, F, O
            ExposedDropdownMenuBox(
                expanded = genderExpanded,
                onExpandedChange = { genderExpanded = it }
            ) {
                OutlinedTextField(
                    value = displayGender,
                    onValueChange = { },
                    label = { Text("Género") },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = genderExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFE53E3E),
                        unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                    )
                )
                
                ExposedDropdownMenu(
                    expanded = genderExpanded,
                    onDismissRequest = { genderExpanded = false }
                ) {
                    listOf("M", "F", "O").forEach { gender ->
                        DropdownMenuItem(
                            text = { 
                                Text(
                                    when(gender) {
                                        "M" -> "Masculino"
                                        "F" -> "Femenino"
                                        "O" -> "Otro"
                                        else -> gender
                                    }
                                ) 
                            },
                            onClick = { 
                                onPassengerChange(passenger.copy(gender = gender))
                                genderExpanded = false
                            }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Nacionalidad - Ahora arriba para mejor UX
            ExposedDropdownMenuBox(
                expanded = nationalityExpanded,
                onExpandedChange = { nationalityExpanded = it }
            ) {
                OutlinedTextField(
                    value = passenger.nationality,
                    onValueChange = { },
                    label = { Text("Nacionalidad") },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = nationalityExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFE53E3E),
                        unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                    )
                )
                
                ExposedDropdownMenu(
                    expanded = nationalityExpanded,
                    onDismissRequest = { nationalityExpanded = false }
                ) {
                    listOf("Peruana", "Argentina", "Boliviana", "Brasileña", "Chilena", "Colombiana", "Ecuatoriana", "Otra").forEach { nationality ->
                        DropdownMenuItem(
                            text = { Text(nationality) },
                            onClick = { 
                                onPassengerChange(passenger.copy(nationality = nationality))
                                nationalityExpanded = false
                            }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // DNI / Pasaporte - Campo completo y mejorado
            OutlinedTextField(
                value = passenger.passportNumber,
                onValueChange = { 
                    // Solo permitir números y letras, limitar longitud
                    val filtered = if (passenger.nationality == "Peruana") {
                        it.filter { it.isDigit() }.take(8)
                    } else {
                        it.filter { it.isLetterOrDigit() }.take(12).uppercase()
                    }
                    onPassengerChange(passenger.copy(passportNumber = filtered))
                },
                label = { 
                    Text(
                        if (passenger.nationality == "Peruana") "DNI" else "Número de Pasaporte / Carnet"
                    )
                },
                placeholder = { 
                    Text(
                        if (passenger.nationality == "Peruana") "12345678" else "CE123456789",
                        color = Color.Gray.copy(alpha = 0.6f)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                isError = passenger.passportNumber.isNotEmpty() && !passenger.isComplete(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.CreditCard,
                        contentDescription = "Documento",
                        tint = if (passenger.passportNumber.isNotEmpty() && !passenger.isComplete()) 
                            Color.Red else Color(0xFFE53E3E),
                        modifier = Modifier.size(22.dp)
                    )
                },
                supportingText = {
                    if (passenger.passportNumber.isNotEmpty() && !passenger.isComplete()) {
                        Text(
                            text = if (passenger.nationality == "Peruana") 
                                "DNI debe tener 8 dígitos" else "Carnet debe tener 12 caracteres",
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    } else if (passenger.nationality == "Peruana") {
                        Text(
                            text = "Ingrese 8 dígitos numéricos",
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    } else {
                        Text(
                            text = "Ingrese 12 caracteres alfanuméricos",
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (passenger.passportNumber.isNotEmpty() && !passenger.isComplete()) 
                        Color.Red else Color(0xFFE53E3E),
                    unfocusedBorderColor = if (passenger.passportNumber.isNotEmpty() && !passenger.isComplete()) 
                        Color.Red.copy(alpha = 0.7f) else Color.Gray.copy(alpha = 0.5f),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = if (passenger.nationality == "Peruana") 
                        KeyboardType.Number else KeyboardType.Text
                )
            )
        }
    }
    
    // DatePicker Dialog para fecha de nacimiento
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        dateOfBirthPickerState.selectedDateMillis?.let { millis ->
                            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            val selectedDate = dateFormat.format(Date(millis))
                            onPassengerChange(passenger.copy(dateOfBirth = selectedDate))
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("Confirmar", color = Color(0xFFE53E3E), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar", color = Color.Gray)
                }
            }
        ) {
            DatePicker(state = dateOfBirthPickerState)
        }
    }
}
