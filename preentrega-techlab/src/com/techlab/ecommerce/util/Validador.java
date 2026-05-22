package com.techlab.ecommerce.util;

import com.techlab.ecommerce.exception.StockInsuficienteException;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase utilitaria con métodos de validación reutilizables.
 *
 * Todos los métodos son estáticos: no necesitamos crear una
 * instancia de Validador para usarlos. Se invocan directamente
 * con Validador.validarPrecio(...), Validador.leerEntero(...), etc.
 *
 * Separar las validaciones en su propia clase mantiene al
 * ProductoService enfocado en la lógica de negocio y al menú
 * enfocado en la interacción con el usuario.
 */
public class Validador {

    // ----------------------------------------------------------------
    // Validaciones de datos del producto
    // ----------------------------------------------------------------
    // Estos métodos lanzan excepción si el dato es inválido.
    // No retornan nada: si terminan sin lanzar excepción, el dato
    // es válido. Es un patrón común para validaciones.

    public static void validarNombre(String nombre) {
        // Un nombre nulo o vacío (incluso con solo espacios en blanco)
        // no representa un producto válido.
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
    }

    public static void validarPrecio(double precio) {
        // Un precio negativo no tiene sentido en un catálogo.
        // Aceptamos 0 por si en algún momento hay productos gratuitos
        // o promocionales.
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
    }

    public static void validarStock(int stock) {
        // Stock negativo no es válido. Usamos nuestra excepción
        // personalizada porque es un error de dominio específico
        // del manejo de inventario.
        if (stock < 0) {
            throw new StockInsuficienteException("El stock no puede ser negativo.");
        }
    }

    public static void validarCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("La categoría no puede estar vacía.");
        }
    }

    // ----------------------------------------------------------------
    // Lectura segura desde consola
    // ----------------------------------------------------------------
    // Estos métodos resuelven el problema clásico: el usuario escribe
    // letras donde se esperaba un número y el programa se rompe.
    // En lugar de dejar que la excepción suba, la atrapamos acá y
    // le pedimos al usuario que vuelva a ingresar el dato.

    public static int leerEntero(Scanner sc, String mensaje) {
        // Bucle infinito que solo se rompe cuando el usuario ingresa
        // un entero válido. Mientras tanto, le avisamos del error.
        while (true) {
            System.out.print(mensaje);
            try {
                int valor = sc.nextInt();
                sc.nextLine(); // limpia el salto de línea pendiente
                return valor;
            } catch (InputMismatchException e) {
                System.out.println(" Debe ingresar un número entero. Intente nuevamente.");
                sc.nextLine(); // descarta lo que el usuario escribió mal
            }
        }
    }

    public static double leerDouble(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                double valor = sc.nextDouble();
                sc.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Debe ingresar un número (puede usar coma o punto). Intente nuevamente.");
                sc.nextLine(); // lee un String completo hasta el Enter y lo devuelve.
            }
        }
    }

    public static String leerTexto(Scanner sc, String mensaje) {
        // Lectura simple de texto. La validación de "no vacío" la
        // hacen los métodos validarNombre / validarCategoria
        // (cada función con una sola responsabilidad).
        System.out.print(mensaje);
        return sc.nextLine(); // lee un String completo hasta el Enter y lo devuelve.
    }
}
