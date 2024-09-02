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
      private String isbn;
    private String titulo;
    private String autor;
    private String genero;
    private int numeroCopias;
    private boolean disponible;

    public Libro(String isbn, String titulo, String autor, String genero, int numeroCopias, boolean disponible) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.numeroCopias = numeroCopias;
        this.disponible = disponible;
    }

    // Métodos getters y setters
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public int getNumeroCopias() { return numeroCopias; }
    public void setNumeroCopias(int numeroCopias) { this.numeroCopias = numeroCopias; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    @Override
    public String toString() {
        return "Libro{" + "isbn=" + isbn + ", titulo=" + titulo + ", autor=" + autor + ", genero=" + genero + ", numeroCopias=" + numeroCopias + ", disponible=" + disponible + '}' + "\n";
    }
    
    
}
