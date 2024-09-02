/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Biblioteca {

    private List<Usuario> usuarios = new ArrayList<>();
    private List<Libro> catalogo = new ArrayList<>();
    private List<Prestamo> prestamos = new ArrayList<>();
    private Logger logger = new FileLogger();  // Crear una instancia de FileLogger
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Definición de dtf

    // Métodos de gestión de usuarios
    public void agregarUsuario(Usuario usuario) {
        // Verificar si el usuario ya existe
        for (Usuario u : usuarios) {
            if (u.getId().equals(usuario.getId())) {
                logger.logError("Intento de agregar un usuario que ya existe: ID " + usuario.getId());
                System.out.println("El usuario con ID " + usuario.getId() + " ya existe.");
                return;
            }
        }
        usuarios.add(usuario);
        
        
        logger.logOperacion("Usuario agregado: ID " + usuario.getId() + ", Nombre: " + usuario.getNombre());
        System.out.println("Usuario agregado: " + usuario.getNombre());
    }

    public void eliminarUsuario(String id) {
        // Buscar el usuario por ID
        Iterator<Usuario> iterator = usuarios.iterator();
        while (iterator.hasNext()) {
            Usuario u = iterator.next();
            if (u.getId().equals(id)) {
                iterator.remove();
                logger.logOperacion("Usuario eliminado: ID " + id);
                System.out.println("Usuario con ID " + id + " eliminado.");
                return;
            }
        }
        logger.logError("Intento de eliminar un usuario que no existe: ID " + id);
        System.out.println("Usuario con ID " + id + " no encontrado.");
    }

    public Usuario autenticar(String id, String password) {
        // En un sistema real, deberías verificar la contraseña aquí
        Fichero fichero = new Fichero();
        fichero.crearArchivo("usuario.txt");
        fichero.crearArchivo("prestamos.txt");
        fichero.crearArchivo("catalogo.txt");

        for (Usuario u : usuarios) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }

    // Métodos para gestión del catálogo
    public void agregarLibro(Libro libro, Usuario bibliotecario) {
        if (!"bibliotecario".equals(bibliotecario.getTipoUsuario())) {
            logger.logError("Acceso denegado para agregar libro por usuario: ID " + bibliotecario.getId());
            System.out.println("Solo los bibliotecarios pueden agregar libros.");
            return;
        }
        // Verificar si el libro ya existe
        for (Libro l : catalogo) {
            if (l.getIsbn().equals(libro.getIsbn())) {
                logger.logError("Intento de agregar un libro que ya existe: ISBN " + libro.getIsbn());
                System.out.println("El libro con ISBN " + libro.getIsbn() + " ya existe.");
                return;
            }
        }
        catalogo.add(libro);
        Fichero n = new Fichero();
        n.escribirArchivo("catalogo.txt", libro);
        logger.logOperacion("Libro agregado: " + libro.getTitulo() + " (ISBN: " + libro.getIsbn() + ")");
        System.out.println("Libro agregado: " + libro.getTitulo());
    }

    public void eliminarLibro(String isbn, Usuario bibliotecario) {
        if (!"bibliotecario".equals(bibliotecario.getTipoUsuario())) {
            logger.logError("Acceso denegado para eliminar libro por usuario: ID " + bibliotecario.getId());
            System.out.println("Solo los bibliotecarios pueden eliminar libros.");
            return;
        }
        // Buscar el libro por ISBN
        Iterator<Libro> iterator = catalogo.iterator();
        while (iterator.hasNext()) {
            Libro l = iterator.next();
            if (l.getIsbn().equals(isbn)) {
                iterator.remove();
                logger.logOperacion("Libro eliminado: ISBN " + isbn);
                System.out.println("Libro con ISBN " + isbn + " eliminado.");
                return;
            }
        }
        logger.logError("Intento de eliminar un libro que no existe: ISBN " + isbn);
        System.out.println("Libro con ISBN " + isbn + " no encontrado.");
    }

    public void modificarLibro(String isbn, String nuevoTitulo, int nuevoNumeroCopias, Usuario bibliotecario) {
        if (!"bibliotecario".equals(bibliotecario.getTipoUsuario())) {
            logger.logError("Acceso denegado para modificar libro por usuario: ID " + bibliotecario.getId());
            System.out.println("Solo los bibliotecarios pueden modificar libros.");
            return;
        }
        // Buscar el libro por ISBN
        for (Libro l : catalogo) {
            if (l.getIsbn().equals(isbn)) {
                l.setTitulo(nuevoTitulo);
                l.setNumeroCopias(nuevoNumeroCopias);
                logger.logOperacion("Libro modificado: ISBN " + isbn + ", Nuevo título: " + nuevoTitulo + ", Nuevas copias: " + nuevoNumeroCopias);
                System.out.println("Libro con ISBN " + isbn + " modificado.");
                return;
            }
        }
        logger.logError("Intento de modificar un libro que no existe: ISBN " + isbn);
        System.out.println("Libro con ISBN " + isbn + " no encontrado.");
    }

    // Métodos para gestión de préstamos
    public void solicitarPrestamo(Libro libro, Usuario usuario) {
        if (libro.getNumeroCopias() <= 0) {
            JOptionPane.showMessageDialog(null, "No hay copias disponibles del libro: " + libro.getTitulo());
            return;
        }

        Prestamo prestamo = new Prestamo(libro, usuario, new Date(), null, "pendiente");
        prestamos.add(prestamo);
        libro.setNumeroCopias(libro.getNumeroCopias() - 1);
        logger.logOperacion("Préstamo solicitado: ISBN " + libro.getIsbn() + " por usuario ID " + usuario.getId());
        JOptionPane.showMessageDialog(null, "Préstamo solicitado para el libro: " + libro.getTitulo());
    }

    public void devolverPrestamo(Prestamo prestamo) {
        prestamo.setFechaDevolucion(new Date());
        prestamo.getLibro().setNumeroCopias(prestamo.getLibro().getNumeroCopias() + 1); // Restablecer copias
        prestamo.setEstado("devuelto");
        logger.logOperacion("Préstamo devuelto: ISBN " + prestamo.getLibro().getIsbn() + ", Usuario: ID " + prestamo.getUsuario().getId());
        System.out.println("Préstamo devuelto para el libro con ISBN " + prestamo.getLibro().getIsbn());
    }

    public void renovarPrestamo(Prestamo prestamo) {
        if ("devuelto".equals(prestamo.getEstado())) {
            logger.logError("Intento de renovar un préstamo ya devuelto: ISBN " + prestamo.getLibro().getIsbn());
            System.out.println("No se puede renovar un préstamo ya devuelto.");
            return;
        }
        // Renueva el préstamo, actualizando la fecha de inicio y la fecha de devolución
        prestamo.setFechaInicio(new Date());
        prestamo.setFechaDevolucion(null); // O una nueva fecha de devolución según las políticas de la biblioteca
        logger.logOperacion("Préstamo renovado: ISBN " + prestamo.getLibro().getIsbn() + ", Usuario: ID " + prestamo.getUsuario().getId());
        System.out.println("Préstamo renovado para el libro con ISBN " + prestamo.getLibro().getIsbn());
    }

    public Libro buscarLibroPorIsbn(String isbn) {
        for (Libro libro : catalogo) {
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }

    public Prestamo buscarPrestamoPorIsbn(String isbn) {
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getLibro().getIsbn().equals(isbn) && "pendiente".equals(prestamo.getEstado())) {
                return prestamo;
            }
        }
        return null;
    }

    public List<Libro> getCatalogo() {
        return catalogo;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void mostrarUsuariosConPrestamos() {
        StringBuilder sb = new StringBuilder();
        sb.append("Lista de usuarios con préstamos:\n");
        for (Prestamo prestamo : prestamos) {
            Usuario usuario = prestamo.getUsuario();
            Libro libro = prestamo.getLibro();
            sb.append("Usuario ID: ").append(usuario.getId()).append(", Nombre: ").append(usuario.getNombre()).append("\n");
            sb.append("  Libro: ").append(libro.getTitulo()).append(" (ISBN: ").append(libro.getIsbn()).append(")\n");
            sb.append("  Fecha de solicitud: ").append(formatDate(prestamo.getFechaInicio())).append("\n");
            sb.append("  Fecha de devolución: ").append(prestamo.getFechaDevolucion() != null ? formatDate(prestamo.getFechaDevolucion()) : "Pendiente").append("\n");
            sb.append("  Estado: ").append(prestamo.getEstado()).append("\n");
            sb.append("--------------------------------------------------\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private String formatDate(Date date) {
        if (date == null) {
            return "N/A";
        }
        LocalDate localDate = new java.sql.Date(date.getTime()).toLocalDate();
        return localDate.format(dtf);
    }

}
