package com.example.travelmarket.ui.public.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelmarket.ui.core.theme.RedMain
import com.example.travelmarket.ui.core.theme.WhitePure

@Composable
fun AppDrawerContent(
    userName: String,
    onCloseDrawer: () -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToHotels: () -> Unit,
    onNavigateToFlights: () -> Unit,
    onNavigateToActivities: () -> Unit,
    onNavigateToCoupons: () -> Unit,
    onNavigateToMessages: () -> Unit,
    onNavigateToPeruInfo: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToSupport: () -> Unit,
    onNavigateToTerms: () -> Unit
) {
    ModalDrawerSheet {
        Column(modifier = Modifier.fillMaxHeight()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Menú",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onCloseDrawer) {
                    Icon(Icons.Default.Close, contentDescription = "Cerrar menú")
                }
            }
            Text(
                text = userName,
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 16.sp,
                color = Color.Gray
            )
            Divider(modifier = Modifier.padding(vertical = 16.dp))

            DrawerItem(icon = Icons.Default.FavoriteBorder, label = "Mis Favoritos", onClick = onNavigateToFavorites)
            DrawerItem(icon = Icons.Default.Hotel, label = "Hoteles", onClick = onNavigateToHotels)
            DrawerItem(icon = Icons.Default.Flight, label = "Vuelos Nacionales", onClick = onNavigateToFlights)
            DrawerItem(icon = Icons.Filled.Hiking, label = "Actividades", onClick = onNavigateToActivities)
            DrawerItem(icon = Icons.Filled.ConfirmationNumber, label = "Cupones y Promociones", onClick = onNavigateToCoupons)
            DrawerItem(icon = Icons.AutoMirrored.Filled.Message, label = "Mis Consultas", onClick = onNavigateToMessages)
            DrawerItem(icon = Icons.Outlined.Info, label = "Información Perú", onClick = onNavigateToPeruInfo)
            DrawerItem(icon = Icons.Default.Settings, label = "Configuración", onClick = onNavigateToSettings)
            DrawerItem(icon = Icons.AutoMirrored.Filled.HelpOutline, label = "Ayuda y Soporte", onClick = onNavigateToSupport)
            DrawerItem(icon = Icons.Default.Description, label = "Términos y Condiciones", onClick = onNavigateToTerms)


            Spacer(modifier = Modifier.weight(1f))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                color = RedMain,
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "PE Travel Marketplace",
                        color = WhitePure,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Descubre la magia del Perú",
                        color = WhitePure.copy(alpha = 0.8f),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun DrawerItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(RedMain.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = RedMain,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = Color.Gray
        )
    }
}
