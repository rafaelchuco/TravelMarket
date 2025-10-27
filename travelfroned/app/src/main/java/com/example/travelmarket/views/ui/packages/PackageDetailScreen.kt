package com.example.travelmarket.views.ui.packages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelmarket.R
import com.example.travelmarket.ui.theme.RedMain
import com.example.travelmarket.ui.theme.TravelMarketTheme
import com.example.travelmarket.ui.theme.WhitePure
import com.example.travelmarket.views.ui.packages.components.ItineraryTab
import com.example.travelmarket.views.ui.packages.components.ReviewsTab

val packageDetailPlaceholder = packages.firstOrNull() ?: Package("pkg_error", "Error", "N/A", "N/A", 0.0, 0, 0.0)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PackageDetailScreen(
    packageId: String?,
    onNavigateBack: () -> Unit,
    onReservarClick: () -> Unit
) {
    var selectedTab by remember { mutableStateOf("Descripción") }
    val tabs = listOf("Descripción", "Itinerario", "Reseñas")
    var isFavorite by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Button(
                    onClick = onReservarClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = RedMain),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Reservar por S/. ${packageDetailPlaceholder.price}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = packageDetailPlaceholder.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .align(Alignment.TopCenter),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        SmallIconButton(icon = Icons.AutoMirrored.Filled.ArrowBack, onClick = onNavigateBack)
                        Row {
                            SmallIconButton(
                                icon = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                onClick = { isFavorite = !isFavorite },
                                tint = if (isFavorite) RedMain else WhitePure
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            SmallIconButton(icon = Icons.Default.Share, onClick = { })
                        }
                    }
                }
            }

            item {
                Column(modifier = Modifier.background(Color.White).padding(16.dp)) {
                    Text(packageDetailPlaceholder.title, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        InfoChip(Icons.Default.LocationOn, packageDetailPlaceholder.location)
                        Spacer(modifier = Modifier.width(8.dp))
                        InfoChip(Icons.Default.Star, "${packageDetailPlaceholder.rating} (${packageDetailPlaceholder.reviews} reseñas)")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    InfoChip(Icons.Default.Person, "Máx. 12 personas")

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Precio por persona",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "S/. ${packageDetailPlaceholder.price}",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = RedMain
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = onReservarClick,
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = RedMain),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Reservar ahora", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            item {
                TabRow(
                    selectedTabIndex = tabs.indexOf(selectedTab),
                    containerColor = Color.White
                ) {
                    tabs.forEach { title ->
                        Tab(
                            selected = (selectedTab == title),
                            onClick = { selectedTab = title },
                            text = { Text(title) },
                            selectedContentColor = RedMain,
                            unselectedContentColor = Color.Gray
                        )
                    }
                }
            }

            item {
                Box(modifier = Modifier
                    .background(Color.White)
                    .padding(16.dp)
                ) {
                    when (selectedTab) {
                        "Descripción" -> DescripcionTab(description = "...")
                        "Itinerario" -> ItineraryTab()
                        "Reseñas" -> ReviewsTab()
                    }
                }
            }
        }
    }
}

@Composable
private fun DescripcionTab(description: String) {
    Column {
        Text("Sobre el tour", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Descubre la maravilla del mundo con nuestro tour exclusivo a Machu Picchu. Incluye visita guiada completa, transporte en tren de lujo y alojamiento en Aguas Calientes.",
            fontSize = 14.sp,
            color = Color.DarkGray
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            InfoColumn("Altitud", "2,430 msnm")
            InfoColumn("Clima", "Templado húmedo (12-24°C)")
            InfoColumn("Mejor época", "Mayo - Septiembre")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text("Incluye", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        IncludeItem(text = "Transporte desde Cusco")
        IncludeItem(text = "Tren de ida y vuelta")
        IncludeItem(text = "Guía oficial certificado")

        Spacer(modifier = Modifier.height(16.dp))

        Text("No incluye", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        IncludeItem(text = "Vuelos a Cusco", included = false)
        IncludeItem(text = "Propinas", included = false)
    }
}

@Composable
private fun SmallIconButton(icon: ImageVector, onClick: () -> Unit, tint: Color = WhitePure) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.4f))
    ) {
        Icon(icon, contentDescription = null, tint = tint)
    }
}

@Composable
private fun InfoChip(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text, fontSize = 13.sp, color = Color.DarkGray)
    }
}

@Composable
private fun InfoColumn(title: String, value: String) {
    Column {
        Text(title, fontSize = 14.sp, color = Color.Gray, fontWeight = FontWeight.SemiBold)
        Text(value, fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun IncludeItem(text: String, included: Boolean = true) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
        Icon(
            imageVector = if (included) Icons.Default.CheckCircle else Icons.Default.Warning,
            contentDescription = null,
            tint = if (included) Color(0xFF4CAF50) else RedMain,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, fontSize = 14.sp, color = Color.DarkGray)
    }
}