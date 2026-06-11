package com.example.stockpro

// Modelo que representa un producto del inventario
data class Producto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val stockActual: Int
)