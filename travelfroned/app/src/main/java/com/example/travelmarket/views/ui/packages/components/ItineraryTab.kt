package com.example.travelmarket.views.ui.packages.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelmarket.logic.domain.models.PackageItinerary

/**
 * ItineraryTab - Tab de itinerario con datos dinámicos
 *
 * @param itineraries Lista de días del itinerario desde la base de datos
 */
@Composable
fun ItineraryTab(
    itineraries: List<PackageItinerary>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (itineraries.isEmpty()) {
            Text(
                text = "No hay itinerario disponible",
                color = Color(0xFF9E9E9E),
                modifier = Modifier.padding(16.dp)
            )
        } else {
            itineraries.forEach { itinerary ->
                ItineraryItem(itinerary = itinerary)
            }
        }
    }
}

/**
 * ItineraryItem - Item individual de día del itinerario
 */
@Composable
fun ItineraryItem(
    itinerary: PackageItinerary,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val rotationAngle by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "rotation"
    )

    Column(modifier = modifier) {
        // Fila principal (siempre visible)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Círculo con número de día
            Text(
                text = itinerary.day.toString(),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFDC143C))  // RedMain
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )

            // Información del día
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Día ${itinerary.day}",
                    fontSize = 14.sp,
                    color = Color(0xFF9E9E9E)  // Gray
                )
                Text(
                    text = itinerary.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF212121)  // Black
                )
            }

            // Botón expandir
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expanded) "Contraer" else "Expandir",
                    modifier = Modifier.rotate(rotationAngle)
                )
            }
        }

        // Contenido expandible
        AnimatedVisibility(visible = expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 56.dp)
            ) {
                // Descripción principal
                Text(
                    text = itinerary.description,
                    fontSize = 14.sp,
                    color = Color(0xFF616161),  // DarkGray
                    lineHeight = 20.sp
                )

                // Comidas (si existe)
                if (!itinerary.meals.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "🍽️ Comidas: ${itinerary.meals}",
                        fontSize = 13.sp,
                        color = Color(0xFF757575)
                    )
                }

                // Alojamiento (si existe)
                if (!itinerary.accommodation.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "🏨 Alojamiento: ${itinerary.accommodation}",
                        fontSize = 13.sp,
                        color = Color(0xFF757575)
                    )
                }

                // Actividades (si existe)
                if (!itinerary.activities.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Actividades:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF424242)
                    )
                    itinerary.activities.forEach { activity ->
                        Text(
                            text = "• $activity",
                            fontSize = 13.sp,
                            color = Color(0xFF757575),
                            modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
    }
}
