package com.example.stockpro

import android.R.attr.type
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.stockpro.ui.theme.StockProTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StockProTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StockProApp()
                }
            }
        }
    }
}

@Composable
fun StockProApp() {
    val navController = rememberNavController()
    val stockViewModel: StockViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "pantalla1"
    ) {
        composable("pantalla1") {
            PantallaIngresoOperario(navController = navController)
        }

        composable(
            route = "pantalla2/{nombreOperario}",
            arguments = listOf(navArgument("nombreOperario") { type = NavType.StringType })
        ) { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombreOperario") ?: "Desconocido"
            PantallaCatalogo(navController = navController, viewModel = stockViewModel, nombreOperario = nombre)
        }

        composable(
            route = "pantalla3/{productoId}",
            arguments = listOf(navArgument("productoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("productoId") ?: 0
            PantallaEdicionStock(navController = navController, viewModel = stockViewModel, productoId = id)
        }

        composable("pantalla4") {
            PantallaReporte(navController = navController, viewModel = stockViewModel)
        }
    }
}
