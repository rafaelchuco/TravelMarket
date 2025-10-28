package com.example.travelmarket.views.ui.reviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.viewmodels.reviews.ReviewDetailViewModel
import com.example.travelmarket.logic.viewmodels.reviews.UpdateReviewViewModel
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
    var overallRating by remember { mutableStateOf(5) }
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
            overallRating = review.overallRating
            accommodationRating = review.accommodationRating?.toString() ?: ""
            transportRating = review.transportRating?.toString() ?: ""
            guideRating = review.guideRating?.toString() ?: ""
            valueRating = review.valueRating?.toString() ?: ""
            title = review.title ?: ""
            comment = review.comment ?: ""
            pros = review.pros ?: ""
            cons = review.cons ?: ""
            isLoaded = true
        }
    }

    LaunchedEffect(updateReviewState) {
        when (updateReviewState) {
            is NetworkResult.Success -> {
                snackbarHostState.showSnackbar(
                    message = "✅ Reseña actualizada exitosamente",
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
                title = {
                    Text(
                        "Actualizar Reseña",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
        ) {
            if (reviewDetailState is NetworkResult.Loading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CircularProgressIndicator(color = Color(0xFFDC143C))
                        Text(
                            "Cargando reseña...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // HEADER
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFDC143C)
                        ),
                        elevation = CardDefaults.cardElevation(4.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Editar tu Opinión",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "Reseña #$reviewId",
                                    fontSize = 14.sp,
                                    color = Color.White.copy(alpha = 0.9f)
                                )
                            }
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }

                    // INFORMACIÓN BÁSICA
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(4.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    Icons.Default.Article,
                                    contentDescription = null,
                                    tint = Color(0xFFDC143C),
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(
                                    text = "Información Básica",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF212121)
                                )
                            }

                            OutlinedTextField(
                                value = title,
                                onValueChange = { title = it },
                                label = { Text("Título de la reseña") },
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFFDC143C),
                                    focusedLabelColor = Color(0xFFDC143C)
                                )
                            )

                            // Calificación general con estrellas
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(
                                    text = "Calificación General",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF212121)
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        repeat(5) { index ->
                                            IconButton(
                                                onClick = { overallRating = index + 1 }
                                            ) {
                                                Icon(
                                                    if (index < overallRating) {
                                                        Icons.Default.Star
                                                    } else {
                                                        Icons.Default.StarBorder
                                                    },
                                                    contentDescription = null,
                                                    tint = Color(0xFFFFC107),
                                                    modifier = Modifier.size(36.dp)
                                                )
                                            }
                                        }
                                    }
                                    Surface(
                                        shape = RoundedCornerShape(20.dp),
                                        color = Color(0xFFDC143C)
                                    ) {
                                        Text(
                                            text = "$overallRating/5",
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White,
                                            modifier = Modifier.padding(
                                                horizontal = 16.dp,
                                                vertical = 8.dp
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // CALIFICACIONES DETALLADAS
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(4.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    Icons.Default.Assessment,
                                    contentDescription = null,
                                    tint = Color(0xFFDC143C),
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(
                                    text = "Calificaciones Detalladas (Opcional)",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF212121)
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                OutlinedTextField(
                                    value = accommodationRating,
                                    onValueChange = {
                                        accommodationRating = it.filter { char -> char.isDigit() }
                                    },
                                    label = { Text("Alojamiento") },
                                    placeholder = { Text("1-5") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.weight(1f),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color(0xFFDC143C),
                                        focusedLabelColor = Color(0xFFDC143C)
                                    )
                                )

                                OutlinedTextField(
                                    value = transportRating,
                                    onValueChange = {
                                        transportRating = it.filter { char -> char.isDigit() }
                                    },
                                    label = { Text("Transporte") },
                                    placeholder = { Text("1-5") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.weight(1f),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color(0xFFDC143C),
                                        focusedLabelColor = Color(0xFFDC143C)
                                    )
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                OutlinedTextField(
                                    value = guideRating,
                                    onValueChange = {
                                        guideRating = it.filter { char -> char.isDigit() }
                                    },
                                    label = { Text("Guía") },
                                    placeholder = { Text("1-5") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.weight(1f),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color(0xFFDC143C),
                                        focusedLabelColor = Color(0xFFDC143C)
                                    )
                                )

                                OutlinedTextField(
                                    value = valueRating,
                                    onValueChange = {
                                        valueRating = it.filter { char -> char.isDigit() }
                                    },
                                    label = { Text("Precio/Calidad") },
                                    placeholder = { Text("1-5") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.weight(1f),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color(0xFFDC143C),
                                        focusedLabelColor = Color(0xFFDC143C)
                                    )
                                )
                            }
                        }
                    }

                    // COMENTARIOS
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(4.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    Icons.Default.Comment,
                                    contentDescription = null,
                                    tint = Color(0xFFDC143C),
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(
                                    text = "Tu Experiencia",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF212121)
                                )
                            }

                            OutlinedTextField(
                                value = comment,
                                onValueChange = { comment = it },
                                label = { Text("Comentario") },
                                modifier = Modifier.fillMaxWidth(),
                                minLines = 4,
                                maxLines = 8,
                                placeholder = { Text("Describe tu experiencia...") },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFFDC143C),
                                    focusedLabelColor = Color(0xFFDC143C)
                                )
                            )

                            OutlinedTextField(
                                value = pros,
                                onValueChange = { pros = it },
                                label = { Text("Aspectos Positivos (Opcional)") },
                                modifier = Modifier.fillMaxWidth(),
                                minLines = 2,
                                maxLines = 4,
                                placeholder = { Text("¿Qué te gustó más?") },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFFDC143C),
                                    focusedLabelColor = Color(0xFFDC143C)
                                ),
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.ThumbUp,
                                        contentDescription = null,
                                        tint = Color(0xFF4CAF50)
                                    )
                                }
                            )

                            OutlinedTextField(
                                value = cons,
                                onValueChange = { cons = it },
                                label = { Text("Aspectos a Mejorar (Opcional)") },
                                modifier = Modifier.fillMaxWidth(),
                                minLines = 2,
                                maxLines = 4,
                                placeholder = { Text("¿Qué podría mejorar?") },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFFDC143C),
                                    focusedLabelColor = Color(0xFFDC143C)
                                ),
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.ThumbDown,
                                        contentDescription = null,
                                        tint = Color(0xFFFF9800)
                                    )
                                }
                            )
                        }
                    }

                    // Estados
                    when (updateReviewState) {
                        is NetworkResult.Loading -> {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White
                                ),
                                elevation = CardDefaults.cardElevation(4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(24.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        CircularProgressIndicator(color = Color(0xFFDC143C))
                                        Text(
                                            "Actualizando reseña...",
                                            fontSize = 14.sp,
                                            color = Color.Gray
                                        )
                                    }
                                }
                            }
                        }

                        is NetworkResult.Error -> {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFFFEBEE)
                                ),
                                elevation = CardDefaults.cardElevation(4.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.Error,
                                        contentDescription = null,
                                        tint = Color(0xFFC62828)
                                    )
                                    Text(
                                        text = "Error: ${(updateReviewState as NetworkResult.Error).message}",
                                        color = Color(0xFFC62828)
                                    )
                                }
                            }
                        }

                        else -> {}
                    }

                    // Botón actualizar
                    Button(
                        onClick = {
                            viewModel.updateReview(
                                id = reviewId,
                                overallRating = overallRating,
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
                        enabled = updateReviewState !is NetworkResult.Loading,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFDC143C)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Update, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Actualizar Reseña", fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
