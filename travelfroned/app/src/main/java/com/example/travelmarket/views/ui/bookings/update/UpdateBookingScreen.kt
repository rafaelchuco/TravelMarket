package com.example.travelmarket.views.ui.bookings.update

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
                title = { Text("Actualizar Reserva") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Actualiza los datos de tu reserva",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "ID: $bookingId",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            OutlinedTextField(
                value = travelDate,
                onValueChange = { travelDate = it },
                label = { Text("Fecha de viaje (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = updateBookingState !is NetworkResult.Loading
            )

            OutlinedTextField(
                value = returnDate,
                onValueChange = { returnDate = it },
                label = { Text("Fecha de retorno (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = updateBookingState !is NetworkResult.Loading
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = numAdults,
                    onValueChange = { numAdults = it },
                    label = { Text("Adultos") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    enabled = updateBookingState !is NetworkResult.Loading
                )

                OutlinedTextField(
                    value = numChildren,
                    onValueChange = { numChildren = it },
                    label = { Text("Niños") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    enabled = updateBookingState !is NetworkResult.Loading
                )

                OutlinedTextField(
                    value = numInfants,
                    onValueChange = { numInfants = it },
                    label = { Text("Infantes") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    enabled = updateBookingState !is NetworkResult.Loading
                )
            }

            OutlinedTextField(
                value = specialRequests,
                onValueChange = { specialRequests = it },
                label = { Text("Solicitudes especiales") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5,
                enabled = updateBookingState !is NetworkResult.Loading
            )

            Spacer(modifier = Modifier.height(8.dp))

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
                enabled = updateBookingState !is NetworkResult.Loading
            ) {
                if (updateBookingState is NetworkResult.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Actualizando...")
                } else {
                    Text("Actualizar Reserva")
                }
            }
        }
    }
}