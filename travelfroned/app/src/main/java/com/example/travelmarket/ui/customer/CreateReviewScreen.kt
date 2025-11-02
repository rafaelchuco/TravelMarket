package com.example.travelmarket.ui.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateReviewScreen() {
    var overallRating by remember { mutableStateOf(0) }
    var accommodationRating by remember { mutableStateOf(0) }
    var transportRating by remember { mutableStateOf(0) }
    var guideRating by remember { mutableStateOf(0) }
    var valueRating by remember { mutableStateOf(0) }
    var title by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }
    var pros by remember { mutableStateOf("") }
    var cons by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE53E3E))
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { /* TODO: Navegar hacia atrás */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Atrás",
                        tint = Color.White
                    )
                }
                
                Text(
                    text = "Crear Reseña",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Información del paquete
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Machu Picchu Full Day",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Text(
                        text = "Cusco, Perú",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    
                    Text(
                        text = "Viajaste el 15 de Diciembre, 2024",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Título de la reseña (mínimo 10 caracteres)
            Text(
                text = "Título de tu reseña",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título (mínimo 10 caracteres)") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Ej: Una experiencia increíble en Machu Picchu") }
            )
            
            if (title.isNotEmpty() && title.length < 10) {
                Text(
                    text = "El título debe tener al menos 10 caracteres",
                    fontSize = 12.sp,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Calificación general (overall_rating 1-5)
            Text(
                text = "Calificación general",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                for (i in 1..5) {
                    IconButton(
                        onClick = { overallRating = i }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Estrella $i",
                            tint = if (i <= overallRating) Color(0xFFFFD700) else Color.Gray,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Comentario (mínimo 20 caracteres)
            Text(
                text = "Comentario",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            OutlinedTextField(
                value = comment,
                onValueChange = { comment = it },
                label = { Text("Comentario (mínimo 20 caracteres)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 5,
                maxLines = 10,
                placeholder = { 
                    Text(
                        text = "Describe tu experiencia detalladamente...",
                        color = Color.Gray
                    )
                }
            )
            
            if (comment.isNotEmpty() && comment.length < 20) {
                Text(
                    text = "El comentario debe tener al menos 20 caracteres",
                    fontSize = 12.sp,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Aspectos específicos (accommodation, transport, guide, value)
            Text(
                text = "Califica aspectos específicos",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            AspectRatingCard(
                title = "Alojamiento",
                subtitle = "Comodidad y calidad",
                rating = accommodationRating,
                onRatingChange = { accommodationRating = it }
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            AspectRatingCard(
                title = "Transporte",
                subtitle = "Comodidad y puntualidad",
                rating = transportRating,
                onRatingChange = { transportRating = it }
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            AspectRatingCard(
                title = "Guía",
                subtitle = "Conocimiento y atención",
                rating = guideRating,
                onRatingChange = { guideRating = it }
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            AspectRatingCard(
                title = "Valor",
                subtitle = "Relación precio-calidad",
                rating = valueRating,
                onRatingChange = { valueRating = it }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Pros
            Text(
                text = "Puntos positivos (opcional)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            OutlinedTextField(
                value = pros,
                onValueChange = { pros = it },
                label = { Text("¿Qué te gustó más?") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                maxLines = 4,
                placeholder = { Text("Ej: El guía fue excelente, la comida muy buena...") }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Cons
            Text(
                text = "Aspectos a mejorar (opcional)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            OutlinedTextField(
                value = cons,
                onValueChange = { cons = it },
                label = { Text("¿Qué podría mejorar?") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                maxLines = 4,
                placeholder = { Text("Ej: El transporte podría ser más cómodo...") }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Opciones adicionales
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Opciones adicionales",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    
                    Text(
                        text = "Recuerda: Solo puedes crear reseñas para reservas completadas (status = completed)",
                        fontSize = 12.sp,
                        color = Color(0xFFF59E0B),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Botones de acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = { /* TODO: Cancelar reseña */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFFE53E3E)
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFE53E3E))
                    )
                ) {
                    Text(
                        text = "Cancelar",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                
                Button(
                    onClick = { /* TODO: Publicar reseña */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE53E3E)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    enabled = overallRating > 0 && 
                             accommodationRating > 0 && 
                             transportRating > 0 && 
                             guideRating > 0 && 
                             valueRating > 0 &&
                             title.length >= 10 && 
                             comment.length >= 20
                ) {
                    Text(
                        text = "Publicar Reseña",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Información adicional
            Text(
                text = "Tu reseña ayudará a otros viajeros a tomar mejores decisiones. ¡Gracias por compartir tu experiencia!",
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun AspectRatingCard(
    title: String,
    subtitle: String,
    rating: Int,
    onRatingChange: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 2.dp)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                for (i in 1..5) {
                    IconButton(
                        onClick = { onRatingChange(i) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Estrella $i",
                            tint = if (i <= rating) Color(0xFFFFD700) else Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateReviewScreenPreview() {
    CreateReviewScreen()
}

@Preview(showBackground = true)
@Composable
fun AspectRatingCardPreview() {
    AspectRatingCard(
        title = "Guía turístico",
        subtitle = "Conocimiento y atención",
        rating = 4,
        onRatingChange = { }
    )
}
