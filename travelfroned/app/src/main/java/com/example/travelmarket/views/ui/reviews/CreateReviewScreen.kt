package com.example.travelmarket.views.ui.reviews

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
import com.example.travelmarket.logic.viewmodels.reviews.CreateReviewViewModel
import com.example.travelmarket.views.navigation.Routes
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateReviewScreen(
    navController: NavController,
    viewModel: CreateReviewViewModel = koinViewModel()
) {
    var overallRating by remember { mutableStateOf(5) }
    var accommodationRating by remember { mutableStateOf("") }
    var transportRating by remember { mutableStateOf("") }
    var guideRating by remember { mutableStateOf("") }
    var valueRating by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }
    var pros by remember { mutableStateOf("") }
    var cons by remember { mutableStateOf("") }
    var bookingId by remember { mutableStateOf("") }
    var packageId by remember { mutableStateOf("") }

    val createReviewState by viewModel.createReviewState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.resetCreateState()
    }

    LaunchedEffect(createReviewState) {
        when (createReviewState) {
            is NetworkResult.Success -> {
                snackbarHostState.showSnackbar(
                    message = "Reseña creada exitosamente",
                    duration = SnackbarDuration.Short
                )
                delay(1000)
                navController.popBackStack()  // ✅ Solo regresa atrás
            }
            else -> {}
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear Reseña") }
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
                text = "Información de la Reseña",
                style = MaterialTheme.typography.titleMedium
            )

            // Título
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título *") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Ej: Excelente experiencia") }
            )

            // Calificación general
            Text(
                text = "Calificación General: $overallRating/5 *",
                style = MaterialTheme.typography.bodyLarge
            )
            Slider(
                value = overallRating.toFloat(),
                onValueChange = { overallRating = it.toInt() },
                valueRange = 1f..5f,
                steps = 3
            )

            // Calificaciones opcionales
            Text(
                text = "Calificaciones Detalladas (Opcional)",
                style = MaterialTheme.typography.titleSmall
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = accommodationRating,
                    onValueChange = { accommodationRating = it.filter { char -> char.isDigit() } },
                    label = { Text("Alojamiento") },
                    placeholder = { Text("1-5") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = transportRating,
                    onValueChange = { transportRating = it.filter { char -> char.isDigit() } },
                    label = { Text("Transporte") },
                    placeholder = { Text("1-5") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = guideRating,
                    onValueChange = { guideRating = it.filter { char -> char.isDigit() } },
                    label = { Text("Guía") },
                    placeholder = { Text("1-5") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = valueRating,
                    onValueChange = { valueRating = it.filter { char -> char.isDigit() } },
                    label = { Text("Relación calidad-precio") },
                    placeholder = { Text("1-5") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }

            // Comentario
            OutlinedTextField(
                value = comment,
                onValueChange = { comment = it },
                label = { Text("Comentario *") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4,
                maxLines = 8,
                placeholder = { Text("Describe tu experiencia...") }
            )

            // Pros
            OutlinedTextField(
                value = pros,
                onValueChange = { pros = it },
                label = { Text("Aspectos Positivos (Opcional)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                maxLines = 4,
                placeholder = { Text("¿Qué te gustó más?") }
            )

            // Cons
            OutlinedTextField(
                value = cons,
                onValueChange = { cons = it },
                label = { Text("Aspectos a Mejorar (Opcional)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                maxLines = 4,
                placeholder = { Text("¿Qué podría mejorar?") }
            )

            // IDs
            Text(
                text = "Referencias",
                style = MaterialTheme.typography.titleSmall
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = bookingId,
                    onValueChange = { bookingId = it.filter { char -> char.isDigit() } },
                    label = { Text("ID Reserva *") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = packageId,
                    onValueChange = { packageId = it.filter { char -> char.isDigit() } },
                    label = { Text("ID Paquete *") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón crear
            Button(
                onClick = {
                    viewModel.createReview(
                        overallRating = overallRating,
                        accommodationRating = accommodationRating.toIntOrNull(),
                        transportRating = transportRating.toIntOrNull(),
                        guideRating = guideRating.toIntOrNull(),
                        valueRating = valueRating.toIntOrNull(),
                        title = title,
                        comment = comment,
                        pros = pros.ifEmpty { null },
                        cons = cons.ifEmpty { null },
                        bookingId = bookingId.toIntOrNull() ?: 0,
                        packageId = packageId.toIntOrNull() ?: 0
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = title.isNotEmpty() &&
                        comment.isNotEmpty() &&
                        bookingId.isNotEmpty() &&
                        packageId.isNotEmpty() &&
                        createReviewState !is NetworkResult.Loading
            ) {
                Text("Crear Reseña")
            }

            // Estados
            when (createReviewState) {
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
                            Text("Creando reseña...")
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
                            text = "Error: ${(createReviewState as NetworkResult.Error).message}",
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