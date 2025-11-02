package com.example.travelmarket.views.ui.home.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

private data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

@Composable
fun AppBottomNavigation(
    selectedIndex: Int,
    onInicioClick: () -> Unit,
    onDestinosClick: () -> Unit,
    onPaquetesClick: () -> Unit,
    onReservasClick: () -> Unit,
    onPerfilClick: () -> Unit
) {
    val items = listOf(
        BottomNavItem("Inicio", Icons.Default.Home, onInicioClick),
        BottomNavItem("Destinos", Icons.Default.LocationOn, onDestinosClick),
        BottomNavItem("Paquetes", Icons.Default.ShoppingCart, onPaquetesClick),
        BottomNavItem("Reservas", Icons.Default.DateRange, onReservasClick),
        BottomNavItem("Perfil", Icons.Default.Person, onPerfilClick)
    )

    NavigationBar(
        containerColor = Color.White
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = (selectedIndex == index),
                onClick = item.onClick,
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = { Text(text = item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFE91E63),     // RedMain
                    selectedTextColor = Color(0xFFE91E63),     // RedMain
                    indicatorColor = Color(0xFFFFEBF0),        // ✅ CAMBIADO: indicatorColor
                    unselectedIconColor = Color(0xFF757575),   // Gray
                    unselectedTextColor = Color(0xFF757575)    // Gray
                )
            )
        }
    }
}
