package com.techlab.ecommerce.service;

import com.techlab.ecommerce.exception.ProductoNoEncontradoException;
import com.techlab.ecommerce.model.Producto;
import com.techlab.ecommerce.util.Validador;

import java.util.ArrayList;
import java.util.List;

/**
 * Capa de servicio: contiene la lógica de negocio del sistema.
 *
 * Es responsable de:
 *  - Mantener la colección de productos.
 *  - Asignar el id al guardar un nuevo producto.
 *  - Validar los datos antes de guardar o actualizar.
 *  - Buscar, modificar y eliminar productos por id.
 *
 * No tiene Scanner ni System.out: no interactúa con el usuario.
 * Quien quiera mostrar mensajes o leer datos lo hace por afuera
 * (en este proyecto, lo hace la clase Main). Esa separación es
 * la que va a permitir, en clases siguientes, reemplazar el menú
 * por una API REST sin tocar este archivo.
 */
public class ProductoService {

    // Colección en memoria que guarda los productos.
    // En la pre-entrega esta lista vive solo durante la ejecución
    // del programa: al cerrar la aplicación se pierde todo. Más
    // adelante, esta lista se reemplazará por una base de datos.
    private List<Producto> productos = new ArrayList<>();

    // Contador para asignar ids únicos. Es 'static' porque pertenece
    // a la clase y no a una instancia particular: garantiza que el
    // id sea único aunque hubiera varias instancias del servicio.
    private static int contadorId = 1;

    // ----------------------------------------------------------------
    // Operaciones del CRUD
    // ----------------------------------------------------------------

    // CREATE: agrega un nuevo producto al catálogo.
    public Producto guardar(Producto p) {
        // Validamos antes de guardar. Si algo está mal, se lanza
        // excepción y el producto NO se agrega a la lista.
        Validador.validarNombre(p.getNombre());
        Validador.validarPrecio(p.getPrecio());
        Validador.validarStock(p.getStock());
        Validador.validarCategoria(p.getCategoria());

        // El id lo asigna el servicio, no el usuario. Después de
        // asignarlo, incrementamos el contador para el próximo.
        p.setId(contadorId);
        contadorId++;

        productos.add(p);
        return p;
    }

    // READ: devuelve toda la lista de productos.
    // Declaramos el retorno como List (interfaz) y no como
    // ArrayList (clase concreta). Así el código depende del
    // "qué se puede hacer" y no del "cómo está implementado".
    // Si mañana cambiamos la implementación interna, quien use
    // este método sigue funcionando sin cambios.
    public List<Producto> listarTodos() {
        return productos;
    }

    // READ: busca un producto por id. Si no existe, lanza excepción.
    // No devolvemos null porque obligaría a quien llama a chequear
    // por null en cada uso, y olvidarse de hacerlo provoca el famoso
    // NullPointerException. Una excepción es más explícita.
    public Producto obtenerPorId(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) {
                return p;
            }
        }
        throw new ProductoNoEncontradoException("No se encontró un producto con id " + id);
    }

    // UPDATE: actualiza los datos de un producto existente.
    // Recibe el id del producto a modificar y un objeto Producto
    // con los nuevos datos. Solo actualiza los campos editables;
    // el id no se modifica nunca.
    public Producto actualizar(int id, Producto datos) {
        // Reutilizamos obtenerPorId: si no existe, lanza excepción
        // y la actualización se cancela automáticamente.
        Producto p = obtenerPorId(id);

        // Validamos los nuevos datos antes de aplicarlos.
        Validador.validarNombre(datos.getNombre());
        Validador.validarPrecio(datos.getPrecio());
        Validador.validarStock(datos.getStock());
        Validador.validarCategoria(datos.getCategoria());

        // Modificamos el producto encontrado. Como Java pasa los
        // objetos por referencia, los cambios se reflejan en la
        // lista sin necesidad de hacer nada más.
        p.setNombre(datos.getNombre());
        p.setPrecio(datos.getPrecio());
        p.setStock(datos.getStock());
        p.setCategoria(datos.getCategoria());

        return p;
    }

    // DELETE: elimina un producto por id.
    public void eliminar(int id) {
        // Verificamos que exista antes de eliminar. Si no existe,
        // obtenerPorId lanza la excepción y el método termina.
        Producto p = obtenerPorId(id);
        productos.remove(p);
    }
}
