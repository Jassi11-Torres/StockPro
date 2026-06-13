package com.example.stockpro

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun PantallaIngresoOperario(navController: NavController) {
    var nombreTexto by remember { mutableStateOf("") }
    val esBotonHabilitado = nombreTexto.trim().length >= 3

    // Contenedor principal con soporte para superposición de componentes y fondo degradado
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF4F46E5),
                        Color(0xFF3B82F6),
                        Color(0xFF06B6D4)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {

            // Representación gráfica de un contenedor de almacenamiento mediante Canvas nativo
            Canvas(modifier = Modifier.size(76.dp).padding(bottom = 8.dp)) {
                val ancho = size.width
                val alto = size.height

                drawRect(
                    color = Color.White,
                    topLeft = Offset(x = ancho * 0.15f, y = alto * 0.35f),
                    size = Size(width = ancho * 0.7f, height = alto * 0.5f),
                    style = Stroke(width = 4.dp.toPx())
                )
                drawLine(
                    color = Color.White,
                    start = Offset(x = ancho * 0.5f, y = alto * 0.35f),
                    end = Offset(x = ancho * 0.5f, y = alto * 0.85f),
                    strokeWidth = 3.dp.toPx()
                )
                drawLine(
                    color = Color.White,
                    start = Offset(x = ancho * 0.15f, y = alto * 0.35f),
                    end = Offset(x = ancho * 0.35f, y = alto * 0.15f),
                    strokeWidth = 4.dp.toPx()
                )
                drawLine(
                    color = Color.White,
                    start = Offset(x = ancho * 0.85f, y = alto * 0.35f),
                    end = Offset(x = ancho * 0.65f, y = alto * 0.15f),
                    strokeWidth = 4.dp.toPx()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tarjeta contenedora con fondo naranja sutil, delineado y una sombra perimetral profunda
            Card(
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF7ED)),
                border = BorderStroke(1.5.dp, Color(0xFFE2E8F0)),
                elevation = CardDefaults.cardElevation(defaultElevation = 24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 56.dp, bottom = 56.dp, start = 32.dp, end = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Título principal con tamaño imponente y color rojo llamativo
                    Text(
                        text = "StockPro",
                        fontSize = 44.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFDC2626)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Subtítulo expandido, oscurecido y perfectamente centrado
                    Text(
                        text = "Sistema de Gestión Integrada y Control de Bodega",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF1E293B),
                        textAlign = TextAlign.Center // Alineación centrada para equilibrio visual
                    )

                    Spacer(modifier = Modifier.height(54.dp))

                    // Campo de texto estilizado en gris pizarra corporativo para la entrada del nombre
                    OutlinedTextField(
                        value = nombreTexto,
                        onValueChange = { nombreTexto = it },
                        label = { Text("Nombre del Operario", fontWeight = FontWeight.Medium) },
                        placeholder = { Text("Ej. Jassi Torres") },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF2563EB),
                            unfocusedBorderColor = Color(0xFFCBD5E1),
                            focusedLabelColor = Color(0xFF2563EB),
                            unfocusedLabelColor = Color(0xFF475569),
                            focusedTextColor = Color(0xFF0F172A),
                            unfocusedTextColor = Color(0xFF0F172A)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Botón de ingreso con color azul eléctrico vibrante y tipografía en negrilla reforzada
                    Button(
                        onClick = {
                            if (esBotonHabilitado) {
                                navController.navigate("pantalla2/${nombreTexto.trim()}")
                            }
                        },
                        enabled = esBotonHabilitado,
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2563EB),
                            contentColor = Color.White,
                            disabledContainerColor = Color(0xFFE2E8F0),
                            disabledContentColor = Color(0xFF94A3B8)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                    ) {
                        Text(
                            text = "Ingresar al Sistema",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
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
