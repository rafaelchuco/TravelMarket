package com.example.travelmarket.views.ui.activities.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.viewmodels.activities.ActivityDetailViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityDetailScreen(
    activityId: Int,
    viewModel: ActivityDetailViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()  // ✅ CAMBIÓ A uiState

    LaunchedEffect(activityId) {
        viewModel.getActivityDetail(activityId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Actividad") }
            )
        }
    ) { paddingValues ->
        when (uiState) {  // ✅ CAMBIÓ A uiState
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
                val activity = (uiState as NetworkResult.Success).data  // ✅ CAMBIÓ A uiState
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = activity.name,
                        style = MaterialTheme.typography.headlineMedium
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(text = "ID: ${activity.id}")
                    Text(text = "Tipo: ${activity.activityType}")
                    Text(text = "Duración: ${activity.durationHours} horas")
                    Text(text = "Dificultad: ${activity.difficultyLevel}")
                    Text(text = "Precio por persona: $${activity.pricePerPerson}")
                    Text(text = "Tamaño máximo de grupo: ${activity.maxGroupSize}")
                    Text(text = "Activo: ${if (activity.isActive) "Sí" else "No"}")
                    Text(text = "Destino ID: ${activity.destinationId}")

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        text = "Descripción:",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(text = activity.description)

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(text = "Creado: ${activity.createdAt}")
                    Text(text = "Actualizado: ${activity.updatedAt}")
                }
            }
            is NetworkResult.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Error: ${(uiState as NetworkResult.Error).message}",  // ✅ CAMBIÓ A uiState
                            color = MaterialTheme.colorScheme.error
                        )
                        Button(onClick = { viewModel.getActivityDetail(activityId) }) {
                            Text("Reintentar")
                        }
                    }
                }
            }
        }
    }
}