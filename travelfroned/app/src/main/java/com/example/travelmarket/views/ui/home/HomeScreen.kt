package com.example.travelmarket.views.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.travelmarket.views.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Travel Market") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Menú Principal",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // ========== CATÁLOGO ==========
            Text(
                text = "CATÁLOGO",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { navController.navigate(Routes.ActivitiesList.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver Actividades")
            }

            Button(
                onClick = { navController.navigate(Routes.DestinationsTest.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver Destinos (Test)")
            }

            Button(
                onClick = { navController.navigate(Routes.HotelsTest.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver Hoteles (Test)")
            }

            Button(
                onClick = { navController.navigate(Routes.FlightsTest.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver Vuelos (Test)")
            }

            Button(
                onClick = { navController.navigate(Routes.PackagesTest.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver Paquetes (Test)")
            }

            Button(
                onClick = { navController.navigate(Routes.CategoriesTest.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver Categorías (Test)")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ========== MI CUENTA ==========
            Text(
                text = "MI CUENTA",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { navController.navigate(Routes.Profile.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Mi Perfil")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ========== MIS RESERVAS ==========
            Text(
                text = "MIS RESERVAS",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { navController.navigate(Routes.BookingsList.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver Mis Reservas")
            }

            Button(
                onClick = { navController.navigate(Routes.MyBookings.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("My Bookings")
            }

            Button(
                onClick = { navController.navigate(Routes.InquiriesTest.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Mis Consultas (Test)")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ========== RESEÑAS Y PROMOCIONES (ALEX) ==========
            Text(
                text = "RESEÑAS Y PROMOCIONES",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )


            Button(
                onClick = { navController.navigate(Routes.MyReviews.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Mis Reseñas")
            }

            Button(
                onClick = { navController.navigate(Routes.CreateReview.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear Reseña")
            }

            Button(
                onClick = { navController.navigate(Routes.PromotionsList.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver Promociones")
            }
        }
    }
}
