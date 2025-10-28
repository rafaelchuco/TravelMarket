package com.example.travelmarket.views.ui.reviews.update

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
import com.example.travelmarket.logic.viewmodels.reviews.ReviewDetailViewModel
import com.example.travelmarket.logic.viewmodels.reviews.UpdateReviewViewModel
import com.example.travelmarket.views.navigation.Routes
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateReviewScreen(
    reviewId: Int,
    navController: NavController,
    viewModel: UpdateReviewViewModel = koinViewModel(),
    detailViewModel: ReviewDetailViewModel = koinViewModel()
) {
    var overallRating by remember { mutableStateOf("5") }
    var accommodationRating by remember { mutableStateOf("") }
    var transportRating by remember { mutableStateOf("") }
    var guideRating by remember { mutableStateOf("") }
    var valueRating by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }
    var pros by remember { mutableStateOf("") }
    var cons by remember { mutableStateOf("") }
    var isLoaded by remember { mutableStateOf(false) }

    val updateReviewState by viewModel.updateReviewState.collectAsState()
    val reviewDetailState by detailViewModel.reviewDetailState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(reviewId) {
        viewModel.resetUpdateState()
        detailViewModel.getReviewById(reviewId)
    }

    LaunchedEffect(reviewDetailState) {
        if (reviewDetailState is NetworkResult.Success && !isLoaded) {
            val review = (reviewDetailState as NetworkResult.Success).data
            overallRating = review.overallRating.toString()
            accommodationRating = review.accommodationRating?.toString() ?: ""
            transportRating = review.transportRating?.toString() ?: ""
            guideRating = review.guideRating?.toString() ?: ""
            valueRating = review.valueRating?.toString() ?: ""
            title = review.title
            comment = review.comment
            pros = review.pros ?: ""
            cons = review.cons ?: ""
            isLoaded = true
        }
    }

    LaunchedEffect(updateReviewState) {
        when (updateReviewState) {
            is NetworkResult.Success -> {
                snackbarHostState.showSnackbar(
                    message = "Reseña actualizada exitosamente",
                    duration = SnackbarDuration.Short
                )
                delay(1000)
                navController.popBackStack()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Actualizar Reseña") }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        if (reviewDetailState is NetworkResult.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "ID Reseña: $reviewId",
                    style = MaterialTheme.typography.titleMedium
                )

                // Título
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Calificación general
                OutlinedTextField(
                    value = overallRating,
                    onValueChange = { overallRating = it.filter { char -> char.isDigit() } },
                    label = { Text("Calificación General (1-5)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                // Calificaciones opcionales
                Text(
                    text = "Calificaciones Detalladas",
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
                        label = { Text("Calidad-Precio") },
                        placeholder = { Text("1-5") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                }

                // Comentario
                OutlinedTextField(
                    value = comment,
                    onValueChange = { comment = it },
                    label = { Text("Comentario") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 4,
                    maxLines = 8
                )

                // Pros
                OutlinedTextField(
                    value = pros,
                    onValueChange = { pros = it },
                    label = { Text("Aspectos Positivos") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2,
                    maxLines = 4
                )

                // Cons
                OutlinedTextField(
                    value = cons,
                    onValueChange = { cons = it },
                    label = { Text("Aspectos a Mejorar") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2,
                    maxLines = 4
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Botón actualizar
                Button(
                    onClick = {
                        viewModel.updateReview(
                            id = reviewId,
                            overallRating = overallRating.toIntOrNull(),
                            accommodationRating = accommodationRating.toIntOrNull(),
                            transportRating = transportRating.toIntOrNull(),
                            guideRating = guideRating.toIntOrNull(),
                            valueRating = valueRating.toIntOrNull(),
                            title = title.ifEmpty { null },
                            comment = comment.ifEmpty { null },
                            pros = pros.ifEmpty { null },
                            cons = cons.ifEmpty { null }
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = updateReviewState !is NetworkResult.Loading
                ) {
                    Text("Actualizar Reseña")
                }

                // Estados
                when (updateReviewState) {
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
                                Text("Actualizando reseña...")
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
                                text = "Error: ${(updateReviewState as NetworkResult.Error).message}",
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
}