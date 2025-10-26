package com.example.travelmarket.ui.public.flight_search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelmarket.ui.core.theme.RedMain
import com.example.travelmarket.ui.core.theme.TravelMarketTheme
import com.example.travelmarket.ui.core.theme.WhitePure
import com.example.travelmarket.ui.public.home.components.AppBottomNavigation

val BlueFlight = Color(0xFF1A237E)

@Composable
fun FlightSearchScreen(
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToDestinations: () -> Unit,
    onNavigateToPackages: () -> Unit,
    onNavigateToBookings: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Buscar Vuelos", "Rutas Populares")

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
                .padding(bottom = paddingValues.calculateBottomPadding()),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                FlightHeader(onNavigateBack = onNavigateBack)
            }

            item {
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = BlueFlight,
                    contentColor = WhitePure
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = { Text(title) }
                        )
                    }
                }
            }

            item {
                when (selectedTabIndex) {
                    0 -> SearchFlightsTab()
                    1 -> PopularRoutesTab()
                }
            }
        }
    }
}

@Composable
fun FlightHeader(onNavigateBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(BlueFlight)
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        IconButton(onClick = onNavigateBack, modifier = Modifier.align(Alignment.TopStart)) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = WhitePure)
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Default.Flight, contentDescription = "Vuelos", tint = WhitePure, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Vuelos Nacionales",
                color = WhitePure,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Rutas por todo el Perú",
                color = WhitePure.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                InfoCard("Vuelos Disponibles", "10")
                InfoCard("Desde S/.", "260")
            }
        }
    }
}

@Composable
fun InfoCard(title: String, value: String) {
    Surface(
        modifier = Modifier
            .width(150.dp)
            .height(60.dp),
        shape = RoundedCornerShape(12.dp),
        color = WhitePure.copy(alpha = 0.2f)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = title, color = WhitePure.copy(alpha = 0.8f), fontSize = 12.sp)
            Text(text = value, color = WhitePure, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchFlightsTab() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Filtros de Búsqueda",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))

        FilterDropdown("Origen", listOf("Todas las ciudades", "Lima", "Cusco"))
        Spacer(modifier = Modifier.height(16.dp))
        FilterDropdown("Destino", listOf("Todas las ciudades", "Arequipa", "Iquitos"))
        Spacer(modifier = Modifier.height(16.dp))
        FilterDropdown("Aerolínea", listOf("Todas las aerolíneas", "LATAM", "Sky"))
        Spacer(modifier = Modifier.height(16.dp))
        FilterDropdown("Clase", listOf("Todas las clases", "Económica", "Premium"))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDropdown(label: String, items: List<String>) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(items[0]) }

    Text(label, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color.DarkGray)
    Spacer(modifier = Modifier.height(8.dp))
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = selectedItem,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = BlueFlight,
                unfocusedIndicatorColor = Color.LightGray,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            shape = RoundedCornerShape(8.dp)
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        selectedItem = item
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun PopularRoutesTab() {
    val routes = listOf(
        FlightRoute("Lima → Cusco", "3 vuelos disponibles", 380.0),
        FlightRoute("Lima → Arequipa", "1 vuelo disponible", 290.0),
        FlightRoute("Lima → Iquitos", "1 vuelo disponible", 520.0)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Rutas Más Populares",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))

        routes.forEach { route ->
            FlightRouteItem(route = route)
            Divider(color = Color.LightGray)
        }
    }
}

data class FlightRoute(val route: String, val availability: String, val price: Double)

@Composable
fun FlightRouteItem(route: FlightRoute) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.Flight,
            contentDescription = null,
            tint = BlueFlight,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(BlueFlight.copy(alpha = 0.1f))
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(route.route, fontWeight = FontWeight.SemiBold, color = Color.Black)
            Text(route.availability, fontSize = 12.sp, color = Color.Gray)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text("Desde", fontSize = 12.sp, color = Color.Gray)
            Text(
                text = "S/. ${route.price}",
                color = RedMain,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}
