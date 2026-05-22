package com.techlab.ecommerce;

import com.techlab.ecommerce.exception.ProductoNoEncontradoException;
import com.techlab.ecommerce.exception.StockInsuficienteException;
import com.techlab.ecommerce.model.Producto;
import com.techlab.ecommerce.service.ProductoService;
import com.techlab.ecommerce.ui.MenuProducto;
import com.techlab.ecommerce.util.Validador;

import java.util.Scanner;

/**
 * Clase principal: punto de entrada del programa.
 *
 * Su única responsabilidad es ORQUESTAR la ejecución:
 *  - Crear el servicio y el menú.
 *  - Controlar el bucle del menú hasta que el usuario decida salir.
 *  - Atrapar las excepciones que puedan surgir y mostrar el mensaje
 *    al usuario sin que el programa se cierre.
 *
 * Toda la interacción con el usuario (mostrar el menú, pedir datos,
 * mostrar resultados) está delegada en MenuProducto. La lógica de
 * negocio (validar, guardar, buscar, actualizar, eliminar) está en
 * ProductoService. Cada clase tiene una sola responsabilidad clara.
 */
public class Main {

    public static void main(String[] args) {
        // Creamos las dependencias una sola vez y las compartimos
        // durante toda la ejecución del programa.
        ProductoService service = new ProductoService();
        Scanner sc = new Scanner(System.in);
        MenuProducto menu = new MenuProducto(sc, service);

        // Carga inicial de productos de prueba.
        // Comentar esta línea si se quiere arrancar con el catálogo vacío.
        cargarDatosDePrueba(service);

        int opcion;

        // do-while garantiza que el menú se muestre al menos una
        // vez. Se repite hasta que el usuario elige la opción 6.
        do {
            menu.mostrarMenu();
            opcion = Validador.leerEntero(sc, "Elija una opción: ");

            // Cada opción del menú está envuelta en try/catch.
            // Si una operación lanza una excepción (producto
            // inexistente, datos inválidos, etc.), el programa NO
            // se cae: muestra el mensaje y vuelve a mostrar el menú.
            try {
                switch (opcion) {
                    case 1 -> menu.agregarProducto();
                    case 2 -> menu.listarProductos();
                    case 3 -> menu.buscarProducto();
                    case 4 -> menu.actualizarProducto();
                    case 5 -> menu.eliminarProducto();
                    case 6 -> System.out.println("¡Hasta luego!");
                    default -> System.out.println("Opción inválida. Elija un número del 1 al 6.");
                }
            } catch (ProductoNoEncontradoException | StockInsuficienteException e) {
                // Capturamos nuestras excepciones personalizadas.
                // Cada una tiene su propio mensaje, definido al
                // momento de lanzarla en el servicio o el validador.
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                // IllegalArgumentException es la que lanza el
                // Validador para datos genéricos inválidos
                // (nombre vacío, precio negativo, etc.).
                System.out.println("Dato inválido: " + e.getMessage());
            }

            System.out.println(); // línea en blanco entre operaciones

        } while (opcion != 6);

        sc.close();
    }

    // ----------------------------------------------------------------
    // Carga de datos de prueba
    // ----------------------------------------------------------------
    // Precarga el catálogo con algunos productos al iniciar el programa.
    // Es útil durante el desarrollo para no tener que cargar productos
    // a mano cada vez que se ejecuta la aplicación. Para empezar con
    // el catálogo vacío, basta con comentar la llamada a este método
    // en el main.
    //
    // Notar que la carga usa el método público service.guardar(): no
    // accedemos directamente a la lista interna del servicio. Esto
    // respeta el encapsulamiento y nos asegura que los productos de
    // prueba pasen por las mismas validaciones que cualquier otro.
    private static void cargarDatosDePrueba(ProductoService service) {
        service.guardar(new Producto("Café molido 500g", 4500, 30, "Bebidas"));
        service.guardar(new Producto("Yerba mate 1kg", 3200, 50, "Bebidas"));
        service.guardar(new Producto("Galletitas dulces", 1850, 100, "Almacén"));
        service.guardar(new Producto("Aceite de oliva 500ml", 6700, 20, "Almacén"));
        service.guardar(new Producto("Chocolate amargo 70%", 2900, 15, "Golosinas"));
        System.out.println("✔ Se cargaron 5 productos de prueba.\n");
    }
}