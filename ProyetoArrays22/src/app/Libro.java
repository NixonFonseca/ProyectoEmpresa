/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

/**
 *
 * @author PC_22
 */
public class Libro {

    private String nombre;
    private String edicion;
    private String categoria;
    private int numEjemplares;
    public  Libro(){
        this.nombre = "";
        this.edicion = "";
        this.categoria = "";
        this.numEjemplares = 1;
    }

    public Libro(String nombre, String edicion, String categoria, int numEjemplares) {
        this.nombre = nombre;
        this.edicion = edicion;
        this.categoria = categoria;
        this.numEjemplares = numEjemplares;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdicion() {
        return edicion;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getNumElemplares() {
        return numEjemplares;
    }

    public void setNumElemplares(int numElemplares) {
        this.numEjemplares = numEjemplares;
    }

}
