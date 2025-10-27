package com.example.travelmarket.views.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.travelmarket.ui.theme.TravelMarketTheme
import com.example.travelmarket.views.ui.home.components.*
import com.example.travelmarket.views.navigation.Routes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController
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
                    navController.navigate(Routes.FlightSearch.route)
                },
                onNavigateToActivities = {
                    scope.launch { drawerState.close() }
                    navController.navigate(Routes.ActivitiesList.route)
                },
                onNavigateToCoupons = {
                    scope.launch { drawerState.close() }
                    navController.navigate(Routes.CouponList.route)
                },
                onNavigateToMessages = {  },
                onNavigateToPeruInfo = {  },
                onNavigateToSettings = {  },
                onNavigateToSupport = { },
                onNavigateToTerms = {  }
            )
        }
    ) {
        Scaffold(
            bottomBar = {
                AppBottomNavigation(
                    selectedIndex = 0,
                    onInicioClick = { },
                    onDestinosClick = { navController.navigate(Routes.DestinationList.route) },
                    onPaquetesClick = { navController.navigate(Routes.PackageList.createRoute("all")) },
                    onReservasClick = { navController.navigate(Routes.BookingsList.route) },
                    onPerfilClick = { navController.navigate(Routes.Profile.route) }
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
                        onSearchClick = {  }
                    )
                }
                item {
                    QuickLinks(
                        onHotelesClick = { },
                        onVuelosClick = { navController.navigate(Routes.FlightSearch.route) },
                        onActividadesClick = { navController.navigate(Routes.ActivitiesList.route) }
                    )
                }
                item {
                    SectionHeader(
                        title = "Categorías",
                        onVerTodosClick = { }
                    )
                }
                item {
                    CategoryGrid(onCategoryClick = { categoryId ->
                        navController.navigate(Routes.PackageList.createRoute(categoryId))
                    })
                }
                item {
                    SectionHeader(
                        title = "Destinos Destacados",
                        onVerTodosClick = { navController.navigate(Routes.DestinationList.route) }
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
                        onVerTodosClick = { navController.navigate(Routes.PackageList.createRoute("all")) }
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
