package com.example.travelmarket.views.ui.destinations.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.travelmarket.logic.domain.models.Destination
import com.example.travelmarket.logic.viewmodels.destinations.DestinationsListState
import com.example.travelmarket.logic.viewmodels.destinations.DestinationsListViewModel
import com.example.travelmarket.ui.theme.RedMain
import com.example.travelmarket.ui.theme.TravelMarketTheme
import com.example.travelmarket.views.ui.destinations.list.components.DestinationListItem
import com.example.travelmarket.views.ui.home.components.AppBottomNavigation


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DestinationListScreen(
    onNavigateToPackages: (destinationId: String) -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToPackageList: () -> Unit,
    onNavigateToBookings: () -> Unit,
    onNavigateToProfile: () -> Unit,
    viewModel: DestinationsListViewModel = hiltViewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedChip by remember { mutableStateOf("Todos") }
    val chips = listOf("Todos", "Costa", "Sierra", "Selva")

    val state by viewModel.state.collectAsState()

    Scaffold(
        bottomBar = {
            AppBottomNavigation(
                selectedIndex = 1, // Destinos
                onInicioClick = onNavigateToHome,
                onDestinosClick = { /* Ya estamos aquí */ },
                onPaquetesClick = onNavigateToPackageList,
                onReservasClick = onNavigateToBookings,
                onPerfilClick = onNavigateToProfile
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5))
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "Explorar Destinos",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                    placeholder = { Text("Buscar destinos o departamentos...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                    shape = RoundedCornerShape(50),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = RedMain,
                        unfocusedBorderColor = Color.LightGray
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    chips.forEach { chip ->
                        FilterChip(
                            selected = (selectedChip == chip),
                            onClick = {
                                selectedChip = chip
                            },
                            label = { Text(chip) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = RedMain,
                                selectedLabelColor = Color.White,
                                containerColor = Color.White
                            )
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5))
            ) {
                when (val currentState = state) {
                    is DestinationsListState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    is DestinationsListState.Error -> {
                        ErrorView(
                            message = currentState.message,
                            onRetry = { viewModel.loadDestinations() },
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    is DestinationsListState.Success -> {
                        DestinationsListContent(
                            destinations = currentState.destinations,
                            onNavigateToPackages = onNavigateToPackages
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DestinationsListContent(
    destinations: List<Destination>,
    onNavigateToPackages: (destinationId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (destinations.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No se encontraron destinos.")
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(destinations) { destination ->
                DestinationListItem(
                    destination = destination,
                    onClick = {
                        onNavigateToPackages(destination.id.toString())
                    }
                )
            }
        }
    }
}

@Composable
private fun ErrorView(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "❌ Error al cargar",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Reintentar")
        }
    }
}