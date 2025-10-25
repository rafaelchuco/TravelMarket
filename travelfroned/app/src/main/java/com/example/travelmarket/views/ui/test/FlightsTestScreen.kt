package com.example.travelmarket.views.ui.test

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel  // ✅ IMPORTAR
import com.example.travelmarket.logic.viewmodels.flights.FlightsListViewModel
import com.example.travelmarket.logic.viewmodels.flights.FlightsListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightsTestScreen(
    onBack: () -> Unit,
    viewModel: FlightsListViewModel = hiltViewModel()  // ✅ CAMBIADO
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("✈️ Flights Test") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Volver")
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
                                "GET /flights/",
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
                        LazyColumn {
                            items(currentState.flights) { flight ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                ) {
                                    Column(Modifier.padding(12.dp)) {
                                        Text(
                                            "Vuelo #${flight.id}",
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Text(
                                            "${flight.origin} → ${flight.destination}",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Text(
                                            "Precio: $${flight.price}",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
