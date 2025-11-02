package com.example.travelmarket.views.ui.bookings.create

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.viewmodels.bookings.CreateBookingViewModel
import com.example.travelmarket.views.navigation.Routes
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateBookingScreen(
    navController: NavController,
    viewModel: CreateBookingViewModel = koinViewModel()
) {
    var packageId by remember { mutableStateOf("") }
    var travelDate by remember { mutableStateOf("") }
    var returnDate by remember { mutableStateOf("") }
    var numAdults by remember { mutableStateOf("1") }
    var numChildren by remember { mutableStateOf("0") }
    var numInfants by remember { mutableStateOf("0") }
    var totalAmount by remember { mutableStateOf("0.0") }  // ✅ AGREGADO
    var specialRequests by remember { mutableStateOf("") }

    val createBookingState by viewModel.createBookingState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.resetCreateState()
    }

    LaunchedEffect(createBookingState) {
        when (createBookingState) {
            is NetworkResult.Success -> {
                snackbarHostState.showSnackbar(
                    message = "Reserva creada exitosamente",
                    duration = SnackbarDuration.Short
                )
                delay(1000)
                navController.navigate(Routes.BookingsList.route) {
                    popUpTo(Routes.CreateBooking.route) { inclusive = true }
                }
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear Reserva") }
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
            Text(
                text = "Información de la Reserva",
                style = MaterialTheme.typography.titleMedium
            )

            // ID del Paquete
            OutlinedTextField(
                value = packageId,
                onValueChange = { packageId = it.filter { char -> char.isDigit() } },
                label = { Text("ID del Paquete *") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                supportingText = { Text("Ingresa el ID del paquete a reservar") }
            )

            // Fechas
            OutlinedTextField(
                value = travelDate,
                onValueChange = { travelDate = it },
                label = { Text("Fecha de viaje *") },
                placeholder = { Text("YYYY-MM-DD") },
                modifier = Modifier.fillMaxWidth(),
                supportingText = { Text("Ejemplo: 2025-12-25") }
            )

            OutlinedTextField(
                value = returnDate,
                onValueChange = { returnDate = it },
                label = { Text("Fecha de retorno *") },
                placeholder = { Text("YYYY-MM-DD") },
                modifier = Modifier.fillMaxWidth(),
                supportingText = { Text("Ejemplo: 2025-12-30") }
            )

            // Número de personas
            Text(
                text = "Número de Pasajeros",
                style = MaterialTheme.typography.titleSmall
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = numAdults,
                    onValueChange = { numAdults = it.filter { char -> char.isDigit() } },
                    label = { Text("Adultos *") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = numChildren,
                    onValueChange = { numChildren = it.filter { char -> char.isDigit() } },
                    label = { Text("Niños") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = numInfants,
                    onValueChange = { numInfants = it.filter { char -> char.isDigit() } },
                    label = { Text("Infantes") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }

            // ✅ AGREGADO - Total Amount
            OutlinedTextField(
                value = totalAmount,
                onValueChange = { totalAmount = it.filter { char -> char.isDigit() || char == '.' } },
                label = { Text("Monto Total *") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                supportingText = { Text("Ingresa el monto total de la reserva") }
            )

            // Solicitudes especiales
            OutlinedTextField(
                value = specialRequests,
                onValueChange = { specialRequests = it },
                label = { Text("Solicitudes especiales (Opcional)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5,
                placeholder = { Text("Preferencias de habitación, dieta, etc.") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Botón crear
            Button(
                onClick = {
                    viewModel.createBooking(
                        packageId = packageId.toIntOrNull() ?: 0,
                        travelDate = travelDate,
                        returnDate = returnDate,
                        numAdults = numAdults.toIntOrNull() ?: 1,
                        numChildren = numChildren.toIntOrNull() ?: 0,
                        numInfants = numInfants.toIntOrNull() ?: 0,
                        totalAmount = totalAmount.toDoubleOrNull() ?: 0.0,  // ✅ AGREGADO
                        specialRequests = specialRequests.ifEmpty { null }
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = packageId.isNotEmpty() &&
                        travelDate.isNotEmpty() &&
                        returnDate.isNotEmpty() &&
                        numAdults.isNotEmpty() &&
                        totalAmount.isNotEmpty() &&  // ✅ AGREGADO
                        createBookingState !is NetworkResult.Loading
            ) {
                Text("Crear Reserva")
            }

            // Estados
            when (createBookingState) {
                is NetworkResult.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            CircularProgressIndicator()
                            Text("Creando reserva...")
                        }
                    }
                }
                is NetworkResult.Error -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Text(
                            text = "Error: ${(createBookingState as NetworkResult.Error).message}",
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
                null -> {}
                else -> {}
            }
        }
    }
}
