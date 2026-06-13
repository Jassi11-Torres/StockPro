package com.example.stockpro

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.util.Locale

//Interfaz para mostrar el balance general y métricas de la bodega
@Composable
fun PantallaReporte(navController: NavController, viewModel: StockViewModel) {

    //Consulto los cálculos financieros y de existencias del ViewModel
    val costoTotalBienes = viewModel.calcularValorTotalInventario()
    val cantidadItemsAgotados = viewModel.obtenerProductosEnCero()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text("Reporte Ejecutivo de Bodega", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(28.dp))

        //Tarjeta informativa con el costo monetario total del inventario
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(text = "Valor Total del Inventario", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "$${String.format(Locale.US, "%.2f", costoTotalBienes)}", fontSize = 28.sp, fontWeight = FontWeight.Black)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        //Tarjeta de alerta para ver la cantidad de productos en cero absoluto
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(text = "Artículos Agotados", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "$cantidadItemsAgotados productos", fontSize = 28.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.error)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        //Botón simple para regresar al catálogo de suministros
        Button(
            onClick = { navController.popBackStack() },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text("Volver al Catálogo", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}
