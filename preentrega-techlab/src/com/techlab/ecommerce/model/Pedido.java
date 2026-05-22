package com.techlab.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un pedido realizado por el cliente.
 * Contiene una lista de LineaPedido y calcula el total.
 */
public class Pedido {
    private static int contadorId = 1; // Autogenerador de ID para los pedidos
    private int id;
    private List<LineaPedido> lineas;

    // Constructor
    public Pedido() {
        this.id = contadorId++;
        this.lineas = new ArrayList<>();
    }

    // Método para agregar una línea al pedido
    public void agregarLinea(LineaPedido linea) {
        this.lineas.add(linea);
    }

    // Calcula el costo total sumando los subtotales de cada línea
    public double calcularTotal() {
        double total = 0;
        for (LineaPedido linea : lineas) {
            total += linea.calcularSubtotal();
        }
        return total;
    }

    // Getters
    public int getId() {
        return id;
    }

    public List<LineaPedido> getLineas() {
        return lineas;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("===============================\n");
        sb.append("PEDIDO ID: ").append(id).append("\n");
        sb.append("-------------------------------\n");
        for (LineaPedido linea : lineas) {
            sb.append("- ").append(linea).append("\n");
        }
        sb.append("-------------------------------\n");
        sb.append("TOTAL DEL PEDIDO: $").append(calcularTotal()).append("\n");
        sb.append("===============================");
        return sb.toString();
    }
}