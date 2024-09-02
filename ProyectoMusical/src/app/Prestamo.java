/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import java.time.LocalDate;

/**
 *
 * @author PC_22
 */
public class Prestamo {
    private String instrumeto;
    private String nombreUsuario;
    private int celular;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;

    public Prestamo(String instrumeto, String nombreUsuario, int celular) {
        this.instrumeto = instrumeto;
        this.nombreUsuario = nombreUsuario;
        this.celular = celular;
        this.fechaPrestamo = LocalDate.now();
        this.fechaDevolucion = fechaPrestamo.plusWeeks(1);
    }

    public String getinstrumeto() {
        return instrumeto;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public int getCelular() {
        return celular;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }
    public boolean isAtrasado(){
                return LocalDate.now().isAfter(fechaDevolucion);

    }
    public void mostraInfo() {
        System.out.println("usuario: " + nombreUsuario);
        System.out.println("Instrumento: " + instrumeto);
        System.out.println("Celular: " + celular);
        System.out.println("Fecha Prestamo: " + fechaPrestamo);
        System.out.println("Fecha devolucion: " + fechaDevolucion);
        System.out.println("Estado: " + (isAtrasado()? "Atrasdo" : "En Plazo"));
        System.out.println("\n");


    }
    
}
