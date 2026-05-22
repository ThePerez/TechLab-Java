package com.techlab.ecommerce.ui;

import com.techlab.ecommerce.model.Producto;
import com.techlab.ecommerce.service.ProductoService;
import com.techlab.ecommerce.util.Validador;

import java.util.List;
import java.util.Scanner;

/**
 * Maneja la interacción con el usuario a través del menú de consola.
 *
 * Esta clase es responsable de:
 *  - Mostrar el menú al usuario.
 *  - Pedirle los datos necesarios para cada operación.
 *  - Mostrar los resultados o mensajes informativos.
 *
 * No contiene lógica de negocio: para todo lo que tenga que ver
 * con guardar, buscar, actualizar o eliminar productos, delega en
 * el ProductoService. Tampoco controla el flujo del programa: eso
 * lo hace el Main, que decide cuándo llamar a cada método de esta
 * clase y atrapa las excepciones que puedan ocurrir.
 */
public class MenuProducto {

    // Atributos: el Scanner y el Service que esta clase necesita
    // para hacer su trabajo. Se reciben por constructor (no se
    // crean acá adentro) para que quien instancia la clase tenga
    // el control sobre qué Scanner y qué Service usar. Esto se
    // llama "inyección por constructor" y es el mismo patrón que
    // van a ver en Spring Boot.
    private final Scanner sc;
    private final ProductoService service;

    public MenuProducto(Scanner sc, ProductoService service) {
        this.sc = sc;
        this.service = service;
    }

    // ----------------------------------------------------------------
    // Menú principal
    // ----------------------------------------------------------------

    public void mostrarMenu() {
        System.out.println("======= TechLab - Gestión de Productos =======");
        System.out.println("1) Agregar producto");
        System.out.println("2) Listar productos");
        System.out.println("3) Buscar producto por ID");
        System.out.println("4) Actualizar producto");
        System.out.println("5) Eliminar producto");
        System.out.println("6) Salir");
        System.out.println("==============================================");
    }

    // ----------------------------------------------------------------
    // Operaciones del CRUD
    // ----------------------------------------------------------------
    // Cada método corresponde a una opción del menú. Como los
    // atributos sc y service ya están guardados en la instancia,
    // los métodos no necesitan recibirlos por parámetro: los usan
    // directamente con this.sc y this.service (Java permite omitir
    // el "this" cuando no hay ambigüedad).

    public void agregarProducto() {
        System.out.println("--- Nuevo producto ---");
        String nombre = Validador.leerTexto(sc, "Nombre: ");
        double precio = Validador.leerDouble(sc, "Precio: ");
        int stock = Validador.leerEntero(sc, "Stock: ");
        String categoria = Validador.leerTexto(sc, "Categoría: ");

        // Construimos el producto y lo enviamos al servicio.
        // El servicio se encarga de validar y de asignar el id.
        Producto p = new Producto(nombre, precio, stock, categoria);
        Producto guardado = service.guardar(p);

        System.out.println("✔ Producto agregado con id " + guardado.getId());
    }

    public void listarProductos() {
        // Recibimos un List, ver explicación en ProductoService
        List<Producto> lista = service.listarTodos();

        if (lista.isEmpty()) {
            System.out.println("No hay productos cargados.");
            return;
        }

        System.out.println("--- Catálogo ---");
        for (Producto p : lista) {
            // Java llama automáticamente a p.toString() al
            // imprimir un objeto con println.
            System.out.println(p);
        }
    }

    public void buscarProducto() {
        int id = Validador.leerEntero(sc, "Ingrese el id del producto: ");
        // Si no existe, obtenerPorId lanza excepción y el catch
        // del Main muestra el mensaje al usuario.
        Producto p = service.obtenerPorId(id);
        System.out.println("Encontrado: " + p);
    }

    public void actualizarProducto() {
        int id = Validador.leerEntero(sc, "Ingrese el id del producto a actualizar: ");

        // Mostramos primero los datos actuales para que el usuario
        // sepa qué está modificando.
        Producto actual = service.obtenerPorId(id);
        System.out.println("Datos actuales: " + actual);

        System.out.println("--- Ingrese los nuevos datos ---");
        String nombre = Validador.leerTexto(sc, "Nombre: ");
        double precio = Validador.leerDouble(sc, "Precio: ");
        int stock = Validador.leerEntero(sc, "Stock: ");
        String categoria = Validador.leerTexto(sc, "Categoría: ");

        Producto datos = new Producto(nombre, precio, stock, categoria);
        Producto actualizado = service.actualizar(id, datos);

        System.out.println("Producto actualizado: " + actualizado);
    }

    public void eliminarProducto() {
        int id = Validador.leerEntero(sc, "Ingrese el id del producto a eliminar: ");
        service.eliminar(id);
        System.out.println("Producto eliminado.");
    }
}