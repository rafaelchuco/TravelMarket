package com.example.travelmarket.views.ui.bookings.update

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    var totalAmount by remember { mutableStateOf("") }
    var specialRequests by remember { mutableStateOf("") }

    val updateBookingState by viewModel.updateBookingState.collectAsState()

    LaunchedEffect(updateBookingState) {
        when (updateBookingState) {
            is NetworkResult.Success -> {
                navController.navigate(Routes.BookingDetail.createRoute(bookingId)) {
                    popUpTo(Routes.UpdateBooking.route) { inclusive = true }
                }
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Actualizar Reserva") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "ID Reserva: $bookingId", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = travelDate,
                onValueChange = { travelDate = it },
                label = { Text("Fecha de viaje (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = returnDate,
                onValueChange = { returnDate = it },
                label = { Text("Fecha de retorno (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = numAdults,
                onValueChange = { numAdults = it },
                label = { Text("Adultos") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = numChildren,
                onValueChange = { numChildren = it },
                label = { Text("Niños") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = numInfants,
                onValueChange = { numInfants = it },
                label = { Text("Infantes") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = totalAmount,
                onValueChange = { totalAmount = it },
                label = { Text("Monto total") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = specialRequests,
                onValueChange = { specialRequests = it },
                label = { Text("Solicitudes especiales") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(16.dp))

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
                        totalAmount = totalAmount.ifEmpty { null },
                        paidAmount = null,
                        status = null,
                        paymentStatus = null,
                        specialRequests = specialRequests.ifEmpty { null },
                        customer = null,
                        packageId = null
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Actualizar Reserva")
            }

            when (updateBookingState) {
                is NetworkResult.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is NetworkResult.Error -> {
                    Text(
                        text = "Error: ${(updateBookingState as NetworkResult.Error).message}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
                else -> {}
            }
        }
    }
}