/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import java.util.Objects;

/**
 *
 * @author PC_22
 */
public class Habitacion {
    private int numero;
    private String tipo;
    private double  precioNoche;

    public Habitacion(int numero, String tipo, double precioNoche) {
        this.numero = numero;
        this.tipo = tipo;
        this.precioNoche = precioNoche;
    }
    
    

    public int getNumero() {
        return numero;
    }

    public String getTipo() {
        return tipo;
    }

    public double getPrecioNoche() {
        return precioNoche;
    }

    @Override
    public int hashCode() {
       return Objects.hash(numero,precioNoche);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Habitacion other = (Habitacion) obj;
        return Double.doubleToLongBits(this.precioNoche) == Double.doubleToLongBits(other.precioNoche);
    }

    

   
    @Override
    public String toString() {
        return "Habitacion{" + "numero=" + numero + ", tipo=" + tipo + ", precioNoche=" + precioNoche + '}';
    }
    
}
