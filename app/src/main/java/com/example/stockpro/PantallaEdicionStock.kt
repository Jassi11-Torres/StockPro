package com.example.stockpro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Interfaz para la modificación regulada de las existencias de un artículo
@Composable
fun PantallaEdicionStock(navController: NavController, viewModel: StockViewModel, productoId: Int) {

    // Búsqueda del artículo correspondiente mediante el identificador único
    val itemSeleccionado = remember { viewModel.obtenerProducto(productoId) }

    // Captura reactiva de la entrada numérica ingresada por el operario
    var cantidadDigitada by remember { mutableStateOf(itemSeleccionado?.stockActual?.toString() ?: "") }

    // Contenedor tipo caja que mantiene el fondo degradado de toda la aplicación visible
    Box(
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
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Contenedor del título superior integrado con el color del reporte financiero
            Surface(
                color = Color(0xFF4ADE80), // Tonalidad verde nítida para perfecta armonía visual
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Actualizar Inventario",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A), // Negro profundo para un contraste impecable
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Tarjeta informativa con los metadatos actuales del suministro seleccionado
            Card(
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF7ED)), // Fondo naranja melocotón suave
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = itemSeleccionado?.nombre ?: "No encontrado",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = itemSeleccionado?.descripcion ?: "",
                        fontSize = 14.sp,
                        color = Color(0xFF475569)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Stock Actual: ${itemSeleccionado?.stockActual ?: 0}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E3A8A)
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Indicación fija en blanco nítido arriba del cuadro para evitar que se pierda
            Text(
                text = "Nueva Cantidad en Bodega",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, bottom = 8.dp) // Pequeño margen para alinear con el cuadro
            )

            // Campo de entrada completamente blanco y limpio por dentro
            OutlinedTextField(
                value = cantidadDigitada,
                onValueChange = { cantidadDigitada = it },
                placeholder = { Text("Ingrese el nuevo stock") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF2563EB), // Azul eléctrico al escribir
                    unfocusedBorderColor = Color(0xFFCBD5E1),
                    focusedTextColor = Color(0xFF0F172A),
                    unfocusedTextColor = Color(0xFF0F172A),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp) // Altura estándar estilizada
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Control de comando formalizado para guardar el cambio y regresar al catálogo
            Button(
                onClick = {
                    val stockValidado = cantidadDigitada.toIntOrNull()
                    if (stockValidado != null && itemSeleccionado != null) {
                        viewModel.actualizarStock(itemSeleccionado.id, stockValidado)
                        navController.popBackStack()
                    }
                },
                enabled = cantidadDigitada.trim().isNotEmpty() && cantidadDigitada.toIntOrNull() != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2563EB),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
            ) {
                Text(
                    text = "Guardar Cambios",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
