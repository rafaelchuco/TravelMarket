package com.example.travelmarket.views.ui.promotions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.viewmodels.promotions.PromotionDetailViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromotionDetailScreen(
    promotionId: Int,
    viewModel: PromotionDetailViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(promotionId) {
        viewModel.getPromotionDetail(promotionId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Promoción") }
            )
        }
    ) { paddingValues ->
        when (uiState) {
            is NetworkResult.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is NetworkResult.Success -> {
                val promotion = (uiState as NetworkResult.Success).data
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = promotion.code,
                                style = MaterialTheme.typography.headlineMedium
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                AssistChip(
                                    onClick = { },
                                    label = {
                                        Text(
                                            if (promotion.discountType == "percentage") {
                                                "${promotion.discountValue}% OFF"
                                            } else {
                                                "$${promotion.discountValue} OFF"
                                            }
                                        )
                                    }
                                )

                                if (promotion.isActive) {
                                    AssistChip(
                                        onClick = { },
                                        label = { Text("Activo") }
                                    )
                                }
                            }
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        text = "DESCRIPCIÓN",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(text = promotion.description)

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        text = "TIPO DE DESCUENTO",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = if (promotion.discountType == "percentage") {
                            "Porcentaje"
                        } else {
                            "Monto Fijo"
                        }
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        text = "VIGENCIA",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(text = "Fecha inicio: ${promotion.startDate}")
                    Text(text = "Fecha fin: ${promotion.endDate}")

                    promotion.minPurchaseAmount?.let {
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        Text(
                            text = "COMPRA MÍNIMA",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(text = "$$it")
                    }

                    promotion.maxDiscountAmount?.let {
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        Text(
                            text = "DESCUENTO MÁXIMO",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(text = "$$it")
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        text = "ESTADO",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(text = if (promotion.isActive) "Activo" else "Inactivo")
                }
            }
            is NetworkResult.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Error: ${(uiState as NetworkResult.Error).message}",
                            color = MaterialTheme.colorScheme.error
                        )
                        Button(onClick = { viewModel.getPromotionDetail(promotionId) }) {
                            Text("Reintentar")
                        }
                    }
                }
            }
        }
    }
}