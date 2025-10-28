package com.example.travelmarket.ui.public.activity_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Hiking
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelmarket.ui.core.theme.TravelMarketTheme
import com.example.travelmarket.ui.public.activity_list.components.ActivityCard
import com.example.travelmarket.ui.public.home.components.AppBottomNavigation
import com.example.travelmarket.ui.public.flight_search.FilterDropdown

data class Activity(
    val id: String,
    val title: String,
    val location: String,
    val type: String,
    val difficulty: String,
    val price: Double,
    val duration: String,
    val maxGroup: Int,
    val description: String
)

val activities = listOf(
    Activity("act1", "Tour Guiado a Machu Picchu", "Machu Picchu", "Turismo", "Fácil", 120.0, "3h", 16, "Recorrido completo..."),
    Activity("act2", "Camino Inca 4D/3N", "Cusco - Machu Picchu", "Aventura", "Difícil", 650.0, "4d", 12, "Trekking clásico..."),
    Activity("act3", "Sobrevuelo Líneas de Nazca", "Nazca", "Turismo", "Fácil", 180.0, "30m", 5, "Vista aérea..."),
    Activity("act4", "Expedición Amazonas 3D/2N", "Iquitos", "Aventura", "Moderado", 420.0, "3d", 10, "Explora la selva...")
)
val featuredActivities = activities.take(4)

val GreenActivity = Color(0xFF388E3C)

@Composable
fun ActivityListScreen(
    onNavigateBack: () -> Unit,
    onNavigateToActivityDetail: (String) -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToDestinations: () -> Unit,
    onNavigateToPackages: () -> Unit,
    onNavigateToBookings: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    Scaffold(
        bottomBar = {
            AppBottomNavigation(
                selectedIndex = -1,
                onInicioClick = onNavigateToHome,
                onDestinosClick = onNavigateToDestinations,
                onPaquetesClick = onNavigateToPackages,
                onReservasClick = onNavigateToBookings,
                onPerfilClick = onNavigateToProfile
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item { ActivityHeader(totalActivities = activities.size, minPrice = 15.0) }

            item { FeaturedActivitiesSection(activities = featuredActivities, onActivityClick = onNavigateToActivityDetail) }

            item { FilterSection() }

            // Lista Vertical Completa
            item {
                Text(
                    text = "Todas las Actividades",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
                )
            }
            items(activities) { activity ->
                ActivityCard(
                    activity = activity,
                    onClick = { onNavigateToActivityDetail(activity.id) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun ActivityHeader(totalActivities: Int, minPrice: Double) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = GreenActivity,
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
            )
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.Hiking,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.2f))
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "Actividades en Perú",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Vive experiencias inolvidables",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 14.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            HeaderInfoCard("Total Actividades", totalActivities.toString())
            HeaderInfoCard("Desde S/.", minPrice.toInt().toString())
        }
    }
}

@Composable
fun HeaderInfoCard(title: String, value: String) {
    Surface(
        modifier = Modifier
            .width(150.dp)
            .height(60.dp),
        shape = RoundedCornerShape(12.dp),
        color = Color.White.copy(alpha = 0.2f)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = title, color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
            Text(text = value, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun FeaturedActivitiesSection(activities: List<Activity>, onActivityClick: (String) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 24.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Hiking, contentDescription = null, tint = Color(0xFFFFA000))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Experiencias Imperdibles",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(activities) { activity ->
                ActivityCard(
                    activity = activity,
                    onClick = { onActivityClick(activity.id) },
                    modifier = Modifier.width(180.dp)
                )
            }
        }
    }
}

@Composable
fun FilterSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Filtros",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            FilterDropdown("Departamento", listOf("Todos los departamentos", "Cusco", "Lima", "Arequipa"))
            Spacer(modifier = Modifier.height(16.dp))
            FilterDropdown("Tipo de Actividad", listOf("Todos los tipos", "Turismo", "Aventura", "Cultural"))
            Spacer(modifier = Modifier.height(16.dp))
            FilterDropdown("Dificultad (importante para zonas de altura)", listOf("Todos los niveles", "Fácil", "Moderado", "Difícil"))
        }
    }
}

