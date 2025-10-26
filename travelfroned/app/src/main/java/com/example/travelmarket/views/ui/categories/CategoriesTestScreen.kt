package com.example.travelmarket.views.ui.categories

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.travelmarket.logic.viewmodels.packages.CategoriesViewModel
import com.example.travelmarket.logic.viewmodels.packages.CategoriesState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesTestScreen(
    onBack: () -> Unit,  // ✅ AGREGADO
    viewModel: CategoriesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🏷️ Categories Test") },
                navigationIcon = {  // ✅ AGREGADO
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            when (val currentState = state) {
                is CategoriesState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                        Text(
                            text = "Cargando categorías...",
                            modifier = Modifier.padding(top = 64.dp)
                        )
                    }
                }

                is CategoriesState.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "❌ Error",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(text = currentState.message)
                        Spacer(Modifier.height(16.dp))
                        Button(onClick = { viewModel.loadCategories() }) {
                            Text(text = "Reintentar")
                        }
                    }
                }

                is CategoriesState.Success -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(
                                text = "GET /packages/categories/",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(text = "Total: ${currentState.categories.size} items")
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    if (currentState.categories.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "✅ API conectada - Sin categorías en BD")
                        }
                    } else {
                        LazyColumn {
                            items(currentState.categories) { category ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                ) {
                                    Column(Modifier.padding(12.dp)) {
                                        Text(
                                            text = category.name,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        if (category.description.isNotEmpty()) {
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = category.description,
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
