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
import com.example.travelmarket.logic.domain.models.Review
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * ReviewsTab - Tab de reseñas con datos dinámicos
 *
 * @param reviews Lista de reseñas desde la base de datos
 * @param averageRating Rating promedio general
 * @param totalReviews Total de reseñas
 */
@Composable
fun ReviewsTab(
    reviews: List<Review>,
    averageRating: Double = 0.0,
    totalReviews: Int = 0,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        ReviewSummary(
            averageRating = averageRating,
            totalReviews = totalReviews,
            reviews = reviews
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Reseñas ($totalReviews)",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF212121)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (reviews.isEmpty()) {
            Text(
                text = "No hay reseñas disponibles",
                fontSize = 14.sp,
                color = Color(0xFF9E9E9E),
                modifier = Modifier.padding(16.dp)
            )
        } else {
            reviews.forEach { review ->
                ReviewCard(review = review)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

/**
 * ReviewSummary - Resumen de ratings
 */
@Composable
fun ReviewSummary(
    averageRating: Double,
    totalReviews: Int,
    reviews: List<Review>,
    modifier: Modifier = Modifier
) {
    // Calcular promedios de ratings específicos
    val avgAccommodation = reviews.mapNotNull { it.accommodationRating }.average().takeIf { !it.isNaN() } ?: 0.0
    val avgTransport = reviews.mapNotNull { it.transportRating }.average().takeIf { !it.isNaN() } ?: 0.0
    val avgGuide = reviews.mapNotNull { it.guideRating }.average().takeIf { !it.isNaN() } ?: 0.0
    val avgValue = reviews.mapNotNull { it.valueRating }.average().takeIf { !it.isNaN() } ?: 0.0

    Card(
        modifier = modifier.fillMaxWidth(),
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
            // Rating general
            Column(
                modifier = Modifier.weight(0.4f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = String.format("%.1f", averageRating),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFDC143C) // RedMain
                )
                Row {
                    repeat(5) { index ->
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = if (index < averageRating.toInt()) Color(0xFFFFC107) else Color(0xFFE0E0E0),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                Text(
                    text = "$totalReviews reseñas",
                    fontSize = 12.sp,
                    color = Color(0xFF9E9E9E)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Desglose de ratings
            Column(modifier = Modifier.weight(0.6f)) {
                if (avgAccommodation > 0) {
                    RatingProgressBar("Alojamiento", avgAccommodation)
                }
                if (avgTransport > 0) {
                    RatingProgressBar("Transporte", avgTransport)
                }
                if (avgGuide > 0) {
                    RatingProgressBar("Guía", avgGuide)
                }
                if (avgValue > 0) {
                    RatingProgressBar("Valor", avgValue)
                }
            }
        }
    }
}

/**
 * RatingProgressBar - Barra de progreso para rating específico
 */
@Composable
fun RatingProgressBar(
    label: String,
    rating: Double,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = Color(0xFF616161),
            modifier = Modifier.width(80.dp)
        )
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
            text = String.format("%.1f", rating),
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF616161),
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

/**
 * ReviewCard - Tarjeta de reseña individual
 */
@Composable
fun ReviewCard(
    review: Review,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header con nombre y rating
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = review.customerName ?: "Usuario",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121),
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Rating",
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = review.overallRating.toString(),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121)
                )
            }

            // Fecha
            Text(
                text = formatDate(review.createdAt),
                fontSize = 12.sp,
                color = Color(0xFF9E9E9E)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Título (si existe)
            if (!review.title.isNullOrEmpty()) {
                Text(
                    text = review.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF424242)
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            // Comentario
            if (!review.comment.isNullOrEmpty()) {
                Text(
                    text = review.comment,
                    fontSize = 14.sp,
                    color = Color(0xFF616161),
                    lineHeight = 20.sp
                )
            }

            // Badge de verificado
            if (review.isVerified) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "✓ Compra verificada",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF4CAF50)
                )
            }
        }
    }
}

/**
 * Formatear fecha de createdAt
 */
private fun formatDate(dateString: String?): String {
    if (dateString.isNullOrEmpty()) return "Fecha no disponible"
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        outputFormat.format(date ?: Date())
    } catch (e: Exception) {
        dateString
    }
}
