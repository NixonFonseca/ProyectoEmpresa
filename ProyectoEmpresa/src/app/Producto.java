/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import java.util.Scanner;

/**
 *
 * @author PC_22
 */
public class Producto {
    String nombre;
    double precio;
    int stock;

    public Producto(String nombre, double precio, int stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    public void actualizarProducto(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Actualizar nombre producto (actual: " + nombre + ")");
        this.nombre = sc.nextLine();
        System.out.println("Actualizar precio producto (actual: " + precio + ")");
        this.precio = sc.nextDouble();
            System.out.println("Actualizar stock producto (actual: " + stock + ")");
        this.stock = sc.nextInt();
        System.out.println("¡¡Prodcuto actualizado Exitósamnete!!");
    }
    public void eliminarProducto(){
        this.nombre = null;
        this.precio =0.0;
        this.stock = 0;
        System.out.println("¡¡Producto eliminado Exitosamnete!!");
    }
    public static Producto crearProdcuto(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el nombre del producto:");
        String nombre = sc.nextLine();
        
        System.out.println("Ingrese el precio del producto:");
        double precio = sc.nextDouble();
        
        System.out.println("Ingrese el nombre del producto:");
        int stock = sc.nextInt();
        return new Producto(nombre, precio, stock);
    }
}
