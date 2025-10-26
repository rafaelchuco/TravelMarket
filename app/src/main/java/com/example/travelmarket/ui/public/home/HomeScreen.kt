package com.example.travelmarket.ui.public.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.travelmarket.ui.core.theme.TravelMarketTheme
import com.example.travelmarket.ui.public.home.components.*
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    onNavigateToDestinations: () -> Unit,
    onNavigateToPackages: () -> Unit,
    onNavigateToBookings: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToCategoryPackages: (String) -> Unit,
    onNavigateToFlights: () -> Unit,
    onNavigateToCoupons: () -> Unit,
    onNavigateToDestinationList: () -> Unit,
    onNavigateToPackageList: () -> Unit,
    onNavigateToActivities: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val userName = "Juan Pérez"

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawerContent(
                userName = userName,
                onCloseDrawer = { scope.launch { drawerState.close() } },
                onNavigateToFavorites = { },
                onNavigateToHotels = { },
                onNavigateToFlights = {
                    scope.launch { drawerState.close() }
                    onNavigateToFlights()
                },
                onNavigateToActivities = {
                    scope.launch { drawerState.close() }
                    onNavigateToActivities()
                },
                onNavigateToCoupons = {
                    scope.launch { drawerState.close() }
                    onNavigateToCoupons()
                },
                onNavigateToMessages = {  },
                onNavigateToPeruInfo = { },
                onNavigateToSettings = {  },
                onNavigateToSupport = {  },
                onNavigateToTerms = {  }
            )
        }
    ) {
        Scaffold(
            bottomBar = {
                AppBottomNavigation(
                    selectedIndex = 0,
                    onInicioClick = { },
                    onDestinosClick = onNavigateToDestinations,
                    onPaquetesClick = onNavigateToPackages,
                    onReservasClick = onNavigateToBookings,
                    onPerfilClick = onNavigateToProfile
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .background(Color(0xFFF5F5F5))
                    .fillMaxSize(),
                contentPadding = paddingValues
            ) {
                item {
                    HomeHeader(
                        name = userName,
                        onMenuClick = { scope.launch { drawerState.open() } },
                        onSearchClick = { }
                    )
                }
                item {
                    QuickLinks(
                        onHotelesClick = { },
                        onVuelosClick = onNavigateToFlights,
                        onActividadesClick = onNavigateToActivities
                    )
                }
                item {
                    SectionHeader(
                        title = "Categorías",
                        onVerTodosClick = { }
                    )
                }
                item {
                    CategoryGrid(onCategoryClick = onNavigateToCategoryPackages)
                }
                item {
                    SectionHeader(
                        title = "Destinos Destacados",
                        onVerTodosClick = onNavigateToDestinationList
                    )
                }
                items(3) { index ->
                    DestinationCard(
                        imageUrl = "",
                        title = "Machu Picchu",
                        location = "Cusco - Sierra",
                        rating = 4.8,
                        onClick = { }
                    )
                }
                item {
                    SectionHeader(
                        title = "Paquetes Populares",
                        onVerTodosClick = onNavigateToPackageList
                    )
                }
                item {
                    PopularPackagesRow(onPackageClick = { })
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
