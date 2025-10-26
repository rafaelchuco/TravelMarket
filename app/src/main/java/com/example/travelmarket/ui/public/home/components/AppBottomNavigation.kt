package com.example.travelmarket.ui.public.home.components

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
import com.example.travelmarket.ui.core.theme.RedMain

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
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = RedMain,
                    selectedTextColor = RedMain,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray
                )
            )
        }
    }
}