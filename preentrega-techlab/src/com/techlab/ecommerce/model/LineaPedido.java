package com.techlab.ecommerce.model;

/**
 * Representa un producto y la cantidad deseada dentro de un pedido.
 */
public class LineaPedido {
    private Producto producto;
    private int cantidad;

    // Constructor
    public LineaPedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    // Calcula el costo de esta línea (precio * cantidad)
    public double calcularSubtotal() {
        return producto.getPrecio() * cantidad;
    }

    // Getters y Setters
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return producto.getNombre() + " x" + cantidad + " (Subtotal: $" + calcularSubtotal() + ")";
    }
}