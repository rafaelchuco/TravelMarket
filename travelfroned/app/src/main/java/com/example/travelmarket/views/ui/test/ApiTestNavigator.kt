package com.example.travelmarket.views.ui.test

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ApiTestNavigator() {
    var currentScreen by remember { mutableStateOf("menu") }

    when (currentScreen) {
        "destinations" -> DestinationsTestScreen(onBack = { currentScreen = "menu" })
        "flights" -> FlightsTestScreen(onBack = { currentScreen = "menu" })
        "hotels" -> HotelsTestScreen(onBack = { currentScreen = "menu" })
        "inquiries" -> InquiriesTestScreen(onBack = { currentScreen = "menu" })
        "packages" -> PackagesTestScreen(onBack = { currentScreen = "menu" })
        "categories" -> CategoriesTestScreen(onBack = { currentScreen = "menu" })
        else -> {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
                Text("🧪 Prueba de 36 APIs", style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(24.dp))

                Button(onClick = { currentScreen = "destinations" }, modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                    Text("📍 Destinations (6 endpoints)")
                }
                Button(onClick = { currentScreen = "flights" }, modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                    Text("✈️ Flights (6 endpoints)")
                }
                Button(onClick = { currentScreen = "hotels" }, modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                    Text("🏨 Hotels (6 endpoints)")
                }
                Button(onClick = { currentScreen = "inquiries" }, modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                    Text("📧 Inquiries (6 endpoints)")
                }
                Button(onClick = { currentScreen = "packages" }, modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                    Text("📦 Packages (6 endpoints)")
                }
                Button(onClick = { currentScreen = "categories" }, modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                    Text("🏷️ Categories (6 endpoints)")
                }
            }
        }
    }
}
