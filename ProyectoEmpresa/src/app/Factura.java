/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 *
 * @author PC_22
 */
public class Factura {

    private String numeroFactura;
    private String fecha;
    private Cliente cliente;
    private List<Producto> productos;
    private List<Integer> cantidades;
    private double total;
    private Envio envio;

    public Factura(Cliente cliente, List<Producto> productos, List<Integer> cantidades, double total, Envio envio) {
        this.numeroFactura = generarNumeroFactura();
        this.fecha = obtenrFechaActual();
        this.cliente = cliente;
        this.productos = productos;
        this.cantidades = cantidades;
        this.total = total;
        this.envio = envio;
    }

    private String generarNumeroFactura() {
        Random random = new Random();
        int numero = random.nextInt(1000000);
        return String.format("%06d", numero);
    }

    private String obtenrFechaActual() {
        SimpleDateFormat date = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
        return date.format(new Date());

    }

    public void imprimirFactura() {
        System.out.println("======================================");
        System.out.println("              FACTURA                 ");
        System.out.println("Numero Factura " + numeroFactura);
        System.out.println("Fecha: " + fecha);
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Dirreccion: " + cliente.getDireccion());
        System.out.println("-------------------------------------");
        System.out.println("Detalles del Producto: ");
        System.out.printf("%-20s %-10s %-10s %-10s%n", "Prodcuto", "Cantidad ", "V. Unitario", "Subtotal");
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            int cantidad = cantidades.get(i);
            double subtotal = p.getPrecio() * cantidad;
            System.out.printf("%-20s %-10d %-10.2f %-10.2f%n", p.getNombre(), cantidad, p.getPrecio(), subtotal);

        }
        System.out.println("-------------------------------------");
        System.out.printf("%-40s %-10.2f%n", "Costo de envio", envio.getCostoEnvio());
        System.out.printf("%-40s %-10.2f%n", "Total a Pagar", total + envio.getCostoEnvio());
        System.out.println("=====================================");
        System.out.println("             ¡Gracias por su Compra!         ");
        System.out.println("=====================================");
    }
}
