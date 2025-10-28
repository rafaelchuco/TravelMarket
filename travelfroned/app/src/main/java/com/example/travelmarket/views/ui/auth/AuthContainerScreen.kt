package com.example.travelmarket.views.ui.auth

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelmarket.R
import com.example.travelmarket.ui.theme.RedMain
import com.example.travelmarket.ui.theme.TravelMarketTheme
import com.example.travelmarket.ui.theme.WhitePure
import com.example.travelmarket.views.ui.auth.components.AuthSwitcher
import com.example.travelmarket.views.ui.auth.login.LoginScreen
import com.example.travelmarket.views.ui.auth.register.RegisterScreen

enum class AuthScreenState {
    LOGIN, REGISTER
}

@Composable
fun AuthContainerScreen(
    onLoginSuccess: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    var currentScreen by remember { mutableStateOf(AuthScreenState.LOGIN) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = WhitePure
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(WhitePure),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Logo",
                    modifier = Modifier.size(50.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Bienvenido",
                color = Color.Black,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Tu puerta al turismo peruano",
                color = Color.DarkGray,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(48.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
                color = WhitePure
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AuthSwitcher(
                        currentScreen = currentScreen,
                        onSwitchScreen = { newState -> currentScreen = newState }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Crossfade(targetState = currentScreen, animationSpec = tween(500)) { screenState ->
                        when (screenState) {
                            AuthScreenState.LOGIN -> LoginScreen(
                                onLoginSuccess = onLoginSuccess,
                            )
                            AuthScreenState.REGISTER -> RegisterScreen(
                                onRegisterSuccess = onRegisterSuccess,
                                onNavigateToLogin = { currentScreen = AuthScreenState.LOGIN }
                            )
                        }
                    }
                }
            }
        }
    }
}