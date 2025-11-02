package com.example.travelmarket.views.ui.home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.travelmarket.logic.viewmodels.packages.PackagesListViewModel
import com.example.travelmarket.views.navigation.Routes
import com.example.travelmarket.views.ui.home.components.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    packagesViewModel: PackagesListViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val sharedPrefs = context.getSharedPreferences("travel_market_prefs", Context.MODE_PRIVATE)

    // ✅ OBTENER NOMBRE REAL DEL USUARIO
    val userName = sharedPrefs.getString("user_name", null)
        ?: sharedPrefs.getString("user_first_name", null)
        ?: "Usuario"

    val packages by packagesViewModel.packages.collectAsState()
    val loading by packagesViewModel.loading.collectAsState()

    // ✅ ESTADO DE BÚSQUEDA
    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        packagesViewModel.loadPackages()
    }

    // ✅ FILTRAR PAQUETES SEGÚN BÚSQUEDA
    val filteredPackages = if (searchQuery.isBlank()) {
        packages
    } else {
        packages.filter { pkg ->
            pkg.title.contains(searchQuery, ignoreCase = true) ||
                    pkg.description.contains(searchQuery, ignoreCase = true)
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawerContent(
                userName = userName,
                onCloseDrawer = { scope.launch { drawerState.close() } },
                onNavigateToFavorites = { navController.navigate(Routes.Profile.route) },
                onNavigateToHotels = { navController.navigate(Routes.HotelsTest.route) },
                onNavigateToFlights = { navController.navigate(Routes.FlightsTest.route) },
                onNavigateToActivities = { navController.navigate(Routes.ActivitiesList.route) },
                onNavigateToCoupons = { navController.navigate(Routes.PromotionsList.route) },
                onNavigateToMessages = { navController.navigate(Routes.InquiriesTest.route) },
                onNavigateToPeruInfo = { },
                onNavigateToSettings = { navController.navigate(Routes.Profile.route) },
                onNavigateToSupport = { },
                onNavigateToTerms = { }
            )
        }
    ) {
        Scaffold(
            bottomBar = {
                AppBottomNavigation(
                    selectedIndex = 0,
                    onInicioClick = { },
                    onDestinosClick = { navController.navigate(Routes.DestinationsTest.route) },
                    onPaquetesClick = { navController.navigate(Routes.PackagesTest.route) },
                    onReservasClick = { navController.navigate(Routes.BookingsList.route) },
                    onPerfilClick = { navController.navigate(Routes.Profile.route) }
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
            ) {
                // ✅ HEADER CON NOMBRE REAL Y BÚSQUEDA FUNCIONAL
                item {
                    HomeHeader(
                        name = userName,
                        searchQuery = searchQuery,
                        onSearchQueryChange = {
                            searchQuery = it
                            isSearching = it.isNotBlank()
                        },
                        onMenuClick = { scope.launch { drawerState.open() } }
                    )
                }

                // ✅ SI ESTÁ BUSCANDO, MOSTRAR SOLO RESULTADOS
                if (isSearching) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Resultados de búsqueda (${filteredPackages.size})",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    if (filteredPackages.isEmpty()) {
                        item {
                            Text(
                                text = "No se encontraron resultados para \"$searchQuery\"",
                                color = Color.Gray,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    } else {
                        items(filteredPackages) { pkg ->
                            DestinationCard(
                                destination = pkg,
                                onClick = { }
                            )
                        }
                    }
                } else {
                    // ✅ VISTA NORMAL CON CATEGORÍAS Y DESTINOS
                    item {
                        SectionHeader(
                            title = "Categorías",
                            onVerTodosClick = { navController.navigate(Routes.CategoriesTest.route) }
                        )
                    }

                    item {
                        CategoriesGrid(
                            onCategoryClick = { category ->
                                when(category) {
                                    "hotels" -> navController.navigate(Routes.HotelsTest.route)
                                    "flights" -> navController.navigate(Routes.FlightsTest.route)
                                    "activities" -> navController.navigate(Routes.ActivitiesList.route)
                                    "packages" -> navController.navigate(Routes.PackagesTest.route)
                                    "destinations" -> navController.navigate(Routes.DestinationsTest.route)
                                    else -> { }
                                }
                            }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        SectionHeader(
                            title = "Destinos Destacados",
                            onVerTodosClick = { navController.navigate(Routes.DestinationsTest.route) }
                        )
                    }

                    if (loading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                contentAlignment = androidx.compose.ui.Alignment.Center
                            ) {
                                CircularProgressIndicator(color = Color(0xFFE53935))
                            }
                        }
                    } else {
                        items(packages.take(3)) { pkg ->
                            DestinationCard(
                                destination = pkg,
                                onClick = { }
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}
