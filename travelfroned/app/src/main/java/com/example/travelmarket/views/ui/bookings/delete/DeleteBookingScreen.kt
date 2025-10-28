package com.example.travelmarket.views.ui.bookings.delete

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.example.travelmarket.logic.viewmodels.bookings.DeleteBookingViewModel
import com.example.travelmarket.views.navigation.Routes
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBookingScreen(
    bookingId: Int,
    navController: NavController,
    viewModel: DeleteBookingViewModel = koinViewModel()
) {
    val deleteBookingState by viewModel.deleteBookingState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var hasNavigated by remember { mutableStateOf(false) }

    LaunchedEffect(deleteBookingState) {
        if (!hasNavigated) {
            when (val state = deleteBookingState) {
                is NetworkResult.Success -> {
                    hasNavigated = true
                    snackbarHostState.showSnackbar("✅ Reserva eliminada exitosamente")
                    navController.navigate(Routes.BookingsList.route) {
                        popUpTo(Routes.BookingDetail.route) { inclusive = true }
                    }
                }
                is NetworkResult.Error -> {
                    val errorMessage = state.message
                    if (errorMessage.contains("204") ||
                        errorMessage.contains("No Content") ||
                        errorMessage.contains("Successfully") ||
                        errorMessage.isEmpty()) {
                        hasNavigated = true
                        snackbarHostState.showSnackbar("✅ Reserva eliminada exitosamente")
                        navController.navigate(Routes.BookingsList.route) {
                            popUpTo(Routes.BookingDetail.route) { inclusive = true }
                        }
                    } else {
                        snackbarHostState.showSnackbar("❌ $errorMessage")
                    }
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
                        "Eliminar Reserva",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        enabled = deleteBookingState !is NetworkResult.Loading
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            "Volver",
                            tint = Color(0xFFDC143C)
                        )
                    }
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // ICONO GRANDE DE ADVERTENCIA
                Surface(
                    shape = RoundedCornerShape(50),
                    color = Color(0xFFDC143C).copy(alpha = 0.1f),
                    modifier = Modifier.size(120.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            Icons.Default.DeleteForever,
                            contentDescription = null,
                            tint = Color(0xFFDC143C),
                            modifier = Modifier.size(64.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // CARD DE ADVERTENCIA
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "¿Estás seguro?",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF212121)
                        )

                        Text(
                            text = "Estás a punto de eliminar esta reserva",
                            fontSize = 16.sp,
                            color = Color.Gray
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = Color(0xFFE0E0E0)
                        )

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFFFEBEE)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Warning,
                                    contentDescription = null,
                                    tint = Color(0xFFDC143C),
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(
                                    text = "Esta acción no se puede deshacer",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFFDC143C)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // ESTADO DE CARGA
                if (deleteBookingState is NetworkResult.Loading) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(4.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(48.dp),
                                color = Color(0xFFDC143C)
                            )
                            Text(
                                text = "Eliminando reserva...",
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        }
                    }
                } else {
                    // BOTONES DE ACCIÓN
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier.weight(1f),
                            enabled = deleteBookingState !is NetworkResult.Loading,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color.Gray
                            )
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text("Cancelar", fontSize = 16.sp)
                        }

                        Button(
                            onClick = { viewModel.deleteBooking(bookingId) },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFDC143C)
                            ),
                            enabled = deleteBookingState !is NetworkResult.Loading,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text("Eliminar", fontSize = 16.sp)
                        }
                    }
                }

                // MENSAJE DE ERROR
                if (deleteBookingState is NetworkResult.Error) {
                    val errorMessage = (deleteBookingState as NetworkResult.Error).message
                    if (!errorMessage.contains("204") &&
                        !errorMessage.contains("No Content") &&
                        !errorMessage.contains("Successfully") &&
                        errorMessage.isNotEmpty()) {

                        Spacer(modifier = Modifier.height(16.dp))

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFFFEBEE)
                            ),
                            elevation = CardDefaults.cardElevation(4.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Error,
                                    contentDescription = null,
                                    tint = Color(0xFFDC143C),
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(
                                    text = errorMessage,
                                    fontSize = 14.sp,
                                    color = Color(0xFFDC143C)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
