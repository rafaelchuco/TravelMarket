package com.example.travelmarket.views.ui.destinations.list.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.travelmarket.logic.domain.models.Destination

/**
 * DestinationListItem - Componente de lista para destinos
 *
 * @param destination El destino a mostrar
 * @param onClick Callback cuando se hace clic
 * @param modifier Modificador opcional
 */
@Composable
fun DestinationListItem(
    destination: Destination,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ====== IMAGEN DEL DESTINO ======
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                // 🔥🔥🔥 TODO: REEMPLAZAR CON IMAGEN DEL BACKEND DJANGO 🔥🔥🔥
                // Cuando Django tenga imageUrl, usar:
                // model = destination.imageUrl.ifEmpty { getDestinationPlaceholder(destination.id.toInt()) }
                AsyncImage(
                    model = if (destination.imageUrl.isNotEmpty()) {
                        destination.imageUrl
                    } else {
                        getDestinationPlaceholder(destination.id.toInt())
                    },
                    contentDescription = destination.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Badge de país
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(6.dp),
                    color = Color.Black.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = destination.country,
                        color = Color.White,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // ====== INFORMACIÓN DEL DESTINO ======
            Column(modifier = Modifier.weight(1f)) {
                // Nombre del destino
                Text(
                    text = destination.name,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121),  // ✅ NEGRO OSCURO CONSISTENTE
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Ubicación (País, Continente)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Ubicación",
                        tint = Color.Gray,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${destination.country}, ${destination.continent}",
                        fontSize = 13.sp,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Descripción corta
                Text(
                    text = destination.shortDescription.ifEmpty {
                        destination.description
                    },
                    fontSize = 13.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Fila inferior: Rating + Badge Popular
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Rating (placeholder - puedes conectarlo con datos reales)
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "4.8",  // TODO: Agregar rating al modelo
                        fontSize = 13.sp,
                        color = Color(0xFF212121),  // ✅ NEGRO OSCURO
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.weight(1f)
                    )

                    // Badge de destino popular
                    if (destination.isPopular) {
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = Color(0xFFE91E63).copy(alpha = 0.1f)
                        ) {
                            Text(
                                text = "Popular",
                                fontSize = 11.sp,
                                color = Color(0xFFE91E63),
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    } else {
                        Text(
                            text = destination.bestSeason,
                            fontSize = 13.sp,
                            color = Color(0xFFE91E63),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

// 🔥 FUNCIÓN TEMPORAL - ELIMINAR cuando Django tenga imageUrl
private fun getDestinationPlaceholder(destinationId: Int): String {
    val worldDestinations = listOf(
        "https://images.unsplash.com/photo-1502602898657-3e91760cbb34?w=400&q=80", // París
        "https://images.unsplash.com/photo-1513635269975-59663e0ac1ad?w=400&q=80", // Londres
        "https://images.unsplash.com/photo-1534351590666-13e3e96b5017?w=400&q=80", // Santorini
        "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf?w=400&q=80", // Nueva York
        "https://images.unsplash.com/photo-1514565131-fce0801e5785?w=400&q=80", // Dubái
        "https://images.unsplash.com/photo-1493976040374-85c8e12f0c0e?w=400&q=80", // Seúl
        "https://images.unsplash.com/photo-1526481280693-3bfa7568e0f3?w=400&q=80", // Tokio
        "https://images.unsplash.com/photo-1523906834658-6e24ef2386f9?w=400&q=80", // México
        "https://images.unsplash.com/photo-1516738901171-8eb4fc13bd20?w=400&q=80", // Río
        "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=400&q=80"  // Islandia
    )
    return worldDestinations[destinationId % worldDestinations.size]
}
