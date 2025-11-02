package com.example.travelmarket.views.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * QuickLinks - Enlaces rápidos a secciones principales
 *
 * @param hotelsCount Cantidad de hoteles disponibles
 * @param flightsCount Cantidad de vuelos disponibles
 * @param activitiesCount Cantidad de actividades disponibles
 * @param onHotelesClick Callback para hoteles
 * @param onVuelosClick Callback para vuelos
 * @param onActividadesClick Callback para actividades
 */
@Composable
fun QuickLinks(
    hotelsCount: Int = 0,
    flightsCount: Int = 0,
    activitiesCount: Int = 0,
    onHotelesClick: () -> Unit,
    onVuelosClick: () -> Unit,
    onActividadesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        QuickLinkItem(
            icon = Icons.Default.Business,
            label = "Hoteles",
            count = if (hotelsCount > 0) "$hotelsCount+" else "Próx.",
            onClick = onHotelesClick
        )
        QuickLinkItem(
            icon = Icons.Default.FlightTakeoff,
            label = "Vuelos",
            count = if (flightsCount > 0) "$flightsCount+" else "Próx.",
            onClick = onVuelosClick
        )
        QuickLinkItem(
            icon = Icons.Default.Explore,
            label = "Actividades",
            count = if (activitiesCount > 0) "$activitiesCount+" else "Próx.",
            onClick = onActividadesClick
        )
    }
}

/**
 * QuickLinkItem - Item individual de enlace rápido
 */
@Composable
private fun QuickLinkItem(
    icon: ImageVector,
    label: String,
    count: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.size(width = 100.dp, height = 100.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color(0xFFE91E63),  // RedMain
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF212121)
            )
            Text(
                text = count,
                fontSize = 12.sp,
                color = Color(0xFF9E9E9E)  // Gray
            )
        }
    }
}
