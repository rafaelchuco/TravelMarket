package com.example.travelmarket.ui.public.auth.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelmarket.ui.core.theme.RedMain
import com.example.travelmarket.ui.core.theme.TravelMarketTheme
import com.example.travelmarket.ui.core.theme.WhitePure
import com.example.travelmarket.ui.public.auth.components.AuthTextField

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    var rememberMe by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Iniciar Sesión",
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Ingresa tus credenciales para continuar",
            color = Color.DarkGray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        AuthTextField(
            label = "Email",
            icon = Icons.Default.Email,
            keyboardType = KeyboardType.Email,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            label = "Contraseña",
            icon = Icons.Default.Lock,
            keyboardType = KeyboardType.Password,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Checkbox(
                checked = rememberMe,
                onCheckedChange = { rememberMe = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = RedMain,
                    uncheckedColor = Color.Gray
                )
            )
            Text(
                text = "Ingresar como Administrador",
                color = Color.DarkGray,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onLoginSuccess,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = RedMain,
                contentColor = WhitePure
            ),
            shape = RoundedCornerShape(50)
        ) {
            Text(text = "Ingresar", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

