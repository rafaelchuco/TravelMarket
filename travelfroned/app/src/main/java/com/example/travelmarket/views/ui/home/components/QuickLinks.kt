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
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Hiking
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
import com.example.travelmarket.ui.theme.RedMain

@Composable
fun QuickLinks(
    onHotelesClick: () -> Unit,
    onVuelosClick: () -> Unit,
    onActividadesClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        QuickLinkItem(
            icon = Icons.Default.Hotel,
            label = "Hoteles",
            count = "150+", // Placeholder
            onClick = onHotelesClick
        )
        QuickLinkItem(
            icon = Icons.Default.FlightTakeoff,
            label = "Vuelos",
            count = "20+", // Placeholder
            onClick = onVuelosClick
        )
        QuickLinkItem(
            icon = Icons.Default.Hiking,
            label = "Actividades",
            count = "50+",
            onClick = onActividadesClick
        )
    }
}

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
            Icon(icon, contentDescription = label, tint = RedMain, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = count,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}