package com.example.travelmarket.views.ui.reviews

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.viewmodels.reviews.ReviewDetailViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewDetailScreen(
    reviewId: Int,
    viewModel: ReviewDetailViewModel = koinViewModel()
) {
    val reviewDetailState by viewModel.reviewDetailState.collectAsState()

    LaunchedEffect(reviewId) {
        viewModel.getReviewById(reviewId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Reseña") }
            )
        }
    ) { paddingValues ->
        when (reviewDetailState) {
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
                val review = (reviewDetailState as NetworkResult.Success).data

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = review.title,
                                style = MaterialTheme.typography.headlineSmall
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Rating",
                                    tint = Color(0xFFFFD700),
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(
                                    text = "${review.overallRating}/5",
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                if (review.isVerified) {
                                    AssistChip(
                                        onClick = { },
                                        label = { Text("Verificado") }
                                    )
                                }
                                if (review.isApproved) {
                                    AssistChip(
                                        onClick = { },
                                        label = { Text("Aprobado") }
                                    )
                                }
                            }
                        }
                    }

                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "CALIFICACIONES",
                                style = MaterialTheme.typography.titleMedium
                            )
                            HorizontalDivider()

                            review.accommodationRating?.let {
                                RatingRow("Alojamiento", it)
                            }
                            review.transportRating?.let {
                                RatingRow("Transporte", it)
                            }
                            review.guideRating?.let {
                                RatingRow("Guía", it)
                            }
                            review.valueRating?.let {
                                RatingRow("Relación calidad-precio", it)
                            }
                        }
                    }

                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "COMENTARIO",
                                style = MaterialTheme.typography.titleMedium
                            )
                            HorizontalDivider()
                            Text(text = review.comment)
                        }
                    }

                    review.pros?.let {
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "ASPECTOS POSITIVOS",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color(0xFF4CAF50)
                                )
                                HorizontalDivider()
                                Text(text = it)
                            }
                        }
                    }

                    review.cons?.let {
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "ASPECTOS A MEJORAR",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color(0xFFF44336)
                                )
                                HorizontalDivider()
                                Text(text = it)
                            }
                        }
                    }

                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "INFORMACIÓN",
                                style = MaterialTheme.typography.titleMedium
                            )
                            HorizontalDivider()
                            Text(text = "ID Reserva: ${review.bookingId}")
                            Text(text = "ID Paquete: ${review.packageId}")
                            Text(text = "ID Cliente: ${review.customerId}")
                            Text(text = "Fecha: ${review.createdAt}")
                        }
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
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Error: ${(reviewDetailState as NetworkResult.Error).message}",
                            color = MaterialTheme.colorScheme.error
                        )
                        Button(onClick = { viewModel.getReviewById(reviewId) }) {
                            Text("Reintentar")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RatingRow(label: String, rating: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color(0xFFFFD700),
                modifier = Modifier.size(16.dp)
            )
            Text(text = "$rating/5")
        }
    }
}