package com.example.travelmarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.travelmarket.ui.theme.TravelMarketTheme
import com.example.travelmarket.views.navigation.NavGraph
import com.example.travelmarket.views.navigation.Routes

// @AndroidEntryPoint // <-- BORRA ESTA LÍNEA
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TravelMarketTheme {
                val navController = rememberNavController()
                // Scaffold ya no es necesario aquí si NavGraph lo maneja
                // o si las pantallas individuales lo usan.
                // Si ninguna pantalla usa Scaffold, puedes quitarlo.
                // Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                NavGraph(
                    navController = navController,
                    // Usa la pantalla de bienvenida como inicio
                    startDestination = Routes.Welcome.route,
                    // El padding usualmente se maneja dentro de las pantallas
                    // modifier = Modifier.padding(innerPadding)
                    modifier = Modifier.fillMaxSize()
                )
                // }
            }
        }
    }
}