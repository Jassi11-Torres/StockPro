package com.example.stockpro

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class StockViewModel : ViewModel() {

    //1. Lista privada: mutableStateListOf avisa a compose cuando hay cambios en el inventario
    //private se declara con guion bajo para indicar que es privada y no se expone directamente a la interfaz gráfica
    private val _productos = mutableStateListOf(
        Producto(1, "Martillo Industrial", "Martillo de acero templado con mango ergonómico anti-vibración.", 14.99, 12),
        Producto(2, "Destornillador Phillips", "Destornillador imantado de alta precisión aislado para 1000V.", 5.50, 3), // Dejamos stock menor a 5 para el color rojo
        Producto(3, "Taladro Inalámbrico 20V", "Taladro percutor con dos baterías de litio y maletín de transporte.", 89.99, 6),
        Producto(4, "Cinta Aisladora 20m", "Cinta de PVC de alta adherencia y resistencia térmica.", 1.25, 0), // Arranca en cero para probar reportes
        Producto(5, "Lentes de Seguridad", "Protección UV con tratamiento anti-empañante y patillas ajustables.", 4.75, 2),
        Producto(6, "Caja de Clavos 2 pulg.", "Caja con 500 unidades de clavos de acero galvanizado.", 3.20, 25)
    )

    //2. Lista pública (solo lectura) expuesta en la interfaz gráfica
    val productos: List<Producto> = _productos
}
