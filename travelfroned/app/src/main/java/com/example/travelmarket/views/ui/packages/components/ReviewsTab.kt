package com.example.travelmarket.views.ui.packages.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelmarket.ui.theme.RedMain

@Composable
fun ReviewsTab() {
    Column(modifier = Modifier.fillMaxWidth()) {
        ReviewSummary()
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Reseñas (2)",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        ReviewCard(
            name = "María González",
            date = "Hace 2 semanas",
            rating = 5,
            comment = "¡Experiencia inolvidable! El guía fue excelente y el tour muy bien organizado. Machu Picchu es simplemente mágico."
        )
        Spacer(modifier = Modifier.height(16.dp))
        ReviewCard(
            name = "Carlos Robles",
            date = "Hace 1 mes",
            rating = 5,
            comment = "Perfecto en todo sentido. El tren es muy cómodo y el hotel en Aguas Calientes estuvo bien ubicado."
        )
    }
}

@Composable
fun ReviewSummary() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(0.4f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "4.9",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = RedMain
                )
                Row {
                    repeat(5) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFC107),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                Text(
                    text = "342 reseñas",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(0.6f)) {
                // Placeholder data
                RatingProgressBar("Transporte", 4.8)
                RatingProgressBar("Guía", 4.9)
                RatingProgressBar("Alojamiento", 4.4)
                RatingProgressBar("Valor", 4.6)
            }
        }
    }
}

@Composable
fun RatingProgressBar(
    label: String,
    rating: Double
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = label, fontSize = 13.sp, color = Color.DarkGray, modifier = Modifier.width(80.dp))
        LinearProgressIndicator(
            progress = { (rating / 5).toFloat() },
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .clip(RoundedCornerShape(50)),
            color = Color(0xFF4CAF50),
            trackColor = Color(0xFFE0E0E0),
            strokeCap = StrokeCap.Round
        )
        Text(
            text = rating.toString(),
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.DarkGray,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun ReviewCard(
    name: String,
    date: String,
    rating: Int,
    comment: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = rating.toString(),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            Text(text = date, fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = comment,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }
    }
}