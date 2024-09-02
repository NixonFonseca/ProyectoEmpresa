/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author PC_22
 */
public class Orden {

    private Cliente cliente;
    private List<Producto> productos;
    private List<Integer> cantidades;
    private double total;

    public Orden(Cliente cliente) {
        this.cliente = cliente;
        this.productos = new ArrayList<>();
        this.cantidades = new ArrayList<>();
        this.total = 0.0;
    }

    public void agregarProducto(  Producto producto, int cantidad) {
        if (producto.getStock() >= cantidad) {
            productos.add(producto);
            cantidades.add(cantidad);
            total += producto.getPrecio() * cantidad;
            producto.setStock(producto.getStock() - cantidad);
        } else {
            System.out.println("No hay sufucinete stock para "
                    + producto.getNombre());
        }
    }

    public double calcularTotal() {
        return total;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public List<Integer> getCantidades() {

        return cantidades;
    }

    public void mostrarOrden() {
        System.out.println("Orden para: " + cliente.getNombre());
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            int cantidad = cantidades.get(i);
            System.out.println("- " + p.getNombre() + "(cantidad"
                    + cantidad + "):" + p.getPrecio() * cantidad);

        }
        System.out.println("Total: $" + calcularTotal());
    }

    public static Orden crearOrden(Cliente cliente) {
        Scanner sc = new Scanner(System.in);
        Orden orden = new Orden(cliente);
        while (true) {
            System.out.println("Desea agregar un producto electronico (E) o un"
                    + " producto comun (c)? (s para salir):");
            String tipo = sc.nextLine();
            if (tipo.equalsIgnoreCase("S")) {
                break;
            }else if (tipo.equalsIgnoreCase("E")) {
                ProductoElectronico productoElectronico = (ProductoElectronico)
                        ProductoElectronico.crearProductoElectronico();
                System.out.println("Digite la cantidad");
                int cantidad = sc.nextInt();
                orden.agregarProducto(productoElectronico, cantidad);
                break;
            }else if (tipo.equalsIgnoreCase("C")) {
                Producto producto = Producto.crearProdcuto();
                int cantidad = sc.nextInt();
                orden.agregarProducto(producto, cantidad);
                break;
            }else{
                System.out.println("Opcion invalida");
            }
        }
        return orden;
    }
}
