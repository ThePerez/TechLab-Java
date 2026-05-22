package com.techlab.ecommerce.ui;

import com.techlab.ecommerce.exception.StockInsuficienteException;
import com.techlab.ecommerce.model.LineaPedido;
import com.techlab.ecommerce.model.Pedido;
import com.techlab.ecommerce.model.Producto;
import com.techlab.ecommerce.service.ProductoService;
import com.techlab.ecommerce.util.Validador;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuProducto {

    private final Scanner sc;
    private final ProductoService service;
    
    // Lista para guardar los pedidos que vayamos creando
    private final List<Pedido> historialPedidos;

    public MenuProducto(Scanner sc, ProductoService service) {
        this.sc = sc;
        this.service = service;
        this.historialPedidos = new ArrayList<>();
    }

    public void mostrarMenu() {
        System.out.println("\n======= TechLab - Gestión de Sistema =======");
        System.out.println("1) Agregar producto");
        System.out.println("2) Listar productos");
        System.out.println("3) Buscar producto por ID");
        System.out.println("4) Actualizar producto");
        System.out.println("5) Eliminar producto");
        System.out.println("6) Crear un pedido");
        System.out.println("7) Listar pedidos");
        System.out.println("8) Salir");
        System.out.println("==============================================");
    }

    public void agregarProducto() {
        System.out.println("--- Nuevo producto ---");
        String nombre = Validador.leerTexto(sc, "Nombre: ");
        double precio = Validador.leerDouble(sc, "Precio: ");
        int stock = Validador.leerEntero(sc, "Stock: ");
        String categoria = Validador.leerTexto(sc, "Categoría: ");

        Producto p = new Producto(nombre, precio, stock, categoria);
        Producto guardado = service.guardar(p);

        System.out.println("✔ Producto agregado con id " + guardado.getId());
    }

    public void listarProductos() {
        List<Producto> lista = service.listarTodos();

        if (lista.isEmpty()) {
            System.out.println("No hay productos cargados.");
            return;
        }

        System.out.println("--- Catálogo ---");
        for (Producto p : lista) {
            System.out.println(p);
        }
    }

    public void buscarProducto() {
        int id = Validador.leerEntero(sc, "Ingrese el id del producto: ");
        Producto p = service.obtenerPorId(id);
        System.out.println("Encontrado: " + p);
    }

    public void actualizarProducto() {
        int id = Validador.leerEntero(sc, "Ingrese el id del producto a actualizar: ");
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

    // ----------------------------------------------------------------
    // Operaciones de Pedidos (NUEVO)
    // ----------------------------------------------------------------

    public void crearPedido() {
        System.out.println("\n--- Nuevo Pedido ---");
        
        if (service.listarTodos().isEmpty()) {
            System.out.println("No hay productos disponibles para comprar.");
            return;
        }

        Pedido nuevoPedido = new Pedido();
        boolean seguirComprando = true;

        while (seguirComprando) {
            listarProductos();
            int id = Validador.leerEntero(sc, "\nIngrese el ID del producto a comprar: ");
            Producto p = service.obtenerPorId(id); // Lanza excepción si no existe

            int cantidad = Validador.leerEntero(sc, "Cantidad deseada: ");
            
            // Validamos que alcance el stock. Si no alcanza, Validador lanza la excepción
            Validador.validarStock(p.getStock() - cantidad);

            // Si llegamos acá, es porque hay stock. Lo descontamos.
            p.setStock(p.getStock() - cantidad);
            
            // Lo agregamos a la orden
            nuevoPedido.agregarLinea(new LineaPedido(p, cantidad));
            System.out.println("✔ Producto agregado al carrito.");

            String resp = Validador.leerTexto(sc, "¿Desea agregar otro producto? (s/n): ");
            if (!resp.equalsIgnoreCase("s")) {
                seguirComprando = false;
            }
        }

        if (!nuevoPedido.getLineas().isEmpty()) {
            historialPedidos.add(nuevoPedido);
            System.out.println("\n¡Pedido confirmado exitosamente!");
            System.out.println(nuevoPedido); // Imprime el ticket
        } else {
            System.out.println("Pedido cancelado.");
        }
    }

    public void listarPedidos() {
        if (historialPedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados en el historial.");
            return;
        }

        System.out.println("\n--- Historial de Pedidos ---");
        for (Pedido ped : historialPedidos) {
            System.out.println(ped);
            System.out.println();
        }
    }
}