package com.example.travelmarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.travelmarket.ui.customer.ProfileScreen
import com.example.travelmarket.ui.customer.ReservationsScreen
import com.example.travelmarket.ui.customer.WishlistScreen
import com.example.travelmarket.ui.customer.BookingFlowScreen
import com.example.travelmarket.ui.customer.CreateReviewScreen
import com.example.travelmarket.ui.customer.MyQueriesScreen
import com.example.travelmarket.ui.customer.NewQueryScreen
import com.example.travelmarket.ui.customer.ReservationDetailScreen
import com.example.travelmarket.ui.customer.ChangePasswordScreen
import com.example.travelmarket.ui.customer.EditProfileScreen
import com.example.travelmarket.ui.theme.TravelMarketTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TravelMarketTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "wishlist"
                    ) {
                        composable("wishlist") {
                            WishlistScreen(
                                onViewDetails = { /* TODO: navegar a detalle de paquete cuando esté listo */ },
                                onBookNow = { _ -> navController.navigate("booking") }
                            )
                        }
                        composable("booking") { BookingFlowScreen() }
                        composable("reservations") { ReservationsScreen() }
                        composable("reservationDetail") { ReservationDetailScreen() }
                        composable("profile") { ProfileScreen() }
                        composable("editProfile") { EditProfileScreen() }
                        composable("changePassword") { ChangePasswordScreen() }
                        composable("createReview") { CreateReviewScreen() }
                        composable("myQueries") { MyQueriesScreen() }
                        composable("newQuery") { NewQueryScreen() }
                    }
                }
            }
        }
    }
}

// Previews para todas las pantallas customer
@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    TravelMarketTheme {
        ProfileScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ReservationsScreenPreview() {
    TravelMarketTheme {
        ReservationsScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun WishlistScreenPreview() {
    TravelMarketTheme {
        WishlistScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun BookingFlowScreenPreview() {
    TravelMarketTheme {
        BookingFlowScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun CreateReviewScreenPreview() {
    TravelMarketTheme {
        CreateReviewScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun MyQueriesScreenPreview() {
    TravelMarketTheme {
        MyQueriesScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun NewQueryScreenPreview() {
    TravelMarketTheme {
        NewQueryScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ReservationDetailScreenPreview() {
    TravelMarketTheme {
        ReservationDetailScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ChangePasswordScreenPreview() {
    TravelMarketTheme {
        ChangePasswordScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    TravelMarketTheme {
        EditProfileScreen()
    }
}