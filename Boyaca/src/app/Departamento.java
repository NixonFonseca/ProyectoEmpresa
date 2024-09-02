/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import javax.swing.JOptionPane;
import java.util.List;

/**
 *
 * @author PC_22
 */
public class Departamento {

    String nombreDepartamento;
    String ubicacion;
    List<Empleado> empleados;

    public Departamento(String nombreDepartamento, String ubicacion) {
        this.nombreDepartamento = JOptionPane.showInputDialog("Ingrese el nombre del departamento");
        this.ubicacion = JOptionPane.showInputDialog("Ingrese la ubicaion (Oficina física, ciudad o región, área dentro de la sede)");
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void agregarEmpleado() {

    }
    public void mostraEmpleado(){
        
    } 
}
