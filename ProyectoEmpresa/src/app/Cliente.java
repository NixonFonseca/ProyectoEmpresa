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
public class Cliente {
    String nombre;
    String direccion;
    String correoElectronico;

    public Cliente(String nombre, String direccion, String correoElectronico) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.correoElectronico = correoElectronico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
    public void actualizarCliente(){
                Scanner sc = new Scanner(System.in);
        System.out.println("Actualizar nombre cliente (actual: " + nombre + ")");
        this.nombre = sc.nextLine();
        System.out.println("Actualizar direccion cliente (actual: " + direccion + ")");
        this.direccion = sc.nextLine();
            System.out.println("Actualizar correo cliente (actual: " + correoElectronico + ")");
        this.correoElectronico = sc.nextLine();
        System.out.println("¡¡Cliente actualizado Exitósamnete!!");
    }
    public void eliminarCliente(){
            this.nombre = null;
        this.direccion = null;
        this.correoElectronico = null;
        System.out.println("¡¡Cliente eliminado Exitosamnete!!");
    }
    public static Cliente crearCliente(){
             Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el nombre del cliente:");
        String nombre = sc.nextLine();
        
        System.out.println("Ingrese el direccion del clinete:");
        String direccion = sc.nextLine();
        
        System.out.println("Ingrese el correo del cliente:");
        String correoElectronico = sc.nextLine();
        return new Cliente(nombre, direccion, correoElectronico);
    }
}
