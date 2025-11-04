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
import androidx.compose.material.icons.filled.LocationOn
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
fun MyQueriesScreen(
    navController: androidx.navigation.NavController? = null
) {
    var queries by remember { mutableStateOf(getSampleQueries()) }
    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf("Próximas", "Pasadas", "Canceladas")
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header con botón Nueva
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE53E3E))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { navController?.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Atrás",
                            tint = Color.White
                        )
                    }
                    
                    Column {
                        Text(
                            text = "Mis Consultas",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                        Text(
                            text = "Gestiona tus consultas al soporte",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.9f),
                            modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                        )
                    }
                }
                
                // Botón Nueva Consulta
                OutlinedButton(
                    onClick = { navController?.navigate(com.example.travelmarket.views.navigation.Routes.NewQuery.route) },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = androidx.compose.ui.graphics.SolidColor(Color.White)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Nueva",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Nueva", fontSize = 14.sp)
                }
            }
        }
        
        // Tabs
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.White,
            contentColor = Color(0xFFE53E3E)
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text("$title (${getQueriesByStatus(queries, index).size})") }
                )
            }
        }
        
        val filteredQueries = getQueriesByStatus(queries, selectedTab)
        
        if (filteredQueries.isEmpty()) {
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
                items(filteredQueries) { query ->
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
                
                // Estado badge
                val statusColor = when (query.status) {
                    "Respondida" -> Color(0xFF10B981)
                    "En Proceso" -> Color(0xFFF59E0B)
                    else -> Color(0xFF6B7280)
                }
                
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = statusColor.copy(alpha = 0.1f)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = query.status,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = statusColor,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Fecha
            Text(
                text = query.date,
                fontSize = 12.sp,
                color = Color.Gray
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Paquete relacionado (si existe)
            if (query.packageName.isNotEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFFE53E3E)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = query.packageName,
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
            
            // Consulta del usuario
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(
                        text = query.description,
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }
            }
            
            // Respuesta del equipo (si existe)
            if (query.status == "Respondida") {
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFECFDF5)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            text = "Respuesta del equipo:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF10B981),
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = "Hola Juan, si incluye entrada a Machu Picchu. Para grupos de +5 personas hay 10% de descuento. ¿Necesitas más información?",
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                        Text(
                            text = "Respondido: 19 de octubre de 2024, 02:22 p.m.",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            } else if (query.status == "En Proceso") {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFFF59E0B)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Esperando respuesta del equipo",
                        fontSize = 14.sp,
                        color = Color(0xFFF59E0B),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewQueryScreen(
    navController: androidx.navigation.NavController? = null
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var selectedPackage by remember { mutableStateOf<String?>(null) }
    var subject by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var isPackageExpanded by remember { mutableStateOf(false) }
    
    val packages = listOf(
        "Tour Machu Picchu 3D/2N",
        "Valle Sagrado + Ollantaytambo",
        "Montaña de Colores",
        "Laguna Humantay",
        "City Tour Cusco",
        "Salineras de Maras"
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
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { navController?.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Atrás",
                            tint = Color.White
                        )
                    }
                    
                    Text(
                        text = "Nueva consulta",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Text(
                    text = "Enviános tu pregunta",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.9f),
                    modifier = Modifier.padding(start = 48.dp, top = 4.dp)
                )
            }
        }
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Nombre Completo
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("Nombre Completo") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("tu@email.com") }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Teléfono (Perú)
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("+51 000-000-000") }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Paquete Relacionado (Opcional)
            ExposedDropdownMenuBox(
                expanded = isPackageExpanded,
                onExpandedChange = { isPackageExpanded = it }
            ) {
                OutlinedTextField(
                    value = selectedPackage ?: "",
                    onValueChange = { },
                    label = { Text("Paquete Relacionado (Opcional)") },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isPackageExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                
                ExposedDropdownMenu(
                    expanded = isPackageExpanded,
                    onDismissRequest = { isPackageExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Ninguno") },
                        onClick = { 
                            selectedPackage = null
                            isPackageExpanded = false
                        }
                    )
                    packages.forEach { packageName ->
                        DropdownMenuItem(
                            text = { Text(packageName) },
                            onClick = { 
                                selectedPackage = packageName
                                isPackageExpanded = false
                            }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Asunto
            OutlinedTextField(
                value = subject,
                onValueChange = { subject = it },
                label = { Text("Asunto") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Ej: Consulta sobre disponibilidad") }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Mensaje
            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Mensaje") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 5,
                maxLines = 10,
                placeholder = { Text("Describe tu consulta...") }
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Botón Enviar
            Button(
                onClick = { /* TODO: Enviar consulta */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE53E3E)
                ),
                shape = RoundedCornerShape(8.dp),
                enabled = fullName.isNotEmpty() && 
                         email.isNotEmpty() && 
                         phone.isNotEmpty() &&
                         subject.isNotEmpty() && 
                         message.isNotEmpty()
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
            title = "Consulta sobre paquete a Cusco",
            description = "Tu consulta: ¿El paquete incluye entrada a Machu Picchu? ¿Hay descuentos para grupos?",
            packageName = "Tour Machu Picchu 3D/2N",
            status = "Respondida",
            date = "18 de Octubre de 2024, 10:30 a. m."
        ),
        Query(
            id = "2",
            title = "Cambio de fecha de reserva",
            description = "Tu consulta: Necesito cambiar la fecha de mi reserva PERU12345678 del 25 de noviembre al 2 de diciembre",
            packageName = "",
            status = "En Proceso",
            date = "18 de Octubre de 2024, 10:30 a. m."
        )
    )
}

fun getQueriesByStatus(queries: List<Query>, tabIndex: Int): List<Query> {
    return when (tabIndex) {
        0 -> queries.filter { it.status == "En Proceso" || it.status == "Pendiente" }
        1 -> queries.filter { it.status == "Respondida" }
        2 -> queries.filter { it.status == "Cancelada" }
        else -> emptyList()
    }
}
