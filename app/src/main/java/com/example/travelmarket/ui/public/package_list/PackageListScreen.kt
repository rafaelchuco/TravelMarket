package com.example.travelmarket.ui.public.package_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults // <-- 1. AÑADE ESTE IMPORT
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelmarket.R
import com.example.travelmarket.ui.core.theme.RedMain
import com.example.travelmarket.ui.core.theme.TravelMarketTheme
import com.example.travelmarket.ui.public.home.components.AppBottomNavigation
import com.example.travelmarket.ui.public.package_list.components.PackageListItem

data class Package(val id: String, val title: String, val location: String, val duration: String, val rating: Double, val reviews: Int, val price: Double)
val packages = listOf(
    Package("pkg1", "Tour Machu Picchu 3D/2N", "Cusco", "3 días", 4.9, 342, 850.0),
    Package("pkg2", "Amazonía 4D/3N", "Iquitos", "4 días", 4.8, 218, 720.0)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PackageListScreen(
    destinationId: String?,
    onNavigateBack: () -> Unit,
    onNavigateToDetail: (packageId: String) -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToDestinations: () -> Unit,
    onNavigateToBookings: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    val title = when (destinationId) {
        "all" -> "Paquetes Turísticos"
        null -> "Paquetes Turísticos"
        else -> "Paquetes en $destinationId"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = "Filtros")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        },
        bottomBar = {
            AppBottomNavigation(
                selectedIndex = 2,
                onInicioClick = onNavigateToHome,
                onDestinosClick = onNavigateToDestinations,
                onPaquetesClick = { },
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
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    placeholder = { Text("Buscar paquetes...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                    shape = RoundedCornerShape(50),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = RedMain,
                        unfocusedBorderColor = Color.LightGray
                    )
                )
            }
            item {
                Text(
                    text = "${packages.size} paquetes encontrados",
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            items(packages) { pkg ->
                PackageListItem(
                    pkg = pkg,
                    onVerDetallesClick = {
                        onNavigateToDetail(pkg.id)
                    }
                )
            }
        }
    }
}

