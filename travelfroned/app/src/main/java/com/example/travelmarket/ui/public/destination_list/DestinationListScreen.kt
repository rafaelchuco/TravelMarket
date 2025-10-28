package com.example.travelmarket.ui.public.destination_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelmarket.ui.core.theme.RedMain
import com.example.travelmarket.ui.core.theme.TravelMarketTheme
import com.example.travelmarket.ui.public.destination_list.components.DestinationListItem
import com.example.travelmarket.ui.public.home.components.AppBottomNavigation

data class Destination(val id: String, val name: String, val region: String, val desc: String, val rating: Double, val packages: Int)
val destinations = listOf(
    Destination("1", "Machu Picchu", "Sierra", "Ciudadela inca del siglo XV", 4.9, 28),
    Destination("2", "Centro Histórico de Lima", "Costa", "Patrimonio de la Humanidad", 4.7, 15),
    Destination("3", "Amazonía Peruana", "Selva", "Biodiversidad incomparable", 4.8, 22),
    Destination("4", "Ciudad Blanca", "Sierra", "Arquitectura colonial única", 4.8, 18),
    Destination("5", "Playas del Norte", "Costa", "Playas paradisíacas", 4.6, 12)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DestinationListScreen(
    onNavigateToPackages: (destinationId: String) -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToPackageList: () -> Unit,
    onNavigateToBookings: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedChip by remember { mutableStateOf("Todos") }
    val chips = listOf("Todos", "Costa", "Sierra", "Selva")

    Scaffold(
        bottomBar = {
            AppBottomNavigation(
                selectedIndex = 1,
                onInicioClick = onNavigateToHome,
                onDestinosClick = {  },
                onPaquetesClick = onNavigateToPackageList,
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
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                    Text(
                        text = "Explorar Destinos",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                        placeholder = { Text("Buscar destinos o departamentos...") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                        shape = RoundedCornerShape(50),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = RedMain,
                            unfocusedIndicatorColor = Color.LightGray
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        chips.forEach { chip ->
                            FilterChip(
                                selected = (selectedChip == chip),
                                onClick = { selectedChip = chip },
                                label = { Text(chip) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = RedMain,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }
            }

            items(destinations) { destination ->
                DestinationListItem(
                    destination = destination,
                    onClick = {
                        onNavigateToPackages(destination.id)
                    }
                )
            }
        }
    }
}

