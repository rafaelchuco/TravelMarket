package com.example.travelmarket.views.ui.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.travelmarket.logic.domain.models.Inquiry
import com.example.travelmarket.logic.viewmodels.inquiries.InquiriesListState
import com.example.travelmarket.logic.viewmodels.inquiries.InquiriesListViewModel
import com.example.travelmarket.views.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InquiriesTestScreen(
    onBack: () -> Unit,
    navController: NavController,
    viewModel: InquiriesListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF0F2F5))
                .padding(it) // ✅ ADVERTENCIA CORREGIDA
        ) {
            // HEADER ROJO
            InquiriesHeader(onNewInquiry = { navController.navigate(Routes.CreateInquiry.route) })

            when (val currentState = state) {
                is InquiriesListState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is InquiriesListState.Error -> {
                    ErrorView(message = currentState.message, onRetry = { viewModel.loadInquiries() })
                }
                is InquiriesListState.Success -> {
                    val (pending, answered, closed) = currentState.inquiries.groupBy { it.status.lowercase() }.let {
                        Triple(
                            it["pending"] ?: emptyList(),
                            it["answered"] ?: emptyList(),
                            it["closed"] ?: emptyList()
                        )
                    }

                    // PESTAÑAS
                    InquiryTabs(selectedTabIndex, pending.size, answered.size, closed.size) { index ->
                        selectedTabIndex = index
                    }

                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        when (selectedTabIndex) {
                            0 -> items(pending) { inquiry -> InquiryCard(inquiry) }
                            1 -> items(answered) { inquiry -> InquiryCard(inquiry) }
                            2 -> items(closed) { inquiry -> InquiryCard(inquiry) }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InquiriesHeader(onNewInquiry: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFDC143C))
            .statusBarsPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text("Mis Consultas", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text("Gestiona tus consultas al soporte", fontSize = 16.sp, color = Color.White.copy(alpha = 0.9f))
        }
        Button(
            onClick = onNewInquiry,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Nueva consulta", tint = Color(0xFFDC143C))
                Text("Nueva", color = Color(0xFFDC143C), fontWeight = FontWeight.Bold, softWrap = false)
            }
        }
    }
}

@Composable
fun InquiryTabs(selectedTabIndex: Int, upcomingCount: Int, pastCount: Int, canceledCount: Int, onTabSelected: (Int) -> Unit) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = Color.Transparent,
        contentColor = Color(0xFFDC143C),
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                height = 3.dp,
                color = Color(0xFFDC143C)
            )
        },
        divider = {},
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Tab(selected = selectedTabIndex == 0, onClick = { onTabSelected(0) }, text = { Text("Próximas ($upcomingCount)") })
        Tab(selected = selectedTabIndex == 1, onClick = { onTabSelected(1) }, text = { Text("Pasadas ($pastCount)") })
        Tab(selected = selectedTabIndex == 2, onClick = { onTabSelected(2) }, text = { Text("Canceladas ($canceledCount)") })
    }
}

@Composable
private fun InquiryCard(inquiry: Inquiry) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(inquiry.subject, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                StatusBadge(inquiry.status)
            }
            Text(inquiry.createdAt, fontSize = 12.sp, color = Color.Gray)
            Spacer(Modifier.height(8.dp))
            inquiry.packageId?.let {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CardTravel, contentDescription = "Paquete", modifier = Modifier.size(16.dp), tint = Color.Gray)
                    Spacer(Modifier.width(8.dp))
                    Text(it.toString(), fontSize = 14.sp, color = Color.DarkGray)
                }
                Spacer(Modifier.height(12.dp))
            }

            // CONSULTA
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F2F5))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Tu consulta:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(inquiry.message, fontSize = 14.sp)
                }
            }

            // RESPUESTA
            inquiry.adminResponse?.let {
                Spacer(Modifier.height(12.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CheckCircle, contentDescription = "Respuesta", modifier = Modifier.size(16.dp), tint = Color(0xFF34A853))
                            Spacer(Modifier.width(8.dp))
                            Text("Respuesta del equipo:", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF34A853))
                        }
                        Text(it, fontSize = 14.sp)
                        Text("Respondido: ${inquiry.createdAt}", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.align(Alignment.End))
                    }
                }
            } ?: run {
                Spacer(Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.HourglassEmpty, contentDescription = "Pendiente", modifier = Modifier.size(16.dp), tint = Color.Gray)
                    Spacer(Modifier.width(8.dp))
                    Text("Esperando respuesta del equipo", fontSize = 13.sp, color = Color.Gray)
                }
            }
        }
    }
}

@Composable
private fun StatusBadge(status: String) {
    val (backgroundColor, textColor) = when (status.lowercase()) {
        "pending" -> Color(0xFFFFF3E0) to Color(0xFFFB8C00)
        "answered" -> Color(0xFFE8F5E9) to Color(0xFF34A853)
        "closed" -> Color(0xFFF3E5F5) to Color(0xFF8E24AA)
        else -> Color.LightGray to Color.Black
    }

    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = status,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelMedium,
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ErrorView(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFDC143C), modifier = Modifier.size(64.dp))
        Spacer(Modifier.height(16.dp))
        Text("Error al cargar consultas", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Text(message, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        Spacer(Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Reintentar")
        }
    }
}
