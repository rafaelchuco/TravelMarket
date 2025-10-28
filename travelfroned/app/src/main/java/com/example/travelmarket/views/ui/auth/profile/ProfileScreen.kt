package com.example.travelmarket.views.ui.auth.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.viewmodels.auth.ProfileViewModel
import com.example.travelmarket.views.navigation.Routes
import com.example.travelmarket.views.ui.auth.components.AuthButton
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = koinViewModel()
) {
    val profileState by viewModel.profileState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.currentStateFlow.collectLatest { state ->
            if (state == Lifecycle.State.RESUMED) {
                viewModel.getUserProfile()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))  // ✅ FONDO GRIS CLARO
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // ✅ HEADER ROJO
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFDC143C))
                    .padding(vertical = 40.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Avatar circular
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(40.dp))
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = Color(0xFFDC143C),
                            modifier = Modifier.size(48.dp)
                        )
                    }

                    when (profileState) {
                        is NetworkResult.Success -> {
                            val user = (profileState as NetworkResult.Success).data
                            Text(
                                text = "${user.firstName} ${user.lastName}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "@${user.username}",
                                fontSize = 14.sp,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                        else -> {}
                    }
                }
            }

            // ✅ CONTENIDO
            when (profileState) {
                is NetworkResult.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0xFFDC143C))
                    }
                }

                is NetworkResult.Success -> {
                    val user = (profileState as NetworkResult.Success).data
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // ✅ INFORMACIÓN PERSONAL
                        ProfileCard(title = "Información Personal") {
                            ProfileItemRow(Icons.Default.Person, "Usuario", user.username)
                            ProfileItemRow(Icons.Default.Email, "Email", user.email)
                            ProfileItemRow(Icons.Default.Badge, "Nombre", user.firstName)
                            ProfileItemRow(Icons.Default.Badge, "Apellidos", user.lastName)
                            user.phone?.let {
                                ProfileItemRow(Icons.Default.Phone, "Teléfono", it)
                            }
                        }

                        // ✅ INFORMACIÓN ADICIONAL
                        ProfileCard(title = "Información Adicional") {
                            user.nationality?.let {
                                ProfileItemRow(Icons.Default.Public, "Nacionalidad", it)
                            }
                            user.passportNumber?.let {
                                ProfileItemRow(Icons.Default.CardTravel, "Pasaporte", it)
                            }
                            user.address?.let {
                                ProfileItemRow(Icons.Default.LocationOn, "Dirección", it)
                            }
                            user.city?.let {
                                ProfileItemRow(Icons.Default.LocationCity, "Ciudad", it)
                            }
                            user.country?.let {
                                ProfileItemRow(Icons.Default.Flag, "País", it)
                            }
                        }

                        // ✅ INFORMACIÓN DE CUENTA
                        ProfileCard(title = "Información de Cuenta") {
                            ProfileItemRow(
                                Icons.Default.VerifiedUser,
                                "Tipo de Usuario",
                                user.userType
                            )
                            ProfileItemRow(
                                Icons.Default.CheckCircle,
                                "Estado",
                                if (user.isActive) "Activo" else "Inactivo"
                            )
                            ProfileItemRow(
                                Icons.Default.CalendarToday,
                                "Fecha de Registro",
                                user.dateJoined
                            )
                        }

                        // ✅ BOTÓN EDITAR
                        AuthButton(
                            text = "Editar Perfil",
                            onClick = { navController.navigate(Routes.EditProfile.route) }
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                is NetworkResult.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ErrorOutline,
                                contentDescription = null,
                                tint = Color(0xFFDC143C),
                                modifier = Modifier.size(64.dp)
                            )
                            Text(
                                text = "Error al cargar el perfil",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color(0xFFDC143C)
                            )
                            Text(
                                text = (profileState as NetworkResult.Error).message,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                            AuthButton(
                                text = "Reintentar",
                                onClick = { viewModel.getUserProfile() }
                            )
                        }
                    }
                }

                else -> {}
            }
        }
    }
}

// ✅ COMPONENTE: Card de sección
@Composable
private fun ProfileCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFFDC143C),
                fontWeight = FontWeight.Bold
            )
            HorizontalDivider(color = Color(0xFFDC143C).copy(alpha = 0.3f))
            content()
        }
    }
}

// ✅ COMPONENTE: Item con icono - VALORES EN NEGRO
@Composable
private fun ProfileItemRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFFDC143C),
            modifier = Modifier.size(24.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF212121)  // ✅ NEGRO OSCURO
            )
        }
    }
}
