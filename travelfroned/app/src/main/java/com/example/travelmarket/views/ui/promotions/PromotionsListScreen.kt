package com.example.travelmarket.views.ui.promotions

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Promotion
import com.example.travelmarket.logic.viewmodels.promotions.PromotionsListViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromotionsListScreen(
    navController: NavController,
    viewModel: PromotionsListViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.loadPromotions()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cupones y promociones", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF0F2F5))
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is NetworkResult.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is NetworkResult.Error -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Error: ${state.message}")
                    }
                }

                is NetworkResult.Success -> {
                    val promotions = state.data.getItems()
                    val activePromotions = promotions.filter { it.isActive }
                    val expiredPromotions = promotions.filter { !it.isActive }

                    HeaderCard(activePromotions.size)
                    Tabs(selectedTabIndex, activePromotions.size, expiredPromotions.size) { index ->
                        selectedTabIndex = index
                    }

                    if (selectedTabIndex == 0) {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(activePromotions) { promotion ->
                                CouponCard(promotion)
                            }
                        }
                    } else {
                        if (expiredPromotions.isEmpty()) {
                            HowToUseCouponsCard()
                        } else {
                            LazyColumn(
                                contentPadding = PaddingValues(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(expiredPromotions) { promotion ->
                                    CouponCard(promotion)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderCard(activeCount: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Box(
            modifier = Modifier.background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFFFFD12C), Color(0xFFFFB800))
                )
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ConfirmationNumber,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = Color.White.copy(alpha = 0.8f)
                )
                Spacer(Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Cupones",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "$activeCount cupones activos",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }
    }
}

@Composable
fun Tabs(selectedTabIndex: Int, activeCount: Int, expiredCount: Int, onTabSelected: (Int) -> Unit) {
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
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Tab(
            selected = selectedTabIndex == 0,
            onClick = { onTabSelected(0) },
            text = { Text("Activos ($activeCount)", fontWeight = FontWeight.Bold) }
        )
        Tab(
            selected = selectedTabIndex == 1,
            onClick = { onTabSelected(1) },
            text = { Text("Expirados ($expiredCount)", fontWeight = FontWeight.Bold) }
        )
    }
}

@Composable
fun CouponCard(promotion: Promotion) {
    val clipboardManager = LocalClipboardManager.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 20.dp)) {
            Row(verticalAlignment = Alignment.Top) {
                Icon(
                    imageVector = Icons.Default.ConfirmationNumber,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color(0xFFFFB800)
                )
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = promotion.description, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(text = "Celebra el Perú con descuentos especiales", fontSize = 14.sp, color = Color.Gray)
                }
                Spacer(Modifier.width(12.dp))
                Surface(
                    color = if (promotion.isActive) Color(0xFF34A853).copy(alpha = 0.9f) else Color.Gray,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = if (promotion.discountType == "percentage") "${promotion.discountValue.toFloat().toInt()}% OFF" else "$${promotion.discountValue.toFloat().toInt()} OFF",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F2F5))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Código de cupón", fontSize = 12.sp, color = Color.Gray)
                        Text(promotion.code, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                    }
                    IconButton(onClick = { clipboardManager.setText(AnnotatedString(promotion.code)) }) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "Copiar código")
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.DateRange, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Válido hasta ${promotion.endDate}", fontSize = 13.sp, color = Color.Gray)
                }
                promotion.minPurchaseAmount?.let {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Compra mínima: S/. ${it.toFloat().toInt()}", fontSize = 13.sp, color = Color.Gray)
                    }
                }
            }
            Spacer(Modifier.height(20.dp))
            Button(
                onClick = { /* Lógica para usar el cupón */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = if (promotion.isActive) Color(0xFFDC143C) else Color.Gray),
                enabled = promotion.isActive
            ) {
                Text("Usar cupón", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun HowToUseCouponsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF1F1)),
        border = BorderStroke(1.dp, Color(0xFFDC143C))
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = null,
                    tint = Color(0xFFDC143C)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Cómo usar los cupones",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFFDC143C)
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("• Copia el código del cupón", fontSize = 14.sp, color = Color.DarkGray)
                Text("• Selecciona tu paquete turístico", fontSize = 14.sp, color = Color.DarkGray)
                Text("• Pégalo en el campo de cupón al reservar", fontSize = 14.sp, color = Color.DarkGray)
                Text("• El descuento se aplicará automáticamente", fontSize = 14.sp, color = Color.DarkGray)
            }
        }
    }
}
