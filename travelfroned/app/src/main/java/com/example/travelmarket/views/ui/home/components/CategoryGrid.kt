package com.example.travelmarket.views.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoriesGrid(
    onCategoryClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CategoryItem(
                icon = Icons.Default.FlightTakeoff,
                label = "Text",
                modifier = Modifier.weight(1f),
                onClick = { onCategoryClick("flights") }
            )
            CategoryItem(
                icon = Icons.Default.Business,
                label = "Text",
                modifier = Modifier.weight(1f),
                onClick = { onCategoryClick("hotels") }
            )
            CategoryItem(
                icon = Icons.Default.Explore,
                label = "Text",
                modifier = Modifier.weight(1f),
                onClick = { onCategoryClick("activities") }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CategoryItem(
                icon = Icons.Default.Hotel,
                label = "Text",
                modifier = Modifier.weight(1f),
                onClick = { onCategoryClick("packages") }
            )
            CategoryItem(
                icon = Icons.Default.CardTravel,
                label = "Text",
                modifier = Modifier.weight(1f),
                onClick = { onCategoryClick("destinations") }
            )
            CategoryItem(
                icon = Icons.Default.Place,
                label = "Text",
                modifier = Modifier.weight(1f),
                onClick = { onCategoryClick("destinations") }
            )
        }
    }
}

@Composable
private fun CategoryItem(
    icon: ImageVector,
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .background(
                color = Color(0xFFFFF9E6),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color(0xFFFFA000),
            modifier = Modifier.size(36.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF424242)
        )
    }
}
