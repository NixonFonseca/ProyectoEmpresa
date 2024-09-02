/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import javax.swing.JOptionPane;

/**
 *
 * @author PC_22
 */
public class Empleado {
    String nombre;
    int edad;
    double salario;
    String puesto;

    public Empleado(String nombre, int edad, double salario, String puesto) {
        this.nombre = JOptionPane.showInputDialog("Ingrese el nombre del empleado:");
        this.edad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la edad del empleado:"));
        this.salario = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el salario del empleado:"));
        this.puesto = JOptionPane.showInputDialog("Ingrese el cargo del empleado:");
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
    public void asignarProyecto(){
        
    }
}
