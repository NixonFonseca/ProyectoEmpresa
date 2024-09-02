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
public class Gestion {
    
    private String instrumeto;
    private String grupo;
    private String estado;
    private int cantidad;
    public Gestion(){
        this.instrumeto = "";
        this.grupo = "";
        this.estado = "";
        this.cantidad = 1;
    }

    public Gestion(String instrumeto, String grupo, String estado, int cantidad) {
        this.instrumeto = instrumeto;
        this.grupo = grupo;
        this.estado = estado;
        this.cantidad = cantidad;
    }

    public String getinstrumeto() {
        return instrumeto;
    }

    public void setinstrumeto(String instrumeto) {
        this.instrumeto = instrumeto;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public String entrada(String texto) {
        return JOptionPane.showInputDialog(texto);
    }
    
}
