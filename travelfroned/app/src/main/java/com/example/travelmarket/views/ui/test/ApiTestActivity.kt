package com.example.travelmarket.views.ui.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApiTestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "menu") {
                composable(route = "menu") { MenuScreen(navController) }

                composable(route = "destinations") {
                    DestinationsTestScreen(onBack = { navController.popBackStack() })
                }

                composable(route = "flights") {
                    FlightsTestScreen(onBack = { navController.popBackStack() })
                }

                composable(route = "hotels") {
                    HotelsTestScreen(onBack = { navController.popBackStack() })
                }

                composable(route = "inquiries") {
                    InquiriesTestScreen(onBack = { navController.popBackStack() })
                }

                composable(route = "packages") {
                    PackagesTestScreen(onBack = { navController.popBackStack() })
                }

                composable(route = "categories") {
                    CategoriesTestScreen(onBack = { navController.popBackStack() })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: androidx.navigation.NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "🧪 Prueba de las 36 APIs") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Selecciona una API para probar:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            ApiButton("🏙️ Destinations (6 APIs)") { navController.navigate("destinations") }
            ApiButton("✈️ Flights (6 APIs)") { navController.navigate("flights") }
            ApiButton("🏨 Hotels (6 APIs)") { navController.navigate("hotels") }
            ApiButton("📝 Inquiries (6 APIs)") { navController.navigate("inquiries") }
            ApiButton("📦 Packages (6 APIs)") { navController.navigate("packages") }
            ApiButton("📂 Categories (6 APIs)") { navController.navigate("categories") }
        }
    }
}

@Composable
fun ApiButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
