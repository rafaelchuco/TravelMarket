package com.example.travelmarket.views.ui.reviews

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Review
import com.example.travelmarket.logic.viewmodels.reviews.DeleteReviewViewModel
import com.example.travelmarket.logic.viewmodels.reviews.MyReviewsViewModel
import com.example.travelmarket.views.navigation.Routes
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyReviewsScreen(
    navController: NavController,
    viewModel: MyReviewsViewModel = koinViewModel(),
    deleteViewModel: DeleteReviewViewModel = koinViewModel()
) {
    val myReviewsState by viewModel.myReviewsState.collectAsState()
    val deleteReviewState by deleteViewModel.deleteReviewState.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var reviewToDelete by remember { mutableStateOf<Review?>(null) }

    LaunchedEffect(deleteReviewState) {
        deleteReviewState?.let { state ->
            when (state) {
                is NetworkResult.Success -> {
                    showDeleteDialog = false
                    reviewToDelete = null
                    viewModel.refresh()
                    deleteViewModel.resetDeleteState()
                }
                is NetworkResult.Error -> {
                    showDeleteDialog = false
                    reviewToDelete = null
                    deleteViewModel.resetDeleteState()
                }
                else -> {}
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Reseñas") }
            )
        }
    ) { paddingValues ->
        when (myReviewsState) {
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
                val reviews = (myReviewsState as NetworkResult.Success).data

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total: ${reviews.size} reseñas",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Button(
                            onClick = { viewModel.refresh() }
                        ) {
                            Text("Actualizar")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (reviews.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No tienes reseñas aún",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(reviews) { review ->
                                MyReviewCard(
                                    review = review,
                                    onEditClick = {
                                        navController.navigate(Routes.UpdateReview.createRoute(review.id))
                                    },
                                    onDeleteClick = {
                                        reviewToDelete = review
                                        showDeleteDialog = true
                                    },
                                    onClick = {
                                        navController.navigate(Routes.ReviewDetail.createRoute(review.id))
                                    }
                                )
                            }
                        }
                    }
                }

                if (showDeleteDialog && reviewToDelete != null) {
                    AlertDialog(
                        onDismissRequest = {
                            showDeleteDialog = false
                            reviewToDelete = null
                        },
                        title = { Text("Eliminar Reseña") },
                        text = { Text("¿Estás seguro de que deseas eliminar esta reseña?") },
                        confirmButton = {
                            Button(
                                onClick = {
                                    reviewToDelete?.let { deleteViewModel.deleteReview(it.id) }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.error
                                )
                            ) {
                                Text("Eliminar")
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    showDeleteDialog = false
                                    reviewToDelete = null
                                }
                            ) {
                                Text("Cancelar")
                            }
                        }
                    )
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
                            text = "Error: ${(myReviewsState as NetworkResult.Error).message}",
                            color = MaterialTheme.colorScheme.error
                        )
                        Button(onClick = { viewModel.refresh() }) {
                            Text("Reintentar")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MyReviewCard(
    review: Review,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Header con título y rating
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = review.title ?: "Sin título",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = review.overallRating.toString(),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            // Comentario
            Text(
                text = review.comment ?: "",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2
            )

            // Badge de aprobación
            if (!review.isApproved) {
                Surface(
                    color = MaterialTheme.colorScheme.errorContainer,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = "Pendiente de aprobación",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }

            // Footer con nombre del paquete y acciones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = review.packageName ?: "Sin paquete",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IconButton(onClick = onEditClick) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(onClick = onDeleteClick) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}
