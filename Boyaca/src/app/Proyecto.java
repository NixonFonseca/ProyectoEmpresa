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
public class Proyecto {
    String nombreProyecto;
    double presupuesto;
    String fechaInicio;
    String fechaFin;

    public Proyecto(String nombreProyecto, double presupuesto, String fechaInicio, String fechaFin) {
        this.nombreProyecto = JOptionPane.showInputDialog("Ingrese el nombre del proyecto");
        this.presupuesto = Double.parseDouble(JOptionPane.showInputDialog("Ingres el presupuesto"));
        this.fechaInicio = JOptionPane.showInputDialog("Ingrese la fecha de inicio");
        this.fechaFin = JOptionPane.showInputDialog("Ingrese la fecha de fin");
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }
    public void asignarEmpleado (){
        
    }
    public void mostrarEmpleado(){
        
    }
}
