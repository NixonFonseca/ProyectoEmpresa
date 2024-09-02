/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

/**
 *
 * @author PC_22
 */
public class Usuario {
     private String id;
    private String nombre;
    private String tipoUsuario; // "lector" o "bibliotecario"
    private String correoElectronico;

    public Usuario(String id, String nombre, String tipoUsuario, String correoElectronico) {
        this.id = id;
        this.nombre = nombre;
        this.tipoUsuario = tipoUsuario;
        this.correoElectronico = correoElectronico;
    }

    // Métodos getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }
    
    
}
