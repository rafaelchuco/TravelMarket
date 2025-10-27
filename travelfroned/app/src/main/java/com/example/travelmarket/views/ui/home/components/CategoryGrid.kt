package com.example.travelmarket.views.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.DownhillSkiing
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class Category(
    val name: String,
    val icon: ImageVector,
    val color: Color
)

private val categories = listOf(
    Category("Arqueológico", Icons.Default.AccountBalance, Color(0xFFE8EAF6)),
    Category("Gastronómico", Icons.Default.Restaurant, Color(0xFFFFF9E0)),
    Category("Aventura", Icons.Default.DownhillSkiing, Color(0xFFE8F5E9)),
    Category("Naturaleza", Icons.Default.Landscape, Color(0xFFE0F7FA)),
    Category("Cultural", Icons.Default.Face, Color(0xFFFCE4EC)),
    Category("Playas", Icons.Default.AddLocation, Color(0xFFFFF3E0))
)

@Composable
fun CategoryGrid(
    onCategoryClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .height(240.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(categories) { category ->
            CategoryItem(
                icon = category.icon,
                label = category.name,
                color = category.color,
                onClick = { onCategoryClick(category.name) }
            )
        }
    }
}

@Composable
private fun CategoryItem(
    icon: ImageVector,
    label: String,
    color: Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            onClick = onClick,
            modifier = Modifier.size(80.dp),
            shape = RoundedCornerShape(16.dp),
            color = color
        ) {
            Icon(
                icon,
                contentDescription = label,
                tint = Color.Black.copy(alpha = 0.7f),
                modifier = Modifier.padding(20.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.DarkGray
        )
    }
}