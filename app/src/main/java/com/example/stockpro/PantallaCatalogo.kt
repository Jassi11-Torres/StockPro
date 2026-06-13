package com.example.stockpro

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

// Interfaz principal para la visualización y filtrado de suministros en bodega
@Composable
fun PantallaCatalogo(navController: NavController, viewModel: StockViewModel, nombreOperario: String) {
    var verSoloCritico by remember { mutableStateOf(false) }

    // Filtrado lógico consumiendo las funciones nativas en español del ViewModel
    val listaAExhibir = if (verSoloCritico) {
        viewModel.obtenerProductosEnRiesgo()
    } else {
        viewModel.productos
    }

    Scaffold(
        containerColor = Color.Transparent, // Permite que el degradado del Box de fondo sea visible
        floatingActionButton = {
            // Botón flotante estilizado con azul de alta intensidad
            ExtendedFloatingActionButton(
                onClick = { navController.navigate("pantalla4") },
                containerColor = Color(0xFF2563EB),
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Ver Reporte", fontWeight = FontWeight.Bold)
            }
        }
    ) { paddingValues ->
        // Contenedor principal con el degradado fluido unificado con el login
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
                )
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Banner superior de operario con un tono morado índigo profundo armónico
                Surface(
                    color = Color(0xFF312E81),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Operario activo: $nombreOperario",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Contenedor del título centrado horizontalmente
                Surface(
                    color = Color(0xFFFACC15), // Amarillo industrial intenso
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Catálogo de Productos",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A),
                        textAlign = TextAlign.Center, // Centramos el texto de forma simétrica
                        modifier = Modifier
                            .fillMaxWidth() // Obliga al texto a usar todo el ancho para poder centrarse
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botonera de filtros tipo cápsula con contornos y fondos reactivos equilibrados
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { verSoloCritico = false },
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(
                            width = 2.dp,
                            color = if (!verSoloCritico) Color(0xFF2563EB) else Color(0xFF1E3A8A)
                        ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!verSoloCritico) Color(0xFF2563EB) else Color(0xFFE2E8F0),
                            contentColor = if (!verSoloCritico) Color.White else Color(0xFF1E3A8A)
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Ver Todo", fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = { verSoloCritico = true },
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(
                            width = 2.dp,
                            color = if (verSoloCritico) Color(0xFFDC2626) else Color(0xFF991B1B)
                        ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (verSoloCritico) Color(0xFFDC2626) else Color(0xFFE2E8F0),
                            contentColor = if (verSoloCritico) Color.White else Color(0xFF991B1B)
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Stock Crítico", fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Lista reactiva para el renderizado del inventario disponible
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(listaAExhibir) { item ->
                        ItemTarjetaProducto(producto = item, onClick = {
                            navController.navigate("pantalla3/${item.id}")
                        })
                    }
                }
            }
        }
    }
}

// Tarjeta contenedora con la información visual estructurada de cada artículo
@Composable
fun ItemTarjetaProducto(producto: Producto, onClick: () -> Unit) {
    val esCritico = producto.stockActual < 5

    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = producto.nombre,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Precio: $${producto.precio}",
                    fontSize = 14.sp,
                    color = Color(0xFF475569),
                    fontWeight = FontWeight.Medium
                )
            }

            // Indicador de existencias con semaforización verde o naranja bajo según criticidad
            Surface(
                color = if (esCritico) Color(0xFFFFEDD5) else Color(0xFFDCFCE7),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "Stock: ${producto.stockActual}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (esCritico) Color(0xFFC2410C) else Color(0xFF15532D),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }
        }
    }
}
