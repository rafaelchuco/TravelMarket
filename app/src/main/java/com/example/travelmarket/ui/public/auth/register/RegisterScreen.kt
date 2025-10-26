package com.example.travelmarket.ui.public.auth.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val cities = listOf("Lima", "Cusco", "Arequipa", "Trujillo")
    var selectedCity by remember { mutableStateOf(cities[0]) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Crear Cuenta",
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Regístrate para comenzar tu aventura",
            color = Color.DarkGray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            AuthTextField(
                label = "Nombre",
                icon = Icons.Default.Person,
                keyboardType = KeyboardType.Text,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            AuthTextField(
                label = "Apellido",
                icon = Icons.Default.Person,
                keyboardType = KeyboardType.Text,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            label = "Usuario",
            icon = Icons.Default.Person,
            keyboardType = KeyboardType.Text,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        AuthTextField(
            label = "DNI",
            icon = Icons.Default.Person,
            keyboardType = KeyboardType.Number,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        AuthTextField(
            label = "Email",
            icon = Icons.Default.Email,
            keyboardType = KeyboardType.Email,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = selectedCity,
                onValueChange = {},
                readOnly = true,
                label = { Text("Ciudad", color = Color.Gray) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF0F0F0),
                    unfocusedContainerColor = Color(0xFFF0F0F0),
                    focusedIndicatorColor = RedMain,
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = RedMain
                ),
                shape = RoundedCornerShape(8.dp)
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                cities.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            selectedCity = item
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            label = "Teléfono",
            icon = Icons.Default.Phone,
            keyboardType = KeyboardType.Phone,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        AuthTextField(
            label = "Contraseña",
            icon = Icons.Default.Lock,
            keyboardType = KeyboardType.Password,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onRegisterSuccess,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = RedMain,
                contentColor = WhitePure
            ),
            shape = RoundedCornerShape(50)
        ) {
            Text(text = "Registrarse", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

