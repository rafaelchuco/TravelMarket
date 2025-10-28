package com.example.travelmarket.views.ui.auth.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * AuthScreenState - Define los estados posibles de autenticación
 */
enum class AuthScreenState {
    LOGIN,
    REGISTER
}

/**
 * AuthSwitcher - Componente selector entre Login y Registro
 */
@Composable
fun AuthSwitcher(
    currentScreen: AuthScreenState,
    onSwitchScreen: (AuthScreenState) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(50))
            .background(Color(0xFFE0E0E0)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SwitcherItem(
            text = "Iniciar Sesión",
            isSelected = currentScreen == AuthScreenState.LOGIN,
            onClick = { onSwitchScreen(AuthScreenState.LOGIN) },
            modifier = Modifier.weight(1f)
        )
        SwitcherItem(
            text = "Registrarse",
            isSelected = currentScreen == AuthScreenState.REGISTER,
            onClick = { onSwitchScreen(AuthScreenState.REGISTER) },
            modifier = Modifier.weight(1f)
        )
    }
}

/**
 * SwitcherItem - Item individual del switcher
 */
@Composable
private fun SwitcherItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(50))
            .background(if (isSelected) Color.White else Color.Transparent)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isSelected) Color(0xFFE91E63) else Color.DarkGray,  // RedMain
            fontSize = 16.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}
