package com.example.travelmarket.ui.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingFlowScreen() {
    var currentStep by remember { mutableStateOf(0) }
    var bookingData by remember { mutableStateOf(BookingData()) }
    
    val steps = listOf("Información", "Pasajeros", "Confirmación")
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header con progreso
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
                        onClick = { /* TODO: Navegar hacia atrás */ }
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
        
        // Contenido del paso actual
        when (currentStep) {
            0 -> BookingStep1(
                data = bookingData,
                onDataChange = { bookingData = it },
                onNext = { currentStep = 1 }
            )
            1 -> BookingStep2(
                data = bookingData,
                onDataChange = { bookingData = it },
                onNext = { currentStep = 2 },
                onPrevious = { currentStep = 0 }
            )
            2 -> BookingStep3(
                data = bookingData,
                onConfirm = { /* TODO: Confirmar reserva */ },
                onPrevious = { currentStep = 1 }
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

@Composable
fun BookingStep1(
    data: BookingData,
    onDataChange: (BookingData) -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
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
        
        // Campos de entrada
        OutlinedTextField(
            value = data.travelDate,
            onValueChange = { onDataChange(data.copy(travelDate = it)) },
            label = { Text("Fecha de viaje") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                TextButton(onClick = { /* TODO: Abrir selector de fecha */ }) {
                    Text("Seleccionar")
                }
            }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = data.specialRequests,
            onValueChange = { onDataChange(data.copy(specialRequests = it)) },
            label = { Text("Solicitudes especiales (opcional)") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE53E3E)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Continuar",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun BookingStep2(
    data: BookingData,
    onDataChange: (BookingData) -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
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
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Información de pasajeros individuales
        if (data.adultCount + data.childCount + data.infantCount > 0) {
            Text(
                text = "Datos de los pasajeros",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
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
                onClick = onNext,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE53E3E)
                ),
                shape = RoundedCornerShape(8.dp),
                enabled = data.passengers.size >= data.adultCount + data.childCount + data.infantCount &&
                         data.passengers.all { it.isComplete() }
            ) {
                Text(
                    text = "Continuar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun BookingStep3(
    data: BookingData,
    onConfirm: () -> Unit,
    onPrevious: () -> Unit
) {
    var couponCode by remember { mutableStateOf(data.couponCode) }
    var specialRequests by remember { mutableStateOf(data.specialRequests) }
    
    val subtotal = data.adultCount * 250.0 + data.childCount * 200.0
    val discount = data.discountAmount
    val igv = (subtotal - discount) * 0.18
    val total = subtotal - discount + igv
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
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
                        onClick = { /* TODO: Aplicar cupón */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE53E3E)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Aplicar")
                    }
                }
                
                if (data.discountAmount > 0) {
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
    val discountAmount: Double = 0.0
)

data class PassengerData(
    val passengerType: String = "adult", // adult, child, infant
    val title: String = "", // Sr., Sra., etc.
    val firstName: String = "",
    val lastName: String = "",
    val dateOfBirth: String = "",
    val gender: String = "", // M, F
    val dni: String = "",
    val nationality: String = "Peruana"
) {
    fun isComplete(): Boolean {
        return firstName.isNotEmpty() && 
               lastName.isNotEmpty() && 
               dateOfBirth.isNotEmpty() && 
               gender.isNotEmpty() && 
               dni.isNotEmpty() && 
               nationality.isNotEmpty()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassengerForm(
    title: String,
    passenger: PassengerData,
    onPassengerChange: (PassengerData) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            // Título (Sr., Sra., etc.)
            ExposedDropdownMenuBox(
                expanded = false,
                onExpandedChange = { }
            ) {
                OutlinedTextField(
                    value = passenger.title,
                    onValueChange = { onPassengerChange(passenger.copy(title = it)) },
                    label = { Text("Título") },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                
                ExposedDropdownMenu(
                    expanded = false,
                    onDismissRequest = { }
                ) {
                    listOf("Sr.", "Sra.", "Srta.").forEach { titleOption ->
                        DropdownMenuItem(
                            text = { Text(titleOption) },
                            onClick = { onPassengerChange(passenger.copy(title = titleOption)) }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Nombre y apellido
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = passenger.firstName,
                    onValueChange = { onPassengerChange(passenger.copy(firstName = it)) },
                    label = { Text("Nombre") },
                    modifier = Modifier.weight(1f)
                )
                
                OutlinedTextField(
                    value = passenger.lastName,
                    onValueChange = { onPassengerChange(passenger.copy(lastName = it)) },
                    label = { Text("Apellido") },
                    modifier = Modifier.weight(1f)
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Fecha de nacimiento y género
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = passenger.dateOfBirth,
                    onValueChange = { onPassengerChange(passenger.copy(dateOfBirth = it)) },
                    label = { Text("Fecha de nacimiento") },
                    placeholder = { Text("DD/MM/AAAA") },
                    modifier = Modifier.weight(1f)
                )
                
                ExposedDropdownMenuBox(
                    expanded = false,
                    onExpandedChange = { }
                ) {
                    OutlinedTextField(
                        value = passenger.gender,
                        onValueChange = { },
                        label = { Text("Género") },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                        modifier = Modifier
                            .weight(1f)
                            .menuAnchor()
                    )
                    
                    ExposedDropdownMenu(
                        expanded = false,
                        onDismissRequest = { }
                    ) {
                        listOf("M", "F").forEach { gender ->
                            DropdownMenuItem(
                                text = { Text(if (gender == "M") "Masculino" else "Femenino") },
                                onClick = { onPassengerChange(passenger.copy(gender = gender)) }
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // DNI y nacionalidad
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = passenger.dni,
                    onValueChange = { onPassengerChange(passenger.copy(dni = it)) },
                    label = { Text("DNI") },
                    placeholder = { Text("12345678") },
                    modifier = Modifier.weight(1f)
                )
                
                ExposedDropdownMenuBox(
                    expanded = false,
                    onExpandedChange = { }
                ) {
                    OutlinedTextField(
                        value = passenger.nationality,
                        onValueChange = { },
                        label = { Text("Nacionalidad") },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                        modifier = Modifier
                            .weight(1f)
                            .menuAnchor()
                    )
                    
                    ExposedDropdownMenu(
                        expanded = false,
                        onDismissRequest = { }
                    ) {
                        listOf("Peruana", "Argentina", "Boliviana", "Brasileña", "Chilena", "Colombiana", "Ecuatoriana", "Otra").forEach { nationality ->
                            DropdownMenuItem(
                                text = { Text(nationality) },
                                onClick = { onPassengerChange(passenger.copy(nationality = nationality)) }
                            )
                        }
                    }
                }
            }
        }
    }
}
