package com.example.travelmarket.views.ui.flights

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Flight
import com.example.travelmarket.logic.viewmodels.flights.FlightsListViewModel
import com.example.travelmarket.ui.theme.RedMain
import com.example.travelmarket.views.ui.home.components.AppBottomNavigation
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchScreen(
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToDestinations: () -> Unit,
    onNavigateToPackages: () -> Unit,
    onNavigateToBookings: () -> Unit,
    onNavigateToProfile: () -> Unit,
    viewModel: FlightsListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    var origin by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }
    var departureDate by remember { mutableStateOf("") }
    var returnDate by remember { mutableStateOf("") }
    var passengers by remember { mutableStateOf(1) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Buscar Vuelos") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        },
        bottomBar = {
            AppBottomNavigation(
                selectedIndex = -1, // Ninguno seleccionado
                onInicioClick = onNavigateToHome,
                onDestinosClick = onNavigateToDestinations,
                onPaquetesClick = onNavigateToPackages,
                onReservasClick = onNavigateToBookings,
                onPerfilClick = onNavigateToProfile
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        FlightTextField(
                            value = origin,
                            onValueChange = { origin = it },
                            label = "Origen",
                            icon = Icons.Default.FlightTakeoff
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        FlightTextField(
                            value = destination,
                            onValueChange = { destination = it },
                            label = "Destino",
                            icon = Icons.Default.FlightLand
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            FlightDateField(
                                value = departureDate,
                                onValueChange = { departureDate = it },
                                label = "Ida",
                                modifier = Modifier.weight(1f)
                            )
                            FlightDateField(
                                value = returnDate,
                                onValueChange = { returnDate = it },
                                label = "Vuelta (Opcional)",
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        PassengerSelector(
                            count = passengers,
                            onCountChange = { passengers = it }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.loadFlights() }, // Simplified for now
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = RedMain),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Buscar Vuelos", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }

            when (val flightsState = state) {
                is NetworkResult.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                is NetworkResult.Error -> {
                    item {
                        Text(
                            text = "Error: ${flightsState.message}",
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
                is NetworkResult.Success -> {
                    val flights = flightsState.data
                    if (flights != null && flights.isNotEmpty()) {
                        item {
                            Text(
                                text = "${flights.size} vuelos encontrados",
                                fontSize = 14.sp,
                                color = Color.DarkGray,
                                modifier = Modifier.padding(bottom = 8.dp, top = 16.dp)
                            )
                        }
                        items(flights) { flight ->
                            FlightResultCard(flight = flight, onSelectClick = {})
                        }
                    } else {
                        item {
                            Text("No se encontraron vuelos para esta búsqueda.")
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun FlightTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        label = { Text(label) },
        leadingIcon = { Icon(icon, contentDescription = label) },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF0F0F0),
            unfocusedContainerColor = Color(0xFFF0F0F0),
            focusedBorderColor = RedMain,
            unfocusedBorderColor = Color.LightGray
        ),
        singleLine = true
    )
}

@Composable
fun FlightDateField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = { Text(label) },
        leadingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = label) },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF0F0F0),
            unfocusedContainerColor = Color(0xFFF0F0F0),
            focusedBorderColor = RedMain,
            unfocusedBorderColor = Color.LightGray
        ),
        singleLine = true,
        readOnly = true
    )
}

@Composable
fun PassengerSelector(count: Int, onCountChange: (Int) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Pasajeros", fontWeight = FontWeight.SemiBold)
        Row(verticalAlignment = Alignment.CenterVertically) {
            SmallCircleButton(icon = Icons.Default.Remove) { if (count > 1) onCountChange(count - 1) }
            Text(
                text = count.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .widthIn(min = 24.dp),
                textAlign = TextAlign.Center
            )
            SmallCircleButton(icon = Icons.Default.Add) { onCountChange(count + 1) }
        }
    }
}

@Composable
fun SmallCircleButton(icon: ImageVector, enabled: Boolean = true, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(if (enabled) RedMain.copy(alpha = 0.1f) else Color.LightGray.copy(alpha = 0.5f))
    ) {
        Icon(icon, contentDescription = null, tint = if (enabled) RedMain else Color.Gray)
    }
}

@Composable
fun FlightResultCard(flight: Flight, onSelectClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(flight.airline, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Text(
                    text = "S/. ${flight.price}",
                    color = RedMain,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TimeColumn(time = flight.departureDate, label = "SAL")
                DurationColumn(duration = "N/A", stops = "N/A") // Placeholder
                TimeColumn(time = flight.arrivalDate, label = "LLE")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onSelectClick,
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RedMain.copy(alpha = 0.1f),
                    contentColor = RedMain
                ),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text("Seleccionar", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
fun TimeColumn(time: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(time, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text(label, fontSize = 12.sp, color = Color.Gray)
    }
}

@Composable
fun DurationColumn(duration: String, stops: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(duration, fontSize = 12.sp, color = Color.Gray)
        Divider(modifier = Modifier.width(60.dp).padding(vertical = 2.dp), color = Color.LightGray)
        Text(stops, fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.SemiBold)
    }
}