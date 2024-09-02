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
public class ProductoElectronico extends Producto{
    private int garantia;

    public ProductoElectronico( String nombre, double precio, int stock, int garantia) {
        super(nombre, precio, stock);
        this.garantia = garantia;
    }

    public int getGarantia() {
        return garantia;
    }

    public void setGarantia(int garantia) {
        this.garantia = garantia;
    }

   

   
   @Override 
    public void actualizarProducto(){
    Scanner sc = new Scanner(System.in);
    System.out.println("Actualizar nombre cliente (actual: " + garantia + ")");
        this.garantia = sc.nextInt();
  
        System.out.println("¡¡Producto Electronico actualizado Exitósamnete!!");
    }
    @Override
    public void eliminarProducto(){
        this.garantia = 0;
        System.out.println("¡¡Producto electronico eliminado Exitosamente!!");
    }
    public static Producto crearProductoElectronico(){
               Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el nombre del producto:");
        String nombre = sc.nextLine();
        
        System.out.println("Ingrese el precio del prodcuto:");
        double precio = sc.nextDouble();
        
        System.out.println("Ingrese el stock del producto:");
        int stock = sc.nextInt();
      
          System.out.println("Ingrese la garantia del producto:");
        int garantia = sc.nextInt();
        
        return new ProductoElectronico(nombre, precio, stock, garantia);
    }

}
