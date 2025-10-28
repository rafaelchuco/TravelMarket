package com.example.travelmarket.ui.public.package_detail.components

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
import androidx.compose.material3.Divider
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelmarket.ui.core.theme.RedMain

@Composable
fun ItineraryTab() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ItineraryItem(
            day = 1,
            title = "Llegada a Cusco y traslado a Aguas Calientes",
            description = "Recojo del aeropuerto, traslado a la estación de Ollantaytambo. Viaje en tren a Aguas Calientes. Noche de hotel."
        )
        ItineraryItem(
            day = 2,
            title = "Visita completa a Machu Picchu",
            description = "Desayuno en el hotel. Subida en bus a Machu Picchu. Visita guiada de 3 horas. Tiempo libre para fotos. Retorno a Aguas Calientes."
        )
        ItineraryItem(
            day = 3,
            title = "Retorno a Cusco",
            description = "Mañana libre en Aguas Calientes (opcional: visita a los baños termales). Viaje en tren de retorno a Ollantaytambo y bus a Cusco. Traslado a su hotel o al aeropuerto."
        )
    }
}

@Composable
fun ItineraryItem(
    day: Int,
    title: String,
    description: String
) {
    var expanded by remember { mutableStateOf(false) }
    val rotationAngle by animateFloatAsState(targetValue = if (expanded) 180f else 0f)

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = day.toString(),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(RedMain)
                    .padding(8.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                fontSize = 18.sp
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = "Día $day", fontSize = 14.sp, color = Color.Gray)
                Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
            }
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expandir",
                    modifier = Modifier.rotate(rotationAngle)
                )
            }
        }

        AnimatedVisibility(visible = expanded) {
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.DarkGray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 56.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Divider()
    }
}