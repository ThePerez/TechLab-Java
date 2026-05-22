# 🛒 TechLab - Sistema de Gestión de Productos

Sistema de consola desarrollado en Java para la gestión de inventario y creación de pedidos. Este proyecto forma parte de la pre-entrega integradora, aplicando principios sólidos de Programación Orientada a Objetos (POO).

## 📌 Características Principales

* **Gestión de Inventario (CRUD):** Agregar, listar, buscar, actualizar y eliminar productos.
* **Sistema de Pedidos:** Creación de órdenes de compra con validación de stock en tiempo real.
* **Validación de Datos:** Control estricto de entradas de usuario para evitar cierres inesperados (manejo de `InputMismatchException`).
* **Excepciones Personalizadas:** Implementación de `ProductoNoEncontradoException` y `StockInsuficienteException` para reglas de negocio.
* **Arquitectura Limpia:** Separación clara entre modelos, servicios, utilidades e interfaz de usuario.

## 🏗️ Estructura del Proyecto

El código está organizado en paquetes lógicos para mantener la escalabilidad:

* `model`: Clases de dominio (`Producto`, `Pedido`, `LineaPedido`).
* `service`: Lógica de negocio (`ProductoService`).
* `ui`: Interfaz de usuario por consola (`MenuProducto`).
* `util`: Validaciones e ingreso seguro de datos (`Validador`).
* `exception`: Manejo de errores personalizados.

## 🛠️ Tecnologías Utilizadas

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
![VS Code](https://img.shields.io/badge/VS_Code-0078D4?style=for-the-badge&logo=visual%20studio%20code&logoColor=white)
![POO](https://img.shields.io/badge/POO-Arquitectura_Limpia-4B0082?style=for-the-badge)

* **Estructuras de Datos:** Colecciones dinámicas (`ArrayList`, `List`).
* **Paradigmas:** Programación Orientada a Objetos (Encapsulamiento, Polimorfismo, Separación de Responsabilidades).
