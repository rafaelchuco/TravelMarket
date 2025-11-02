package com.example.travelmarket.views.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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

/**
 * AppDrawerContent - Contenido del drawer lateral de la app
 */
@OptIn(ExperimentalMaterial3Api::class)
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
            // Cabecera con título y botón cerrar
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
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar menú"
                    )
                }
            }

            // Nombre del usuario
            Text(
                text = userName,
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 16.sp,
                color = Color(0xFF757575)  // Gray
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            // Items del menú
            DrawerItem(
                icon = Icons.Default.FavoriteBorder,
                label = "Mis Favoritos",
                onClick = onNavigateToFavorites
            )
            DrawerItem(
                icon = Icons.Default.Business,  // ✅ Reemplaza Hotel
                label = "Hoteles",
                onClick = onNavigateToHotels
            )
            DrawerItem(
                icon = Icons.Default.Flight,
                label = "Vuelos Nacionales",
                onClick = onNavigateToFlights
            )
            DrawerItem(
                icon = Icons.Default.Explore,  // ✅ Reemplaza Hiking
                label = "Actividades",
                onClick = onNavigateToActivities
            )
            DrawerItem(
                icon = Icons.Default.LocalOffer,  // ✅ Reemplaza ConfirmationNumber
                label = "Cupones y Promociones",
                onClick = onNavigateToCoupons
            )
            DrawerItem(
                icon = Icons.Default.Email,  // ✅ Reemplaza Message
                label = "Mis Consultas",
                onClick = onNavigateToMessages
            )
            DrawerItem(
                icon = Icons.Default.Info,  // ✅ Usa Info filled
                label = "Información Perú",
                onClick = onNavigateToPeruInfo
            )
            DrawerItem(
                icon = Icons.Default.Settings,
                label = "Configuración",
                onClick = onNavigateToSettings
            )
            DrawerItem(
                icon = Icons.Default.Help,  // ✅ Reemplaza HelpOutline
                label = "Ayuda y Soporte",
                onClick = onNavigateToSupport
            )
            DrawerItem(
                icon = Icons.Default.Description,
                label = "Términos y Condiciones",
                onClick = onNavigateToTerms
            )

            Spacer(modifier = Modifier.weight(1f))

            // Footer con branding
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                color = Color(0xFFE91E63),  // RedMain
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "PE Travel Marketplace",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Descubre la magia del Perú",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

/**
 * DrawerItem - Item individual del drawer
 */
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
        // Círculo con ícono
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFFE91E63).copy(alpha = 0.1f)),  // RedMain alpha
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color(0xFFE91E63),  // RedMain
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Label del item
        Text(
            text = label,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )

        // Flecha derecha
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = Color(0xFF9E9E9E)  // Gray
        )
    }
}
