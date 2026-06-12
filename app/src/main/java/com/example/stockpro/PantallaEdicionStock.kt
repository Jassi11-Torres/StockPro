package com.example.stockpro

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Interfaz para que el usuario edite las existencias de un artículo
@Composable
fun PantallaEdicionStock(navController: NavController, viewModel: StockViewModel, productoId: Int) {

    // Busco el ítem usando el procedimiento de ID que ya se códifico en el ViewModel
    val itemSeleccionado = remember { viewModel.obtenerProducto(productoId) }

    // Variable para capturar lo que el usuario redacta en la caja de texto
    var cantidadDigitada by remember { mutableStateOf(itemSeleccionado?.stockActual?.toString() ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text("Actualizar Inventario", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))

        // Tarjeta para mostrarle al operario qué producto está tocando
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = itemSeleccionado?.nombre ?: "No encontrado", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = itemSeleccionado?.descripcion ?: "", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Stock Actual: ${itemSeleccionado?.stockActual ?: 0}", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Input numérico para meter la nueva cantidad de la bodega
        OutlinedTextField(
            value = cantidadDigitada,
            onValueChange = { cantidadDigitada = it },
            label = { Text("Nueva Cantidad en Bodega") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón para aplicar el cambio y regresar al catálogo
        Button(
            onClick = {
                val stockValidado = cantidadDigitada.toIntOrNull()
                if (stockValidado != null && itemSeleccionado != null) {
                    viewModel.actualizarStock(itemSeleccionado.id, stockValidado)
                    navController.popBackStack()
                }
            },
            enabled = cantidadDigitada.trim().isNotEmpty() && cantidadDigitada.toIntOrNull() != null,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text("Guardar Cambios", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}
