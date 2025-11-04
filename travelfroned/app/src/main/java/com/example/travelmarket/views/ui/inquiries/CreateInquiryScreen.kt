package com.example.travelmarket.views.ui.inquiries

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.travelmarket.logic.data.models.request.inquiries.CreateInquiryRequest
import com.example.travelmarket.logic.viewmodels.inquiries.CreateInquiryState
import com.example.travelmarket.logic.viewmodels.inquiries.CreateInquiryViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateInquiryScreen(
    navController: NavController,
    viewModel: CreateInquiryViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var relatedPackage by remember { mutableStateOf("") }

    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state) {
        if (state is CreateInquiryState.Success) {
            snackbarHostState.showSnackbar("¡Consulta enviada con éxito!")
            delay(1500)
            navController.popBackStack()
            viewModel.resetState()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva consulta") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFDC143C),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF0F2F5))
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // FORMULARIO
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    InquiryTextField(value = name, onValueChange = { name = it }, label = "Nombre Completo")
                    Spacer(Modifier.height(16.dp))
                    InquiryTextField(value = email, onValueChange = { email = it }, label = "Email")
                    Spacer(Modifier.height(16.dp))
                    InquiryTextField(value = phone, onValueChange = { phone = it }, label = "Teléfono (+51 000-000-000)")
                    Spacer(Modifier.height(16.dp))
                    InquiryTextField(
                        value = relatedPackage,
                        onValueChange = { relatedPackage = it },
                        label = "Paquete Relacionado (Opcional)",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(Modifier.height(16.dp))
                    InquiryTextField(value = subject, onValueChange = { subject = it }, label = "Asunto")
                    Spacer(Modifier.height(16.dp))
                    InquiryTextField(value = message, onValueChange = { message = it }, label = "Mensaje", singleLine = false, minLines = 5)
                }
            }

            Spacer(Modifier.weight(1f))

            // BOTÓN ENVIAR
            Button(
                onClick = {
                    val request = CreateInquiryRequest(
                        name = name,
                        email = email,
                        phone = phone,
                        subject = subject,
                        message = message,
                        packageId = relatedPackage.toLongOrNull()
                    )
                    viewModel.createInquiry(request)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC143C)),
                enabled = state !is CreateInquiryState.Loading && name.isNotEmpty() && email.isNotEmpty() && subject.isNotEmpty() && message.isNotEmpty()
            ) {
                if (state is CreateInquiryState.Loading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Icon(Icons.Default.Send, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Enviar Consulta", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun InquiryTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    singleLine: Boolean = true,
    minLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        singleLine = singleLine,
        minLines = minLines,
        keyboardOptions = keyboardOptions,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFDC143C),
            unfocusedBorderColor = Color.LightGray,
            focusedLabelColor = Color(0xFFDC143C)
        )
    )
}
