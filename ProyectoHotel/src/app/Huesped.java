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
public class Huesped {
    private String nombre;
    private String documento;
    private String correoElectronico;
    private String telefono;

    public Huesped(String nombre, String documento, String correoElectronico, String telefono) {
        this.nombre = nombre;
        this.documento = documento;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getTelefono() {
        return telefono;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.documento);
        return hash;
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
        final Huesped other = (Huesped) obj;
        return Objects.equals(this.documento, other.documento);
    }

  

  

    @Override
    public String toString() {
        return "Huesped{" + "nombre=" + nombre + ", documento=" + documento + ", correoElectronico=" + correoElectronico + ", telefono=" + telefono + '}';
    }
    
            
}
