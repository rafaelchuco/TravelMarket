package com.example.travelmarket.ui.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyQueriesScreen() {
    var queries by remember { mutableStateOf(getSampleQueries()) }
    
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
                    text = "Mis Consultas",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
        
        if (queries.isEmpty()) {
            // Empty state
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Chat,
                    contentDescription = "Sin consultas",
                    modifier = Modifier.size(64.dp),
                    tint = Color.Gray
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "No tienes consultas aún",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
                
                Text(
                    text = "Haz tu primera consulta sobre nuestros paquetes",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        } else {
            // Lista de consultas
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(queries) { query ->
                    QueryCard(
                        query = query,
                        onViewDetails = { /* TODO: Ver detalles de la consulta */ }
                    )
                }
            }
        }
    }
}

@Composable
fun QueryCard(
    query: Query,
    onViewDetails: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header con título y estado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = query.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
                
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = when (query.status) {
                            "Respondida" -> Color(0xFF10B981).copy(alpha = 0.1f)
                            "Pendiente" -> Color(0xFFF59E0B).copy(alpha = 0.1f)
                            else -> Color(0xFF6B7280).copy(alpha = 0.1f)
                        }
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = query.status,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = when (query.status) {
                            "Respondida" -> Color(0xFF10B981)
                            "Pendiente" -> Color(0xFFF59E0B)
                            else -> Color(0xFF6B7280)
                        },
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Descripción de la consulta
            Text(
                text = query.description,
                fontSize = 14.sp,
                color = Color.Gray,
                maxLines = 2,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Información adicional
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Paquete: ${query.packageName}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                
                Text(
                    text = query.date,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            
            if (query.status == "Respondida") {
                Spacer(modifier = Modifier.height(12.dp))
                
                Button(
                    onClick = onViewDetails,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE53E3E)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Ver Respuesta",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewQueryScreen() {
    var selectedPackage by remember { mutableStateOf("") }
    var queryTitle by remember { mutableStateOf("") }
    var queryDescription by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }
    
    val packages = listOf(
        "Machu Picchu Full Day",
        "Valle Sagrado + Ollantaytambo",
        "Montaña de Colores",
        "Laguna Humantay",
        "City Tour Cusco",
        "Salineras de Maras"
    )
    
    val categories = listOf(
        "Información general",
        "Precios y promociones",
        "Disponibilidad",
        "Requisitos y documentos",
        "Cancelaciones",
        "Otros"
    )
    
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
                    text = "Nueva Consulta",
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
            Text(
                text = "Haz tu consulta",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // Selector de paquete
            ExposedDropdownMenuBox(
                expanded = false,
                onExpandedChange = { }
            ) {
                OutlinedTextField(
                    value = selectedPackage,
                    onValueChange = { },
                    label = { Text("Seleccionar paquete") },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                
                ExposedDropdownMenu(
                    expanded = false,
                    onDismissRequest = { }
                ) {
                    packages.forEach { packageName ->
                        DropdownMenuItem(
                            text = { Text(packageName) },
                            onClick = { selectedPackage = packageName }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Categoría de consulta
            ExposedDropdownMenuBox(
                expanded = false,
                onExpandedChange = { }
            ) {
                OutlinedTextField(
                    value = selectedCategory,
                    onValueChange = { },
                    label = { Text("Categoría de consulta") },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                
                ExposedDropdownMenu(
                    expanded = false,
                    onDismissRequest = { }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category) },
                            onClick = { selectedCategory = category }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Título de la consulta
            OutlinedTextField(
                value = queryTitle,
                onValueChange = { queryTitle = it },
                label = { Text("Título de tu consulta") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Ej: ¿Incluye almuerzo?") }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Descripción detallada
            OutlinedTextField(
                value = queryDescription,
                onValueChange = { queryDescription = it },
                label = { Text("Describe tu consulta en detalle") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4,
                maxLines = 8,
                placeholder = { 
                    Text(
                        text = "Proporciona todos los detalles necesarios para que podamos ayudarte mejor...",
                        color = Color.Gray
                    )
                }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Información adicional
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Información importante",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    Text(
                        text = "• Responderemos tu consulta en un plazo máximo de 24 horas\n" +
                                "• Si tu consulta es urgente, puedes contactarnos por WhatsApp\n" +
                                "• Asegúrate de proporcionar información precisa para una mejor respuesta",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        lineHeight = 16.sp
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
                    onClick = { /* TODO: Cancelar consulta */ },
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
                    onClick = { /* TODO: Enviar consulta */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE53E3E)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    enabled = selectedPackage.isNotEmpty() && 
                             selectedCategory.isNotEmpty() && 
                             queryTitle.isNotEmpty() && 
                             queryDescription.isNotEmpty()
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Enviar",
                        modifier = Modifier.size(18.dp)
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Text(
                        text = "Enviar Consulta",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

data class Query(
    val id: String,
    val title: String,
    val description: String,
    val packageName: String,
    val status: String,
    val date: String
)

fun getSampleQueries(): List<Query> {
    return listOf(
        Query(
            id = "1",
            title = "¿Incluye almuerzo en Machu Picchu?",
            description = "Quiero saber si el paquete incluye almuerzo y qué tipo de comida ofrecen.",
            packageName = "Machu Picchu Full Day",
            status = "Respondida",
            date = "10 Dic 2024"
        ),
        Query(
            id = "2",
            title = "Disponibilidad para el 25 de diciembre",
            description = "Necesito confirmar si hay disponibilidad para el tour del Valle Sagrado el 25 de diciembre.",
            packageName = "Valle Sagrado + Ollantaytambo",
            status = "Pendiente",
            date = "12 Dic 2024"
        ),
        Query(
            id = "3",
            title = "Requisitos para Montaña de Colores",
            description = "¿Qué nivel de condición física se requiere para hacer el trekking a la Montaña de Colores?",
            packageName = "Montaña de Colores",
            status = "Respondida",
            date = "08 Dic 2024"
        )
    )
}
