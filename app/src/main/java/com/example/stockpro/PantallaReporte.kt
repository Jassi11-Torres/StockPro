package com.example.stockpro

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.util.Locale

// Interfaz para mostrar el balance general y métricas de la bodega
@Composable
fun PantallaReporte(navController: NavController, viewModel: StockViewModel) {

    // Consulto los cálculos financieros y de existencias del ViewModel
    val costoTotalBienes = viewModel.calcularValorTotalInventario()
    val cantidadItemsAgotados = viewModel.obtenerProductosEnCero()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF4F46E5), // Morado moderno
                        Color(0xFF3B82F6), // Azul eléctrico
                        Color(0xFF06B6D4)  // Cian brillante
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
            )
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Contenedor del título suavizado a un tono grafito/pizarra intermedio
        Surface(
            color = Color(0xFF334155), // Gris oscuro corporativo con intensidad regulada
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.5.dp, Color(0xFF475569)), // Delineado sutil de contorno
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Reporte Financiero de Stock",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White, // Blanco nítido para un contraste y lectura impecables
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Tarjeta informativa con el costo monetario total del inventario
        Card(
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF7ED)), // Fondo naranja melocotón suave
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Valor Total del Inventario",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF475569)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "$${String.format(Locale.US, "%.2f", costoTotalBienes)}",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF0F172A)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Tarjeta de alerta para ver la cantidad de productos en cero absoluto
        Card(
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB)), // Fondo amarillo crema sutil
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Artículos Agotados",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF475569)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "$cantidadItemsAgotados productos",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFFDC2626)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Botón principal de retorno tipo cápsula en azul de alta intensidad
        Button(
            onClick = { navController.popBackStack() },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2563EB),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
        ) {
            Text("Volver al Catálogo", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}
