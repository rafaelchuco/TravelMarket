package com.example.travelmarket.views.ui.flights

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelmarket.ui.theme.RedMain
import com.example.travelmarket.ui.theme.TravelMarketTheme
import com.example.travelmarket.ui.theme.WhitePure
import com.example.travelmarket.logic.viewmodels.flights.FlightsListViewModel
import org.koin.androidx.compose.koinViewModel

data class Coupon(
    val id: String,
    val title: String,
    val description: String,
    val discount: String,
    val code: String,
    val expiry: String,
    val minPurchase: String,
    val active: Boolean,
    val color: Color
)

val coupons = listOf(
    Coupon("1", "Fiestas Patrias", "Celebra el Perú con descuentos especiales", "15% OFF", "FIESTASP2024", "30 de julio de 2024", "S/. 500", true, Color(0xFFFFF9E0)),
    Coupon("2", "Verano Perú", "Descuento para tus vacaciones de verano", "10% OFF", "VERANO2024", "30 de marzo de 2025", "S/. 300", true, Color(0xFFFFF9E0)),
    Coupon("3", "Primera Compra", "Bienvenido a Travel Marketplace", "20% OFF", "PRIMERACOMPRA", "30 de diciembre de 2025", "S/. 400", true, Color(0xFFE8F5E9)),
    Coupon("4", "Semana Santa", "Viaja en Semana Santa con descuento", "12% OFF", "SEMANASANTA", "19 de abril de 2025", "S/. 400", true, Color(0xFFFFF9E0)),
    Coupon("5", "Aniversario", "Celebramos contigo", "25% OFF", "ANIVERSARIO", "29 de setiembre de 2024", "S/. 800", false, Color(0xFFFCE4EC))
)

val activeCoupons = coupons.filter { it.active }
val expiredCoupons = coupons.filter { !it.active }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CouponListScreen(
    onNavigateBack: () -> Unit
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Activos (${activeCoupons.size})", "Expirados (${expiredCoupons.size})")
    val currentList = if (selectedTabIndex == 0) activeCoupons else expiredCoupons

    Scaffold(
        topBar = {
            TopAppBar(
                title = {  },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = WhitePure)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFFB300),
                    navigationIconContentColor = WhitePure
                )
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
            item { CouponHeader(activeCount = activeCoupons.size) }

            item {
                CouponTabs(
                    tabs = tabs,
                    selectedTabIndex = selectedTabIndex,
                    onTabSelected = { selectedTabIndex = it }
                )
            }

            items(currentList) { coupon ->
                CouponCard(coupon = coupon, isActive = selectedTabIndex == 0)
            }

            item { HowToUseSection() }
        }
    }
}

@Composable
fun CouponHeader(activeCount: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFFFB300),
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
            )
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.ConfirmationNumber,
                contentDescription = null,
                tint = WhitePure,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(WhitePure.copy(alpha = 0.2f))
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "Cupones",
                    color = WhitePure,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$activeCount cupones activos",
                    color = WhitePure.copy(alpha = 0.9f),
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun CouponTabs(tabs: List<String>, selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .offset(y = (-20).dp),
        shape = RoundedCornerShape(50),
        color = Color(0xFFE0E0E0),
        shadowElevation = 4.dp
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent,
            contentColor = RedMain,
            indicator = { },
            divider = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(50))
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { onTabSelected(index) },
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(4.dp)
                        .clip(RoundedCornerShape(50))
                        .background(if (selectedTabIndex == index) Color.White else Color.Transparent)
                ) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.SemiBold,
                        color = if (selectedTabIndex == index) RedMain else Color.DarkGray
                    )
                }
            }
        }
    }
}

@Composable
fun CouponCard(coupon: Coupon, isActive: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.ConfirmationNumber,
                    contentDescription = null,
                    tint = RedMain,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(coupon.color)
                        .padding(6.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(coupon.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(coupon.description, fontSize = 13.sp, color = Color.Gray)
                }
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (isActive) Color(0xFF4CAF50).copy(alpha = 0.8f) else Color.Gray.copy(alpha = 0.5f),
                ) {
                    Text(
                        text = coupon.discount,
                        color = WhitePure,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Código de cupón", fontSize = 13.sp, color = Color.Gray)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(coupon.code, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.ContentCopy, contentDescription = "Copiar", tint = Color.Gray, modifier = Modifier.size(18.dp).clickable { /* TODO: Copy logic */ })
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            InfoRow(icon = Icons.Default.Schedule, text = "Válido hasta ${coupon.expiry}", active = isActive)
            InfoRow(icon = Icons.Default.CheckCircleOutline, text = "Compra mínima: ${coupon.minPurchase}", active = isActive)

            if (isActive) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { /* TODO: Apply coupon */ },
                    modifier = Modifier.fillMaxWidth().height(44.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = RedMain),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Usar cupón", fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, text: String, active: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
        Icon(icon, contentDescription = null, tint = if(active) Color.Gray else Color.LightGray, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, fontSize = 13.sp, color = if(active) Color.DarkGray else Color.LightGray)
    }
}

@Composable
fun HowToUseSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = RedMain.copy(alpha = 0.08f)),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Info, contentDescription = null, tint = RedMain)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cómo usar los cupones", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("• Copia el código del cupón", fontSize = 14.sp, color = Color.DarkGray)
            Text("• Selecciona tu paquete turístico", fontSize = 14.sp, color = Color.DarkGray)
            Text("• Pégalo en el campo de cupón al reservar", fontSize = 14.sp, color = Color.DarkGray)
            Text("• El descuento se aplicará automáticamente", fontSize = 14.sp, color = Color.DarkGray)
        }
    }
}
