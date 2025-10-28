package com.example.travelmarket.views.ui.destinations.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Destination
import com.example.travelmarket.logic.viewmodels.destinations.DestinationsListViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DestinationsListScreen(
    viewModel: DestinationsListViewModel = koinViewModel()
) {
    val destinationsState by viewModel.destinationsState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Destinos") }
            )
        }
    ) { paddingValues ->
        when (destinationsState) {
            is NetworkResult.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is NetworkResult.Success -> {
                val destinations = (destinationsState as NetworkResult.Success).data
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(destinations) { destination ->
                        DestinationItem(destination = destination)
                    }
                }
            }
            is NetworkResult.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Error: ${(destinationsState as NetworkResult.Error).message}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
fun DestinationItem(destination: Destination) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = "ID: ${destination.id}")
        Text(text = "Nombre: ${destination.name}")
        Text(text = "País: ${destination.country}")
        Text(text = "Continente: ${destination.continent}")
        Text(text = "Descripción: ${destination.shortDescription}")
        Text(text = "Mejor temporada: ${destination.bestSeason}")
        Text(text = "Popular: ${if (destination.isPopular) "Sí" else "No"}")
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
    }
}