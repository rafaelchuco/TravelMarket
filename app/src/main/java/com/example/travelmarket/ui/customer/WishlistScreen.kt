package com.example.travelmarket.ui.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.travelmarket.ui.viewmodels.PackagesViewModel

@Composable
fun WishlistScreen(
    viewModel: PackagesViewModel = viewModel()
) {
    val packages by viewModel.packages.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    
    // Convertir Package a WishlistItem para mantener compatibilidad
    val wishlistItems = packages.map { packageItem ->
        WishlistItem(
            id = packageItem.id.toString(),
            title = packageItem.title,
            location = "Ubicación", // TODO: Obtener de packageItem
            price = "S/ ${packageItem.price.toInt()}",
            duration = "${packageItem.durationDays} día(s)",
            rating = 4.5,
            imageUrl = packageItem.imageUrl
        )
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE53E3E))
                .padding(16.dp)
        ) {
            Text(
                text = "Mi Wishlist",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        
        // Mostrar estado de carga o error
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (error != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Error al cargar datos: $error",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                    Button(
                        onClick = { viewModel.loadPackages() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE53E3E)
                        )
                    ) {
                        Text("Reintentar")
                    }
                }
            }
        } else if (wishlistItems.isEmpty()) {
            // Empty state
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Wishlist vacía",
                    modifier = Modifier.size(64.dp),
                    tint = Color.Gray
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Tu wishlist está vacía",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
                
                Text(
                    text = "Explora nuestros paquetes y guarda tus favoritos",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        } else {
            // Grid de paquetes
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(wishlistItems) { item ->
                    WishlistItemCard(
                        item = item,
                        onRemoveFromWishlist = { 
                            wishlistItems = wishlistItems.filter { it.id != item.id }
                        },
                        onViewDetails = { /* TODO: Ver detalles del paquete */ },
                        onBookNow = { /* TODO: Reservar paquete */ }
                    )
                }
            }
        }
    }
}

@Composable
fun WishlistItemCard(
    item: WishlistItem,
    onRemoveFromWishlist: () -> Unit,
    onViewDetails: () -> Unit,
    onBookNow: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            // Imagen del paquete
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = item.title,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                    contentScale = ContentScale.Crop
                )
                
                // Botón de favorito
                IconButton(
                    onClick = onRemoveFromWishlist,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Quitar de favoritos",
                        tint = Color(0xFFE53E3E),
                        modifier = Modifier.size(20.dp)
                    )
                }
                
                // Duración
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.7f)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                ) {
                    Text(
                        text = item.duration,
                        fontSize = 10.sp,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
            
            // Contenido de la card
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                Text(
                    text = item.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = item.location,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.price,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE53E3E)
                    )
                    
                    Text(
                        text = "${item.rating}★",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Botones de acción
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onViewDetails,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFFE53E3E)
                        ),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFE53E3E))
                        )
                    ) {
                        Text(
                            text = "Ver",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    
                    Button(
                        onClick = onBookNow,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE53E3E)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Reservar",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

data class WishlistItem(
    val id: String,
    val title: String,
    val location: String,
    val price: String,
    val duration: String,
    val rating: Double,
    val imageUrl: String
)

fun getSampleWishlistItems(): List<WishlistItem> {
    return listOf(
        WishlistItem(
            id = "1",
            title = "Machu Picchu Full Day",
            location = "Cusco, Perú",
            price = "S/ 250",
            duration = "1 día",
            rating = 4.8,
            imageUrl = "https://images.unsplash.com/photo-1587595431973-160d0d94add1?w=400"
        ),
        WishlistItem(
            id = "2",
            title = "Valle Sagrado + Ollantaytambo",
            location = "Cusco, Perú",
            price = "S/ 180",
            duration = "1 día",
            rating = 4.6,
            imageUrl = "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=400"
        ),
        WishlistItem(
            id = "3",
            title = "Montaña de Colores",
            location = "Cusco, Perú",
            price = "S/ 200",
            duration = "1 día",
            rating = 4.9,
            imageUrl = "https://images.unsplash.com/photo-1551524164-6cf2ac5313c2?w=400"
        ),
        WishlistItem(
            id = "4",
            title = "Laguna Humantay",
            location = "Cusco, Perú",
            price = "S/ 150",
            duration = "1 día",
            rating = 4.7,
            imageUrl = "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=400"
        ),
        WishlistItem(
            id = "5",
            title = "City Tour Cusco",
            location = "Cusco, Perú",
            price = "S/ 120",
            duration = "1 día",
            rating = 4.5,
            imageUrl = "https://images.unsplash.com/photo-1587595431973-160d0d94add1?w=400"
        ),
        WishlistItem(
            id = "6",
            title = "Salineras de Maras",
            location = "Cusco, Perú",
            price = "S/ 100",
            duration = "1 día",
            rating = 4.4,
            imageUrl = "https://images.unsplash.com/photo-1551524164-6cf2ac5313c2?w=400"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun WishlistScreenPreview() {
    WishlistScreen()
}

@Preview(showBackground = true)
@Composable
fun WishlistItemCardPreview() {
    WishlistItemCard(
        item = WishlistItem(
            id = "1",
            title = "Machu Picchu Full Day",
            location = "Cusco, Perú",
            price = "S/ 250",
            duration = "1 día",
            rating = 4.8,
            imageUrl = "https://images.unsplash.com/photo-1587595431973-160d0d94add1?w=400"
        ),
        onRemoveFromWishlist = { },
        onViewDetails = { },
        onBookNow = { }
    )
}
