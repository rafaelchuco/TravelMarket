package com.example.travelmarket.views.ui.bookings.update

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
import com.example.travelmarket.logic.viewmodels.bookings.UpdateBookingViewModel
import com.example.travelmarket.views.navigation.Routes
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateBookingScreen(
    bookingId: Int,
    navController: NavController,
    viewModel: UpdateBookingViewModel = koinViewModel()
) {
    var travelDate by remember { mutableStateOf("") }
    var returnDate by remember { mutableStateOf("") }
    var numAdults by remember { mutableStateOf("") }
    var numChildren by remember { mutableStateOf("") }
    var numInfants by remember { mutableStateOf("") }
    var specialRequests by remember { mutableStateOf("") }

    val updateBookingState by viewModel.updateBookingState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var hasNavigated by remember { mutableStateOf(false) }

    LaunchedEffect(updateBookingState) {
        if (!hasNavigated) {
            when (val state = updateBookingState) {
                is NetworkResult.Success -> {
                    hasNavigated = true
                    snackbarHostState.showSnackbar("✅ Reserva actualizada exitosamente")
                    navController.navigate(Routes.BookingDetail.createRoute(bookingId)) {
                        popUpTo(Routes.UpdateBooking.route) { inclusive = true }
                    }
                }
                is NetworkResult.Error -> {
                    snackbarHostState.showSnackbar("❌ ${state.message}")
                }
                else -> {}
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Actualizar Reserva",
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
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // HEADER
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFDC143C)
                    ),
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Modifica tu Reserva",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "ID: #$bookingId",
                                fontSize = 14.sp,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }

                // FECHAS
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
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                Icons.Default.CalendarToday,
                                contentDescription = null,
                                tint = Color(0xFFDC143C),
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "Fechas del Viaje",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF212121)
                            )
                        }

                        OutlinedTextField(
                            value = travelDate,
                            onValueChange = { travelDate = it },
                            label = { Text("Fecha de viaje") },
                            placeholder = { Text("YYYY-MM-DD") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.FlightTakeoff,
                                    contentDescription = null,
                                    tint = Color(0xFFDC143C)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            enabled = updateBookingState !is NetworkResult.Loading,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFFDC143C),
                                focusedLabelColor = Color(0xFFDC143C)
                            )
                        )

                        OutlinedTextField(
                            value = returnDate,
                            onValueChange = { returnDate = it },
                            label = { Text("Fecha de retorno") },
                            placeholder = { Text("YYYY-MM-DD") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.FlightLand,
                                    contentDescription = null,
                                    tint = Color(0xFFDC143C)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            enabled = updateBookingState !is NetworkResult.Loading,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFFDC143C),
                                focusedLabelColor = Color(0xFFDC143C)
                            )
                        )
                    }
                }

                // PASAJEROS
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
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                Icons.Default.People,
                                contentDescription = null,
                                tint = Color(0xFFDC143C),
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "Número de Pasajeros",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF212121)
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OutlinedTextField(
                                value = numAdults,
                                onValueChange = { numAdults = it.filter { char -> char.isDigit() } },
                                label = { Text("Adultos") },
                                placeholder = { Text("0") },
                                modifier = Modifier.weight(1f),
                                singleLine = true,
                                enabled = updateBookingState !is NetworkResult.Loading,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFFDC143C),
                                    focusedLabelColor = Color(0xFFDC143C)
                                )
                            )

                            OutlinedTextField(
                                value = numChildren,
                                onValueChange = { numChildren = it.filter { char -> char.isDigit() } },
                                label = { Text("Niños") },
                                placeholder = { Text("0") },
                                modifier = Modifier.weight(1f),
                                singleLine = true,
                                enabled = updateBookingState !is NetworkResult.Loading,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFFDC143C),
                                    focusedLabelColor = Color(0xFFDC143C)
                                )
                            )

                            OutlinedTextField(
                                value = numInfants,
                                onValueChange = { numInfants = it.filter { char -> char.isDigit() } },
                                label = { Text("Bebés") },
                                placeholder = { Text("0") },
                                modifier = Modifier.weight(1f),
                                singleLine = true,
                                enabled = updateBookingState !is NetworkResult.Loading,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFFDC143C),
                                    focusedLabelColor = Color(0xFFDC143C)
                                )
                            )
                        }
                    }
                }

                // SOLICITUDES ESPECIALES
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
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                Icons.Default.Notes,
                                contentDescription = null,
                                tint = Color(0xFFDC143C),
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "Solicitudes Especiales",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF212121)
                            )
                        }

                        OutlinedTextField(
                            value = specialRequests,
                            onValueChange = { specialRequests = it },
                            label = { Text("Escribe tus solicitudes (opcional)") },
                            placeholder = { Text("Ej: Habitación con vista al mar...") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 4,
                            maxLines = 6,
                            enabled = updateBookingState !is NetworkResult.Loading,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFFDC143C),
                                focusedLabelColor = Color(0xFFDC143C)
                            )
                        )
                    }
                }

                // ESTADO DE CARGA
                if (updateBookingState is NetworkResult.Loading) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                CircularProgressIndicator(color = Color(0xFFDC143C))
                                Text(
                                    "Actualizando reserva...",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }

                // BOTÓN ACTUALIZAR
                Button(
                    onClick = {
                        viewModel.updateBooking(
                            id = bookingId,
                            bookingNumber = null,
                            travelDate = travelDate.ifEmpty { null },
                            returnDate = returnDate.ifEmpty { null },
                            numAdults = numAdults.toIntOrNull(),
                            numChildren = numChildren.toIntOrNull(),
                            numInfants = numInfants.toIntOrNull(),
                            subtotal = null,
                            discountAmount = null,
                            taxAmount = null,
                            totalAmount = null,
                            paidAmount = null,
                            status = null,
                            paymentStatus = null,
                            specialRequests = specialRequests.ifEmpty { null },
                            customer = null,
                            packageId = null
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = updateBookingState !is NetworkResult.Loading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFDC143C)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Update, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        if (updateBookingState is NetworkResult.Loading) "Actualizando..." else "Actualizar Reserva",
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
