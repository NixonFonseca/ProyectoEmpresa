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

    private Libro libro;
    private String nombreUsuario;
    private String celular;
    private String direccion;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;

    public Prestamo(Libro libro, String nombreUsuario, String celular, String direccion) {
        this.libro = libro;
        this.nombreUsuario = nombreUsuario;
        this.celular = celular;
        this.direccion = direccion;
        this.fechaPrestamo = LocalDate.now();
        this.fechaDevolucion = fechaPrestamo.plusWeeks(1);
    }

    public Libro getLibro() {
        return libro;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getCelular() {
        return celular;
    }

    public String getDireccion() {
        return direccion;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public boolean isAtrasado() {
        return LocalDate.now().isAfter(fechaDevolucion);
    }

    public void mostraInfo() {
        System.out.println("usuario: " + nombreUsuario);
        System.out.println("Libro: " + libro);
        System.out.println("Celular: " + celular);
        System.out.println("Direccion: " + direccion);
        System.out.println("Fecha Prestamo: " + fechaPrestamo);
        System.out.println("Fecha devolucion: " + fechaDevolucion);
        System.out.println("Estado: " + (isAtrasado()? "Atrasdo" : "En Plazo"));
        System.out.println("\n");


    }

}
