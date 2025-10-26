package com.example.travelmarket.ui.customer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.travelmarket.ui.theme.TravelMarketTheme

// Archivo de prueba para las pantallas customer
@Composable
fun TestCustomerScreens() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Text(
            text = "Pantallas Customer - En desarrollo",
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TestCustomerScreensPreview() {
    TravelMarketTheme {
        TestCustomerScreens()
    }
}
