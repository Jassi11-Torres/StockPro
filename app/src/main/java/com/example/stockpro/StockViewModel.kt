package com.example.stockpro

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class StockViewModel : ViewModel() {

    // 1. Lista privada reactiva para el inventario de la bodega
    private val _productos = mutableStateListOf(
        Producto(1, "Martillo Industrial", "Martillo de acero templado con mango ergonómico anti-vibración.", 14.99, 12),
        Producto(2, "Destornillador Phillips", "Destornillador imantado de alta precisión aislado para 1000V.", 5.50, 3),
        Producto(3, "Taladro Inalámbrico 20V", "Taladro percutor con dos baterías de litio y maletín de transporte.", 89.99, 6),
        Producto(4, "Cinta Aisladora 20m", "Cinta de PVC de alta adherencia y resistencia térmica.", 1.25, 0),
        Producto(5, "Lentes de Seguridad", "Protección UV con tratamiento anti-empañante y patillas ajustables.", 4.75, 2),
        Producto(6, "Caja de Clavos 2 pulg.", "Caja con 500 unidades de clavos de acero galvanizado.", 3.20, 25)
    )

    // 2. Propiedad pública de solo lectura para la interfaz gráfica
    val productos: List<Producto> = _productos

    // 3. Busca un producto específico por su ID único para la edición
    fun obtenerProducto(id: Int): Producto? {
        return _productos.find { it.id == id }
    }

    // 4. Actualiza las existencias de un producto en la lista mutable
    fun actualizarStock(id: Int, nuevaCantidad: Int) {
        val productoEncontrado = _productos.find { it.id == id }
        if (productoEncontrado != null) {
            val indice = _productos.indexOf(productoEncontrado)
            _productos[indice] = productoEncontrado.copy(stockActual = nuevaCantidad)
        }
    }

    // 5. Calcula la sumatoria total del valor del inventario
    fun calcularValorTotalInventario(): Double {
        return _productos.sumOf { it.precio * it.stockActual }
    }

    // 6. Filtra los artículos con existencias críticas menores a 5
    fun obtenerProductosEnRiesgo(): List<Producto> {
        return _productos.filter { it.stockActual < 5 }
    }

    // 7. Cuenta los artículos agotados en su totalidad
    fun obtenerProductosEnCero(): Int {
        return _productos.count { it.stockActual == 0 }
    }
}
