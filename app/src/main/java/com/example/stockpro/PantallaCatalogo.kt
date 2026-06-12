package com.example.stockpro

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// 6.0 Pantalla principal del catálogo de suministros
@Composable
fun PantallaCatalogo(navController: NavController, viewModel: StockViewModel, nombreOperario: String) {
    var verSoloCritico by remember { mutableStateOf(false) }

    // 6.1 Filtrado dinámico usando la lógica en español del ViewModel
    val listaAExhibir = if (verSoloCritico) {
        viewModel.obtenerProductosEnRiesgo()
    } else {
        viewModel.productos
    }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { navController.navigate("pantalla4") },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Ver Reporte", fontWeight = FontWeight.Bold)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // 6.2 Banner informativo con el operario logueado
            Surface(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Operario activo: $nombreOperario",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Catálogo de Suministros", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            // 6.3 Botones de filtro para alternar estados de vista
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { verSoloCritico = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!verSoloCritico) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = if (!verSoloCritico) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Ver Todo")
                }

                Button(
                    onClick = { verSoloCritico = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (verSoloCritico) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = if (verSoloCritico) MaterialTheme.colorScheme.onError else MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Stock Crítico")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 6.4 Lista optimizada y reactiva del inventario
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
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

// 6.5 Tarjeta contenedora con la información visual del artículo
@Composable
fun ItemTarjetaProducto(producto: Producto, onClick: () -> Unit) {
    val esCritico = producto.stockActual < 5

    Card(
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = producto.nombre, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Precio: $${producto.precio}", style = MaterialTheme.typography.bodyMedium)
            }

            Surface(
                color = if (esCritico) Color(0x1FFF0000) else MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Stock: ${producto.stockActual}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (esCritico) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPantallaCatalogo() {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Vista Previa: Catálogo StockPro",
                modifier = Modifier.padding(16.dp),
                fontWeight = FontWeight.Bold
            )
        }
    }
}
