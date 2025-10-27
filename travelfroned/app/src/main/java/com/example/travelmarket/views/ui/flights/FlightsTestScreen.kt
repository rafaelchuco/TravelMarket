package com.example.travelmarket.views.ui.flights

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.travelmarket.logic.domain.models.Flight
import com.example.travelmarket.logic.viewmodels.flights.FlightsListViewModel
import com.example.travelmarket.logic.viewmodels.flights.FlightsListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightsTestScreen(
    onBack: () -> Unit,
    viewModel: FlightsListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadFlights()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("✈️ Flights Test") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            when (val currentState = state) {
                is FlightsListState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                        Text(
                            "Cargando vuelos...",
                            modifier = Modifier.padding(top = 64.dp)
                        )
                    }
                }

                is FlightsListState.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "❌ Error",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(currentState.message)
                        Spacer(Modifier.height(16.dp))
                        Button(onClick = { viewModel.loadFlights() }) {
                            Text("Reintentar")
                        }
                    }
                }

                is FlightsListState.Success -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(
                                "GET /flights/", // Endpoint de ejemplo
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text("Total: ${currentState.flights.size} items")
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    if (currentState.flights.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("✅ API conectada - Sin vuelos en BD")
                        }
                    } else {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(currentState.flights) { flight ->
                                FlightItemCard(flight = flight)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FlightItemCard(flight: Flight) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(
                "Vuelo #${flight.id}", // Asume ID existe
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "${flight.origin} → ${flight.destination}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = flight.airline,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "S/. ${flight.price}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold // Hacer precio más visible
                )
            }

        }
    }
}