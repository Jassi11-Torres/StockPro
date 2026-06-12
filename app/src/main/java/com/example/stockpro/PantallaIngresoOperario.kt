package com.example.stockpro

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun PantallaIngresoOperario(navController: NavController) {
    var nombreTexto by remember { mutableStateOf("") }

    // El botón se habilita únicamente si el nombre tiene al menos 3 caracteres reales
    val esBotonHabilitado = nombreTexto.trim().length >= 3

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenido a StockPro",
            fontSize = 30.sp,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Gestión Integrada de Bodega",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = nombreTexto,
            onValueChange = { nombreTexto = it },
            label = { Text("Nombre del Operario") },
            placeholder = { Text("Ej. Juan Pérez") },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (esBotonHabilitado) {
                    // Viajamos al catálogo inyectando el parámetro en la ruta de navegación
                    navController.navigate("pantalla2/${nombreTexto.trim()}")
                }
            },
            enabled = esBotonHabilitado,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text("Ingresar al Sistema", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPantallaIngresoOperario() {
    MaterialTheme {
        PantallaIngresoOperario(navController = rememberNavController())
    }
}
