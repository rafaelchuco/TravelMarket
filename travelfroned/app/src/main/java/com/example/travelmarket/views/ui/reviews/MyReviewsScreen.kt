package com.example.travelmarket.views.ui.reviews

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                title = {
                    Text(
                        "Mis Reseñas",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
        ) {
            when (myReviewsState) {
                is NetworkResult.Loading -> {
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
                                "Cargando tus reseñas...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        }
                    }
                }

                is NetworkResult.Success -> {
                    val reviews = (myReviewsState as NetworkResult.Success).data

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Header con contador y botón
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
                                        text = "Tus Opiniones",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "${reviews.size} reseñas publicadas",
                                        fontSize = 14.sp,
                                        color = Color.White.copy(alpha = 0.9f)
                                    )
                                }

                                IconButton(
                                    onClick = { viewModel.refresh() }
                                ) {
                                    Icon(
                                        Icons.Default.Refresh,
                                        contentDescription = "Actualizar",
                                        tint = Color.White,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            }
                        }

                        // Lista de reseñas
                        if (reviews.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(24.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White
                                    ),
                                    elevation = CardDefaults.cardElevation(4.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(32.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.RateReview,
                                            contentDescription = null,
                                            tint = Color.Gray,
                                            modifier = Modifier.size(64.dp)
                                        )
                                        Text(
                                            text = "No tienes reseñas aún",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF212121)
                                        )
                                        Text(
                                            text = "Comparte tu experiencia con otros viajeros",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color.Gray
                                        )
                                    }
                                }
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

                    // Dialog de eliminación
                    if (showDeleteDialog && reviewToDelete != null) {
                        AlertDialog(
                            onDismissRequest = {
                                showDeleteDialog = false
                                reviewToDelete = null
                            },
                            icon = {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = null,
                                    tint = Color(0xFFDC143C),
                                    modifier = Modifier.size(48.dp)
                                )
                            },
                            title = {
                                Text(
                                    "Eliminar Reseña",
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            text = {
                                Text("¿Estás seguro de que deseas eliminar esta reseña? Esta acción no se puede deshacer.")
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        reviewToDelete?.let { deleteViewModel.deleteReview(it.id) }
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFDC143C)
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
                                    Text("Cancelar", color = Color.Gray)
                                }
                            }
                        )
                    }
                }

                is NetworkResult.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Icon(
                                    Icons.Default.ErrorOutline,
                                    contentDescription = null,
                                    tint = Color(0xFFDC143C),
                                    modifier = Modifier.size(64.dp)
                                )
                                Text(
                                    "Error al cargar reseñas",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    (myReviewsState as NetworkResult.Error).message,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                                Button(
                                    onClick = { viewModel.refresh() },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFDC143C)
                                    )
                                ) {
                                    Icon(Icons.Default.Refresh, contentDescription = null)
                                    Spacer(Modifier.width(8.dp))
                                    Text("Reintentar")
                                }
                            }
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
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
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
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121),
                    modifier = Modifier.weight(1f)
                )

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color(0xFFFFC107)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = review.overallRating.toString(),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }

            // Comentario
            Text(
                text = review.comment ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                maxLines = 2
            )

            HorizontalDivider(color = Color(0xFFE0E0E0))

            // Footer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            Icons.Default.CardTravel,
                            contentDescription = null,
                            tint = Color(0xFFDC143C),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = review.packageName ?: "Sin paquete",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF212121),
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    if (!review.isApproved) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Surface(
                            color = Color(0xFFFF9800).copy(alpha = 0.1f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    Icons.Default.Schedule,
                                    contentDescription = null,
                                    tint = Color(0xFFFF9800),
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = "Pendiente de aprobación",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFFF9800)
                                )
                            }
                        }
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(
                        onClick = onEditClick,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar",
                            tint = Color(0xFF2196F3),
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    IconButton(
                        onClick = onDeleteClick,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = Color(0xFFDC143C),
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
    }
}
